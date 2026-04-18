package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Shadow @Final protected LevelStorageSource.LevelStorageAccess storageSource;
    @Shadow @Final private PackRepository packRepository;
    @Shadow private PlayerList playerList;
    @Shadow @Final protected WorldData worldData;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void copyDatapacks(CallbackInfo ci) {
        copyDatapacks();
    }

    @ModifyVariable(method = "reloadResources", at = @At("HEAD"), argsOnly = true)
    private Collection<String> reload(Collection<String> packsToEnable) {
        copyDatapacks();
        displayMessage();

        var worldDatapacks = worldData.getDataConfiguration().dataPacks();
        List<String> disabledDatapacks = new ArrayList<>(worldDatapacks.getDisabled());
        List<String> datapacksToEnable = new ArrayList<>(worldDatapacks.getEnabled());
        List<String> uniqueDatapacks = packsToEnable.stream().filter(e -> !datapacksToEnable.contains(e)).toList();

        // Enabling required datapacks
        if (Main.getConfig().shouldApplyRequiredPacksToExistingWorld())
            uniqueDatapacks.stream().filter(Main::isRequiredDatapack).forEach(datapacksToEnable::add);

        // If the datapack was disabled and then turned on, it needs to be applied
        disabledDatapacks.stream().filter(datapack -> !datapacksToEnable.contains(datapack)).forEach(datapacksToEnable::add);

        return datapacksToEnable;
    }

    @Redirect(method = "configurePackRepository", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", ordinal = 1))
    private static boolean preventAutoLoading(Set<String> packs, Object pack) {
        String packName = (String) pack;

        if (!packName.startsWith("file/") || Main.isRequiredDatapack(packName))
            return packs.add(packName);

        return false;
    }

    @Unique
    private void copyDatapacks() {
        if (packRepository != null) {
            Path path = storageSource.getLevelPath(LevelResource.DATAPACK_DIR);
            Collection<String> enabled = packRepository.getSelectedIds();
            Main.copyDatapacks(path, new ArrayList<>(enabled));
        }
    }

    @Unique
    private void displayMessage() {
        if (Main.getConfig().sendRestartWarning()) {
            playerList.getPlayers().forEach(player ->
                    //~ if >=26.1 'displayClientMessage' -> 'sendSystemMessage'
                    player.sendSystemMessage(Component
                            .translatableWithFallback("commands.reload.reload_needed", "You may need to restart the world (if there are datapacks that require it)")
                            .withStyle(ChatFormatting.RED), true
                    )
            );
        }
    }
}

package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

//? >=1.21.11 {
/*import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow public abstract CompletableFuture<Void> reloadResources(Collection<String> dataPacks);

    @Shadow @Final protected LevelStorageSource.LevelStorageAccess storageSource;
    @Shadow @Final private PackRepository packRepository;

    @Inject(method="<init>", at=@At("TAIL"))
    public void reload(CallbackInfo ci) {
        reloadResources(packRepository.getSelectedIds());
    }

    @Inject(method = "reloadResources", at = @At(value = "HEAD"))
    private void copyDatapacksInGame(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (packRepository != null) {
            Path path = this.storageSource.getLevelPath(LevelResource.DATAPACK_DIR);
            Collection<String> enabled = packRepository.getSelectedIds();

            SimpleDatapacks.copyDatapacks(path, new ArrayList<>(enabled));
        }
    }

    @Redirect(method = "configurePackRepository", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", ordinal = 1))
    private static boolean preventAutoLoading(Set<String> packs, Object pack) {
        String packName = (String) pack;

        if ((Compat.isModLoaded(SimpleDatapacks.clothConfigId) && Configs.getInstance().globalDatapacks) || !packName.startsWith("file/")) return packs.add(packName);
        return false;
    }
}

*///?} <=1.21.10 {
import net.minecraft.resource.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow public abstract CompletableFuture<Void> reloadResources(Collection<String> dataPacks);

    @Shadow @Final protected LevelStorage.Session session;
    @Shadow @Final private ResourcePackManager dataPackManager;

    @Inject(method="<init>", at=@At("TAIL"))
    public void reload(CallbackInfo ci) {
        /*? >=1.20.5*/ reloadResources(dataPackManager.getEnabledIds());
        /*? <=1.20.4*/ /*reloadResources(dataPackManager.getEnabledNames());*/
    }

    @Inject(method = "reloadResources", at = @At(value = "HEAD"))
    private void copyDatapacksInGame(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (dataPackManager != null) {
            Path path = this.session.getDirectory(WorldSavePath.DATAPACKS);
            /*? >=1.20.5*/ Collection<String> enabled = dataPackManager.getEnabledIds();
            /*? <=1.20.4*/ /*Collection<String> enabled = dataPackManager.getEnabledNames();*/

            SimpleDatapacks.copyDatapacks(path, new ArrayList<>(enabled));
        }
    }

    @Redirect(method = "loadDataPacks", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", ordinal = 1))
    private static boolean preventAutoLoading(Set<String> packs, Object pack) {
        String packName = (String) pack;

        if ((Compat.isModLoaded(SimpleDatapacks.clothConfigId) && Configs.getInstance().globalDatapacks) || !packName.startsWith("file/")) return packs.add(packName);
        return false;
    }
}//?}

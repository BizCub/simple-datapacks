package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import net.minecraft.resource.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Shadow @Final protected LevelStorage.Session session;
    @Shadow @Final private ResourcePackManager dataPackManager;

    @Inject(method = "reloadResources", at = @At(value = "HEAD"))
    private void copyDatapacksInGame(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (dataPackManager != null) {
            Path path = this.session.getDirectory(WorldSavePath.DATAPACKS);
            /*? >=1.20.5*/ Collection<String> enabled = dataPackManager.getEnabledIds();
            /*? <1.20.5*/ /*Collection<String> enabled = dataPackManager.getEnabledNames();*/

            SimpleDatapacks.copyDatapacks(path, new ArrayList<>(enabled));
        }
    }

    @Redirect(method = "loadDataPacks", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", ordinal = 1))
    private static boolean preventAutoLoading(Set<String> packs, Object pack) {
        String packName = (String) pack;
        if (packName.startsWith("file/")) return false;
        else return packs.add(packName);
    }
}

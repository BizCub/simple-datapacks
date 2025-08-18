package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
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
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Shadow @Final protected LevelStorage.Session session;
    @Shadow @Final private ResourcePackManager dataPackManager;

    @Inject(method = "reloadResources", at = @At(value = "HEAD"))
    private void reloadResources(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (dataPackManager != null) {
            //? if >=1.20.5 {
            /*Collection<String> enabled = dataPackManager.getEnabledIds();
             *///?} else {
            Collection<String> enabled = dataPackManager.getEnabledNames();//?}

            if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
                for (String str : Configs.getInstance().datapacksPaths) {
                    SimpleDatapacks.copyDatapacks(Path.of(str), this.session.getDirectory(WorldSavePath.DATAPACKS), new ArrayList<>(enabled));
                }
            }
        }
    }
}

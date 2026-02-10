package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow @Final protected LevelStorageSource.LevelStorageAccess storageSource;
    @Shadow @Final private PackRepository packRepository;

    @Shadow public abstract CompletableFuture<Void> reloadResources(Collection<String> dataPacks);

    @Inject(method = "<init>", at = @At("TAIL"))
    public void reloadPacks(CallbackInfo ci) {
        reloadResources(packRepository.getSelectedIds());
    }

    @Inject(method = "reloadResources", at = @At("HEAD"))
    private void copyDatapacks(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (packRepository != null) {
            Path path = storageSource.getLevelPath(LevelResource.DATAPACK_DIR);
            Collection<String> enabled = packRepository.getSelectedIds();
            SimpleDatapacks.copyDatapacks(path, new ArrayList<>(enabled));
        }
    }

    @Redirect(method = "configurePackRepository", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", ordinal = 1))
    private static boolean preventAutoLoading(Set<String> packs, Object pack) {
        String packName = (String) pack;

        if (!packName.startsWith("file/")) {
            return packs.add(packName);
        }

        if (Compat.isClothConfigLoaded()) {
            for (String path : Configs.getInstance().requiredDatapacksPaths) {
                File[] files = new File(path).listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().equals(packName.substring(5))) {
                            return packs.add(packName);
                        }
                    }
                }
            }
        }

        return false;
    }
}

package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.resource.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.nio.file.Path;

@Mixin(VanillaDataPackProvider.class)
public abstract class VanillaDataPackProviderMixin {

    //? if >=1.20.2 {
    /*@ModifyArg(method = "createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;"), index = 0)
    *///?} else {
    @ModifyArg(method = "createManager(Lnet/minecraft/world/level/storage/LevelStorage$Session;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Ljava/nio/file/Path;)Lnet/minecraft/resource/ResourcePackManager;"), index = 0)
    //?}
    private static Path datapackDirProvider(Path dataPacksPath) {
        return SimpleDatapacks.datapacksFolder;
    }

    //? if >=1.20.2 {
    /*@ModifyArg(method = "createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/FileResourcePackProvider;<init>(Ljava/nio/file/Path;Lnet/minecraft/resource/ResourceType;Lnet/minecraft/resource/ResourcePackSource;Lnet/minecraft/util/path/SymlinkFinder;)V"), index = 2)
    *///?} else {
    @ModifyArg(method = "createManager(Ljava/nio/file/Path;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/FileResourcePackProvider;<init>(Ljava/nio/file/Path;Lnet/minecraft/resource/ResourceType;Lnet/minecraft/resource/ResourcePackSource;)V"), index = 2)
    //?}
    private static ResourcePackSource createManager1(ResourcePackSource resourcePackSource) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            if (!Configs.getInstance().worldText) return ResourcePackSource.NONE;
        }
        return ResourcePackSource.WORLD;
    }
}

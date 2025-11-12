package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

//? >=1.21.11 {
/*import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.validation.DirectoryValidator;

@Mixin(ServerPacksSource.class)
public abstract class VanillaDataPackProviderMixin {

    @Unique private static DirectoryValidator simpleDatapacks$symlinkFinder;

    @Inject(method = "createPackRepository(Ljava/nio/file/Path;Lnet/minecraft/world/level/validation/DirectoryValidator;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At(value = "HEAD"))
    private static void getSymlinkFinder(Path path, DirectoryValidator directoryValidator, CallbackInfoReturnable<PackRepository> cir) {
        VanillaDataPackProviderMixin.simpleDatapacks$symlinkFinder = directoryValidator;
    }

    @ModifyArg(method = "createPackRepository(Ljava/nio/file/Path;Lnet/minecraft/world/level/validation/DirectoryValidator;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private static RepositorySource[] addProviders(RepositorySource[] par1) {
        return AddProviders.add(par1, simpleDatapacks$symlinkFinder);
    }

    @Inject(method = "createBuiltinPack", at = @At(value = "HEAD"), cancellable = true)
    private void removeFeatures(CallbackInfoReturnable<Pack> cir) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            if (!Configs.getInstance().showFeatures) cir.cancel();
        }
    }
}

*///?} <=1.21.10 {
import net.minecraft.resource.*;
/*? >=1.20.2*/ import net.minecraft.util.path.SymlinkFinder;

@Mixin(VanillaDataPackProvider.class)
public abstract class VanillaDataPackProviderMixin {

    //? >=1.20.2 {
    @Unique private static SymlinkFinder simpleDatapacks$symlinkFinder;

    @Inject(method = "createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "HEAD"))
    private static void getSymlinkFinder(Path dataPacksPath, SymlinkFinder symlinkFinder, CallbackInfoReturnable<ResourcePackManager> cir) {
        VanillaDataPackProviderMixin.simpleDatapacks$symlinkFinder = symlinkFinder;
    }

    @ModifyArg(method = "createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"))
    private static ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {
        return AddProviders.add(par1, simpleDatapacks$symlinkFinder);
    }

    //?} >=1.19.4 {
    /*@ModifyArg(method = "createManager(Ljava/nio/file/Path;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"))
    private static ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {
        return AddProviders.add(par1);
    }*///?}

    @Inject(method = "create", at = @At(value = "HEAD"), cancellable = true)
    private void removeFeatures(CallbackInfoReturnable<ResourcePackProfile> cir) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            if (!Configs.getInstance().showFeatures) cir.cancel();
        }
    }
}//?}

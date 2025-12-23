package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

import net.minecraft.server.packs.repository.*;
/*? >=1.20.2*/ import net.minecraft.world.level.validation.DirectoryValidator;

@Mixin(ServerPacksSource.class)
public abstract class VanillaDataPackProviderMixin {

    //? >=1.20.2 {
    @Unique private static DirectoryValidator simpleDatapacks$symlinkFinder;

    @Inject(method = "createPackRepository(Ljava/nio/file/Path;Lnet/minecraft/world/level/validation/DirectoryValidator;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At(value = "HEAD"))
    private static void getSymlinkFinder(Path path, DirectoryValidator directoryValidator, CallbackInfoReturnable<PackRepository> cir) {
        VanillaDataPackProviderMixin.simpleDatapacks$symlinkFinder = directoryValidator;
    }

    @ModifyArg(method = "createPackRepository(Ljava/nio/file/Path;Lnet/minecraft/world/level/validation/DirectoryValidator;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private static RepositorySource[] addProviders(RepositorySource[] par1) {
        return AddProviders.add(par1, simpleDatapacks$symlinkFinder);
    }

    //?} >=1.19.4 {
    /*@ModifyArg(method = "createPackRepository(Ljava/nio/file/Path;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private static RepositorySource[] addProviders(RepositorySource[] par1) {
        return AddProviders.add(par1);
    }*///?}

    @Inject(method = "createBuiltinPack", at = @At(value = "HEAD"), cancellable = true)
    private void removeFeatures(CallbackInfoReturnable<Pack> cir) {
        if (Compat.isClothConfigLoaded()) {
            if (!Configs.getInstance().showFeatures) cir.cancel();
        }
    }
}

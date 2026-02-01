package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPacksSource.class)
public class ServerPacksSourceMixin {

    @ModifyArg(method = "createPackRepository", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private static RepositorySource[] addProviders(RepositorySource[] args) {
        args = AddProviders.add(args, false);
        args = AddProviders.add(args, true);
        return args;
    }

    @Inject(method = "createBuiltinPack", at = @At("HEAD"), cancellable = true)
    private void removeFeatures(CallbackInfoReturnable<Pack> cir) {
        if (Compat.isClothConfigLoaded() && !Configs.getInstance().enableFeatures) cir.cancel();
    }
}

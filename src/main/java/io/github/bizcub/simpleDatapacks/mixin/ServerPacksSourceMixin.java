package io.github.bizcub.simpleDatapacks.mixin;

import io.github.bizcub.simpleDatapacks.config.Config;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.ServerPacksSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPacksSource.class)
public class ServerPacksSourceMixin {

    @Inject(method = "createBuiltinPack", at = @At("HEAD"), cancellable = true)
    private void sd$removeFeatures(CallbackInfoReturnable<Pack> cir) {
        if (!Config.get().enableFeatures()) cir.cancel();
    }
}

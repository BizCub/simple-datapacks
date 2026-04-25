package com.bizcub.simpleDatapacks.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? >=1.19.3 {
import com.bizcub.simpleDatapacks.Main;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.ServerPacksSource;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPacksSource.class)
public class ServerPacksSourceMixin {

    @Inject(method = "createBuiltinPack", at = @At("HEAD"), cancellable = true)
    private void removeFeatures(CallbackInfoReturnable<Pack> cir) {
        if (!Main.getConfig().enableFeatures()) cir.cancel();
    }
}

//?} else {
/*@Mixin(value = {})
public class ServerPacksSourceMixin {

}*///?}

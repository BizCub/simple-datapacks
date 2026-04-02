package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.Main;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FeatureFlagRegistry.Builder.class)
public class FeatureFlagsMixin {

    @Inject(method = "createVanilla", at = @At("HEAD"))
    private void createVanilla(String name, CallbackInfoReturnable<FeatureFlag> cir) {
        if (!name.equals("vanilla")) {
            Main.features.add(name);
        }
    }
}

package com.bizcub.simpleDatapacks.mixin;

import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.world.SaveProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {

    @Inject(method = "findNewDataPacks", at = @At("RETURN"), cancellable = true)
    private static void findNewDataPacks(ResourcePackManager dataPackManager, SaveProperties saveProperties, Collection<String> enabledDataPacks, CallbackInfoReturnable<Collection<String>> cir) {
        cir.setReturnValue(enabledDataPacks);
    }
}

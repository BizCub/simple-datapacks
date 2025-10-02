package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
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

    @Inject(
            /*? >=1.17*/ method = "findNewDataPacks",
            /*? 1.16.5*/ /*method = "method_29478",*/
            at = @At("RETURN"),
            cancellable = true)
    private static void preventAutoLoading(ResourcePackManager dataPackManager, SaveProperties saveProperties, Collection<String> enabledDataPacks, CallbackInfoReturnable<Collection<String>> cir) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId) && Configs.getInstance().globalDatapacks) return;
        cir.setReturnValue(enabledDataPacks);
    }
}

package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.WorldData;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {

    @Inject(method = "discoverNewPacks", at = @At("RETURN"), cancellable = true)
    private static void preventAutoLoading(PackRepository packRepository, WorldData worldData, Collection<String> enabledDataPacks, CallbackInfoReturnable<Collection<String>> cir) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId) && Configs.getInstance().globalDatapacks) return;
        cir.setReturnValue(enabledDataPacks);
    }
}

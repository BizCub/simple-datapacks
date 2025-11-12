package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

//? >=1.21.11 {
/*import net.minecraft.server.commands.ReloadCommand;
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

*///?} <=1.21.10 {
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.world.SaveProperties;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {

    @Inject(method = "findNewDataPacks", at = @At("RETURN"), cancellable = true)
    private static void preventAutoLoading(ResourcePackManager dataPackManager, SaveProperties saveProperties, Collection<String> enabledDataPacks, CallbackInfoReturnable<Collection<String>> cir) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId) && Configs.getInstance().globalDatapacks) return;
        cir.setReturnValue(enabledDataPacks);
    }
}//?}

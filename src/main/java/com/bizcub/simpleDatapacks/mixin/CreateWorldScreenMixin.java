package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import com.bizcub.simpleDatapacks.SimpleDatapacks;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
/*? >=1.19.4*/ import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    /*? >=1.19.4*/ @Final @Shadow WorldCreator worldCreator;
    /*? <=1.19.3*/ /*@Shadow String saveDirectoryName;*/
    @Shadow private ResourcePackManager packManager;

    @Inject(method = "clearDataPackTempDir", at = @At(value = "TAIL"))
    private void copyDatapacksBeforeGame(CallbackInfo ci) {
        if (packManager != null) {
            /*? >=1.19.4*/ Path path = SimpleDatapacks.minecraftFolder.resolve("saves").resolve(worldCreator.getWorldDirectoryName()).resolve("datapacks");
            /*? <=1.19.3*/ /*Path path = SimpleDatapacks.minecraftFolder.resolve("saves").resolve(saveDirectoryName).resolve("datapacks");*/

            /*? >=1.20.5*/ List<String> datapacks = new ArrayList<>(packManager.getEnabledIds());
            /*? <=1.20.4*/ /*List<String> datapacks = new ArrayList<>(packManager.getEnabledNames());*/

            SimpleDatapacks.copyDatapacks(path, datapacks);
        }
    }

    //? >=1.17 && <=1.19.3 {
    /*@ModifyArg(method = "getScannedPack", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>(Lnet/minecraft/resource/ResourceType;[Lnet/minecraft/resource/ResourcePackProvider;)V"))
    private ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {
        return AddProviders.add(par1);
    }

    *///?} <=1.16.5 {
    /*@ModifyArg(method = "method_30296", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"))
    private ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {
        return AddProviders.add(par1);
    }*///?}
}

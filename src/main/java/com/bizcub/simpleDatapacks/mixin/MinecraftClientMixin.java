package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    //? <=1.18.2 {
    /*/^? 1.18.2^/ /^@ModifyArg(method = "createServerDataManager", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>(Lnet/minecraft/resource/ResourceType;[Lnet/minecraft/resource/ResourcePackProvider;)V"))^/
    /^? >=1.17 && <=1.18.1^/ /^@ModifyArg(method = "createIntegratedResourceManager", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>(Lnet/minecraft/resource/ResourceType;[Lnet/minecraft/resource/ResourcePackProvider;)V"))^/
    /^? 1.16.5^/ /^@ModifyArg(method = "method_29604", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"))^/

    /^? 1.18.2^/ /^private static ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {^/
    /^? <=1.18.1^/ /^private ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {^/
        return AddProviders.add(par1);
    }*///?}
}

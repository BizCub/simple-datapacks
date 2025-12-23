package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.WorldData;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {

    @Unique private static boolean simpleDatapacks$shouldSend = false;

    @Inject(method = "discoverNewPacks", at = @At("RETURN"), cancellable = true)
    private static void preventAutoLoading(PackRepository packRepository, WorldData worldData, Collection<String> enabledDataPacks, CallbackInfoReturnable<Collection<String>> cir) {
        if (Compat.isClothConfigLoaded() && Configs.getInstance().globalDatapacks) return;
        cir.setReturnValue(enabledDataPacks);
    }

    @Inject(method = "reloadPacks", at = @At(value = "HEAD"))
    private static void shouldSend(CallbackInfo ci) {
        simpleDatapacks$shouldSend = true;
    }

    @Inject(method = "register", at = @At("HEAD"))
    private static void sendMessage(CallbackInfo ci) {
        if (simpleDatapacks$shouldSend && !(Compat.isClothConfigLoaded() && !Configs.getInstance().sendRestartWarning)) {
            var player = Minecraft.getInstance().player;
            assert player != null;
            player.displayClientMessage(Component.translatable("commands.reload.reload_needed").withStyle(ChatFormatting.RED), true);
            simpleDatapacks$shouldSend = false;
        }
    }
}

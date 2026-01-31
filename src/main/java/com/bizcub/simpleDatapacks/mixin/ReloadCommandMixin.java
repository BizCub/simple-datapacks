package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.ReloadCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {

    @Unique private static boolean sd$shouldSend = false;
    @Unique private static CommandSourceStack sd$sourceStack;

    @Inject(method = "reloadPacks", at = @At("HEAD"))
    private static void shouldSend(Collection<String> collection, CommandSourceStack commandSourceStack, CallbackInfo ci) {
        sd$shouldSend = true;
        sd$sourceStack = commandSourceStack;
    }

    @Inject(method = "register", at = @At("HEAD"))
    private static void sendMessage(CallbackInfo ci) {
        if (sd$shouldSend && !(Compat.isClothConfigLoaded() && !Configs.getInstance().sendRestartWarning)) {
            var playerlist = sd$sourceStack.getServer().getPlayerList();
            playerlist.getPlayers().forEach(player ->
                    player.displayClientMessage(Component.translatable("commands.reload.reload_needed").withStyle(ChatFormatting.RED), true));
            sd$shouldSend = false;
        }
    }
}

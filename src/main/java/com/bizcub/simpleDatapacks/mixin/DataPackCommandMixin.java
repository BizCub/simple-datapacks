package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.Main;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.DataPackCommand;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DataPackCommand.class)
public class DataPackCommandMixin {

    @Inject(method = "disablePack", at = @At("HEAD"))
    private static void getDisabledPackId(CommandSourceStack source, Pack unopened, CallbackInfoReturnable<Integer> cir) {
        Main.disabledDatapack = unopened.getId();
    }
}

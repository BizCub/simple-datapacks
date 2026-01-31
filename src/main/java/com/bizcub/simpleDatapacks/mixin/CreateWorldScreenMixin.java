package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import net.minecraft.server.packs.repository.RepositorySource;
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

import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.server.packs.repository.PackRepository;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    @Final @Shadow WorldCreationUiState uiState;
    @Shadow private PackRepository tempDataPackRepository;

    @Inject(method = "removeTempDataPackDir", at = @At("TAIL"))
    private void copyDatapacksBeforeGameStart(CallbackInfo ci) {
        if (tempDataPackRepository != null) {
            Path path = SimpleDatapacks.minecraftFolder.resolve("saves").resolve(uiState.getName()).resolve("datapacks");
            List<String> datapacks = new ArrayList<>(tempDataPackRepository.getSelectedIds());
            SimpleDatapacks.copyDatapacks(path, datapacks);
        }
    }

    @ModifyArg(method = "openCreateWorldScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private static RepositorySource[] addProviders(RepositorySource[] args) {
        if (Compat.isClothConfigLoaded()) return AddProviders.add(args, true);
        return args;
    }
}

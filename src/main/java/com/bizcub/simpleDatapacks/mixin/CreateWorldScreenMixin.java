package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.server.packs.repository.PackRepository;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    @Final @Shadow WorldCreationUiState uiState;
    @Shadow private PackRepository tempDataPackRepository;

    @Inject(method = "removeTempDataPackDir", at = @At(value = "TAIL"))
    private void copyDatapacksBeforeGame(CallbackInfo ci) {
        if (tempDataPackRepository != null) {
            Path path = SimpleDatapacks.minecraftFolder.resolve("saves").resolve(uiState.getName()).resolve("datapacks");
            List<String> datapacks = new ArrayList<>(tempDataPackRepository.getSelectedIds());
            SimpleDatapacks.copyDatapacks(path, datapacks);
        }
    }
}

package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.WorldDataConfiguration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
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

    @ModifyArg(method = "openCreateWorldScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;createDefaultLoadConfig(Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/world/level/WorldDataConfiguration;)Lnet/minecraft/server/WorldLoader$InitConfig;"))
    private static WorldDataConfiguration addGlobalFeatures(WorldDataConfiguration worldDataConfiguration) {
        if (Compat.isClothConfigLoaded() && Configs.getInstance().globalFeatures) {
            List<String> features = new ArrayList<>(worldDataConfiguration.dataPacks().getEnabled());
            features.addAll(SimpleDatapacks.features);
            return new WorldDataConfiguration(new DataPackConfig(features, List.of()), FeatureFlags.DEFAULT_FLAGS);
        } return worldDataConfiguration;
    }

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

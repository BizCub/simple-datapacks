package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.AddProviders;
import com.bizcub.simpleDatapacks.Main;
import com.bizcub.simpleDatapacks.config.Compat;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.WorldDataConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.util.ArrayList;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    @ModifyArg(method = "openCreateWorldScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;createDefaultLoadConfig(Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/world/level/WorldDataConfiguration;)Lnet/minecraft/server/WorldLoader$InitConfig;"))
    private static WorldDataConfiguration addGlobalFeatures(WorldDataConfiguration worldDataConfiguration) {
        if (Main.getConfig().globalFeatures()) {
            List<String> features = new ArrayList<>(worldDataConfiguration.dataPacks().getEnabled());
            features.addAll(Main.features);
            return new WorldDataConfiguration(new DataPackConfig(features, List.of()), FeatureFlags.DEFAULT_FLAGS);
        }
        return worldDataConfiguration;
    }

    @ModifyArg(method = "openCreateWorldScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private static RepositorySource[] addProviders(RepositorySource[] args) {
        if (Compat.isClothConfigLoaded()) return AddProviders.add(args, true);
        return args;
    }
}

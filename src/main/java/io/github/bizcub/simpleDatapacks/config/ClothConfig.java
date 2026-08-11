package io.github.bizcub.simpleDatapacks.config;

import io.github.bizcub.simpleDatapacks.Main;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import java.util.List;

@me.shedaniel.autoconfig.annotation.Config(name = Main.MOD_ID)
public class ClothConfig implements Config, ConfigData {

    public static ClothConfig getInstance() {
        return AutoConfig.register(ClothConfig.class, GsonConfigSerializer::new).getConfig();
    }

    @Tooltip
    public boolean copyDatapacks = Config.super.copyDatapacks();

    @Tooltip
    public boolean shouldApplyRequiredPacksToExistingWorld = Config.super.shouldApplyRequiredPacksToExistingWorld();

    @Tooltip
    public boolean enableFeatures = Config.super.enableFeatures();

    @Tooltip(count = 2)
    public boolean globalFeatures = Config.super.globalFeatures();

    @Tooltip
    public boolean sendRestartWarning = Config.super.sendRestartWarning();

    public List<String> requiredDatapacksPaths = Config.super.requiredDatapacksPaths();

    public List<String> optionalDatapacksPaths = Config.super.optionalDatapacksPaths();

    @Override
    public boolean copyDatapacks() {
        return this.copyDatapacks;
    }

    @Override
    public boolean shouldApplyRequiredPacksToExistingWorld() {
        return this.shouldApplyRequiredPacksToExistingWorld;
    }

    @Override
    public boolean enableFeatures() {
        return this.enableFeatures;
    }

    @Override
    public boolean globalFeatures() {
        return this.globalFeatures;
    }

    @Override
    public boolean sendRestartWarning() {
        return this.sendRestartWarning;
    }

    @Override
    public List<String> requiredDatapacksPaths() {
        return this.requiredDatapacksPaths;
    }

    @Override
    public List<String> optionalDatapacksPaths() {
        return this.optionalDatapacksPaths;
    }
}

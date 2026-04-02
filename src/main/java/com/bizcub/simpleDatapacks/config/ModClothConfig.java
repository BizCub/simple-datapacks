package com.bizcub.simpleDatapacks.config;

import com.bizcub.simpleDatapacks.Main;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import java.util.List;

@Config(name = Main.MOD_ID)
public class ModClothConfig implements ModConfig, ConfigData {

    public static ModClothConfig getInstance() {
        return AutoConfig.register(ModClothConfig.class, GsonConfigSerializer::new).getConfig();
    }

    @Tooltip public boolean copyDatapacks = ModConfig.super.copyDatapacks();
    @Tooltip public boolean enableFeatures = ModConfig.super.enableFeatures();
    @Tooltip(count = 2) public boolean globalFeatures = ModConfig.super.globalFeatures();
    @Tooltip public boolean sendRestartWarning = ModConfig.super.sendRestartWarning();

    public List<String> requiredDatapacksPaths = ModConfig.super.requiredDatapacksPaths();
    public List<String> optionalDatapacksPaths = ModConfig.super.optionalDatapacksPaths();

    @Override
    public boolean copyDatapacks() {
        return this.copyDatapacks;
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

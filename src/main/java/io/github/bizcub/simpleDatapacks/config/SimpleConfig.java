package io.github.bizcub.simpleDatapacks.config;

import io.github.bizcub.simpleConfigLib.autoconfig.ConfigHolder;
import io.github.bizcub.simpleConfigLib.autoconfig.annotation.*;
import io.github.bizcub.simpleDatapacks.Main;

import java.util.List;

@AutoConfig(name = Main.MOD_ID, translate = true)
public class SimpleConfig implements Config {

    public static ConfigHolder<SimpleConfig> getInstance() {
        return ConfigHolder.register(SimpleConfig.class);
    }
    
    @Tooltip
    public boolean copyDatapacks = Config.super.copyDatapacks();

    @Tooltip
    public boolean shouldApplyRequiredPacksToExistingWorld = Config.super.shouldApplyRequiredPacksToExistingWorld();

    @Tooltip
    public boolean enableFeatures = Config.super.enableFeatures();

    @Tooltip
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

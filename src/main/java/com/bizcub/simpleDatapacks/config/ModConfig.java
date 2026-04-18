package com.bizcub.simpleDatapacks.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ModConfig {
    ModConfig CONFIG = Compat.isClothConfigLoaded() ? ModClothConfig.getInstance() : new ModConfig() { };

    default boolean copyDatapacks() {
        return false;
    }

    default boolean shouldApplyRequiredPacksToExistingWorld() {
        return false;
    }

    default boolean enableFeatures() {
        return true;
    }

    default boolean globalFeatures() {
        return false;
    }

    default boolean sendRestartWarning() {
        return true;
    }

    default List<String> requiredDatapacksPaths() {
        return new ArrayList<>();
    }

    default List<String> optionalDatapacksPaths() {
        return new ArrayList<>(Arrays.asList("datapacks", System.getProperty("user.home") + "\\Downloads"));
    }
}

package io.github.bizcub.simpleDatapacks.config;

import io.github.bizcub.simpleConfigLib.autoconfig.ConfigProvider;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Config {
    static Config get() {
        return ConfigProvider.get(Config.class);
    }
    static void set(Config instance) {
        ConfigProvider.set(Config.class, instance);
    }

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
        return new ArrayList<>(Arrays.asList(
                "datapacks",
                Paths.get(System.getProperty("user.home"), "Downloads").toString()
        ));
    }
}

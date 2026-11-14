package io.github.bizcub.simpleDatapacks.config;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Config {
    static Config get() {
        return Holder.INSTANCE;
    }

    static void set(final Config config) {
        if (config != null) {
            Holder.INSTANCE = config;
        }
    }

    static boolean isLoaded() {
        return Holder.INSTANCE != Holder.DEFAULT;
    }

    class Holder {
        private static final Config DEFAULT = new Config() { };
        private static Config INSTANCE = DEFAULT;
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

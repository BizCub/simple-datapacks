package io.github.bizcub.simpleDatapacks.config;

import io.github.bizcub.simpleDatapacks.Main;
import io.github.bizcub.simpleConfigLib.autoconfig.gui.ConfigScreens;
import net.minecraft.client.gui.screens.Screen;

public class ConfigHelper {
    public static final String SIMPLE_CONFIG_MOD_ID = "simple_config_lib";

    public static boolean isConfigLoaded() {
        return Config.isLoaded();
    }

    public static Screen getScreen(Screen parent) {
        return isConfigLoaded() ? ConfigScreens.open(Main.MOD_ID, parent) : parent;
    }
}

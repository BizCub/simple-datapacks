package io.github.bizcub.simpleDatapacks.config;

import io.github.bizcub.simpleDatapacks.Main;
import io.github.bizcub.simpleConfigLib.autoconfig.gui.ConfigScreens;
import io.github.bizcub.simpleConfigLib.util.Platform;
import net.minecraft.client.gui.screens.Screen;

public class ConfigHelper {
    public static boolean isConfigLoaded() {
        return Platform.isModLoaded("simple_config_lib");
    }

    public static Screen getScreen(Screen parent) {
        return isConfigLoaded() ? ConfigScreens.open(Main.MOD_ID, parent) : parent;
    }
}

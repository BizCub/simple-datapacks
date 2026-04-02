//? fabric {
package com.bizcub.simpleDatapacks.platform;

import com.bizcub.simpleDatapacks.Main;
import com.bizcub.simpleDatapacks.config.Compat;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Main.init(FabricLoader.getInstance().getGameDir());
    }

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return Compat::getScreen;
        }
    }
}//?}

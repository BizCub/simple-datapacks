package io.github.bizcub.simpleDatapacks.fabric;

import io.github.bizcub.simpleDatapacks.Main;
import io.github.bizcub.simpleDatapacks.config.ConfigHelper;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Main.init(FabricLoader.getInstance().getGameDir(), FabricLoader.getInstance().isModLoaded(ConfigHelper.SIMPLE_CONFIG_MOD_ID));
    }

    public static class ModMenu implements ModMenuApi {

        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return ConfigHelper::getScreen;
        }
    }
}

package io.github.bizcub.simpleDatapacks.forge;

import io.github.bizcub.simpleDatapacks.Main;
import io.github.bizcub.simpleDatapacks.config.ConfigHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Main.MOD_ID)
public class Forge {

    public Forge() {
        Main.init(FMLPaths.GAMEDIR.get(), ModList.isLoaded(ConfigHelper.SIMPLE_CONFIG_MOD_ID));

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> ConfigHelper.getScreen(screen)));
    }
}

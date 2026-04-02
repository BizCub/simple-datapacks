//? forge {
/*package com.bizcub.simpleDatapacks.platform;

import com.bizcub.simpleDatapacks.Main;
import com.bizcub.simpleDatapacks.config.Compat;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Main.MOD_ID)
public class Forge {

    public Forge() {
        Main.init(FMLPaths.GAMEDIR.get());

        //? is_cloth_config_available {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) ->
                        Compat.getScreen(screen)
        ));//?}
    }
}*///?}

//? forge {
/*package com.bizcub.simpleDatapacks.platform;

import com.bizcub.simpleDatapacks.Main;
import com.bizcub.simpleDatapacks.config.Compat;
/^? >=1.19^/ import net.minecraftforge.client.ConfigScreenHandler;
/^? >=1.18 && <=1.18.2^/ //import net.minecraftforge.client.ConfigGuiHandler;
/^? >=1.17 && <=1.17.1^/ //import net.minecraftforge.fmlclient.ConfigGuiHandler;
/^? <=1.16.5^/ //import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Main.MOD_ID)
public class Forge {

    public Forge() {
        Main.init(FMLPaths.GAMEDIR.get());

        //? >=1.19 && <=1.21.3 {
        /^ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) ->
                        Compat.getScreen(screen)
                ));

        ^///?} >=1.17 && <=1.18.2 {
        /^ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () ->
                new ConfigGuiHandler.ConfigGuiFactory((minecraft, screen) ->
                        Compat.getScreen(screen)
                ));

        ^///?} <=1.16.5 {
        /^ModLoadingContext.get().registerExtensionPoint(
                ExtensionPoint.CONFIGGUIFACTORY, () -> (minecraft, screen) ->
                        Compat.getScreen(screen)
        );^///?}
    }
}*///?}

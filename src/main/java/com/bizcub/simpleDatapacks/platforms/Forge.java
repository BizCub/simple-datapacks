//? forge {
/*package com.bizcub.simpleDatapacks.platforms;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(SimpleDatapacks.modId)
public class Forge {

    public Forge() {
        SimpleDatapacks.init(FMLPaths.GAMEDIR.get());

        //? <=1.21.3 {
        /^ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
            return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
                return PlatformInit.getScreen(screen);
            });
        });^///?}
    }
}*///?}

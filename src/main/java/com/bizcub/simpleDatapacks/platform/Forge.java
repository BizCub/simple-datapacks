//? forge {
/*package com.bizcub.simpleDatapacks.platform;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(SimpleDatapacks.MOD_ID)
public class Forge {

    public Forge() {
        SimpleDatapacks.init(FMLPaths.GAMEDIR.get());

        //? is_cloth_config_available {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
            return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
                return PlatformInit.getScreen(screen);
            });
        });//?}
    }
}*///?}

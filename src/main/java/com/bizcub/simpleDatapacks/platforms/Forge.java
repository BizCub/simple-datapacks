//? if forge {
/*package com.bizcub.simpleDatapacks.platforms;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Configs;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(SimpleDatapacks.modId)
public class Forge {

    public Forge() {
        SimpleDatapacks.init(FMLPaths.GAMEDIR.get());

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
            return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
                return AutoConfig.getConfigScreen(Configs.class, screen).get();
            });
        });
    }
}*///?}

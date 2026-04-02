//? neoforge {
/*package com.bizcub.simpleDatapacks.platform;

import com.bizcub.simpleDatapacks.Main;
import com.bizcub.simpleDatapacks.config.Compat;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(Main.MOD_ID)
public class NeoForge {

    public NeoForge() {
        Main.init(FMLPaths.GAMEDIR.get());

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class, () -> (minecraft, screen) ->
                        Compat.getScreen(screen)
        );
    }
}*///?}

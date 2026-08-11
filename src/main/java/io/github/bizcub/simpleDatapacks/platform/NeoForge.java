//? neoforge {
/*package io.github.bizcub.simpleDatapacks.platform;

import io.github.bizcub.simpleDatapacks.Main;
import io.github.bizcub.simpleDatapacks.config.ConfigHelper;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(Main.MOD_ID)
public class NeoForge {

    public NeoForge() {
        Main.init(FMLPaths.GAMEDIR.get());

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
            (container, parent) -> ConfigHelper.getScreen(parent));
    }
}*///?}

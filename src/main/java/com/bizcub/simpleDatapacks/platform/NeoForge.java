//? neoforge {
/*package com.bizcub.simpleDatapacks.platform;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(SimpleDatapacks.MOD_ID)
public class NeoForge {

    public NeoForge() {
        SimpleDatapacks.init(FMLPaths.GAMEDIR.get());

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
            (container, parent) -> PlatformInit.getScreen(parent));
    }
}*///?}

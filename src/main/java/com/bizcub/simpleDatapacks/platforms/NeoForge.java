//? neoforge {
/*package com.bizcub.simpleDatapacks.platforms;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(SimpleDatapacks.modId)
public class NeoForge {

    public NeoForge() {
        SimpleDatapacks.init(FMLPaths.GAMEDIR.get());

        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (container, parent) -> {
                return AutoConfig.getConfigScreen(Configs.class, parent).get();
            });
        }
    }
}*///?}

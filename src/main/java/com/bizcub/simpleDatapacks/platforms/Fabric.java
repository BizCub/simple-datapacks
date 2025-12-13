//? fabric {
package com.bizcub.simpleDatapacks.platforms;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Fabric implements ModInitializer, ModMenuApi {

    @Override
    public void onInitialize() {
        SimpleDatapacks.init(FabricLoader.getInstance().getGameDir());
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return PlatformInit::getScreen;
    }
}//?}

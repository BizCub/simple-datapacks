//? if fabric {
package com.bizcub.simpleDatapacks.platforms;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SimpleDatapacks.init(FabricLoader.getInstance().getGameDir());
    }
}
//?}

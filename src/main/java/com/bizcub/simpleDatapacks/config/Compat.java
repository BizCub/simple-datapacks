package com.bizcub.simpleDatapacks.config;

//? if fabric {
import net.fabricmc.loader.api.FabricLoader;
//?} elif forge {
/*import net.minecraftforge.fml.ModList;
*///?} elif neoforge {
/*import net.neoforged.fml.ModList;*///?}

public class Compat {

    public static boolean isModLoaded(String modId) {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(modId);
        //?} elif forge || neoforge {
        /*return ModList.get().isLoaded(modId);*///?}
    }
}

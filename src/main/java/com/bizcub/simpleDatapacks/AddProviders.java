package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class AddProviders {

    //? >=1.19.4 && <=1.20.1 {
    public static ResourcePackProvider[] add(ResourcePackProvider[] arg) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(new FileResourcePackProvider(Paths.get(path), ResourceType.SERVER_DATA, ResourcePackSource.NONE));
            }
        } else providedDatapacks.add(new FileResourcePackProvider(Paths.get("datapacks"), ResourceType.SERVER_DATA, ResourcePackSource.NONE));
        return providedDatapacks.toArray(new ResourcePackProvider[0]);
    }

    //?} >=1.17 && <=1.19.3 {
    /*public static ResourcePackProvider[] add(ResourcePackProvider[] arg) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(new FileResourcePackProvider(Paths.get(path).toFile(), ResourcePackSource.PACK_SOURCE_NONE));
            }
        } else providedDatapacks.add(new FileResourcePackProvider(Paths.get("datapacks").toFile(), ResourcePackSource.PACK_SOURCE_NONE));
        return providedDatapacks.toArray(new ResourcePackProvider[0]);
    }

    *///?} 1.16.5 {
    /*public static ResourcePackProvider[] add(ResourcePackProvider[] arg) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(new FileResourcePackProvider(Paths.get(path).toFile(), ResourcePackSource.field_25347));
            }
        } else providedDatapacks.add(new FileResourcePackProvider(Paths.get("datapacks").toFile(), ResourcePackSource.field_25347));
        return providedDatapacks.toArray(new ResourcePackProvider[0]);
    }*///?}
}

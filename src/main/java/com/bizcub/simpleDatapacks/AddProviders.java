package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
/*? >=1.20.2*/ import net.minecraft.util.path.SymlinkFinder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class AddProviders {

    //? >=1.20.2 {
    public static ResourcePackProvider[] add(ResourcePackProvider[] arg, SymlinkFinder symlinkFinder) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(addProvider(Paths.get(path), symlinkFinder));
            }
        } else providedDatapacks.add(addProvider(Paths.get("datapacks"), symlinkFinder));
        return providedDatapacks.toArray(new ResourcePackProvider[0]);
    }

    private static FileResourcePackProvider addProvider(Path path, SymlinkFinder symlinkFinder) {
        return new FileResourcePackProvider(path, ResourceType.SERVER_DATA, ResourcePackSource.NONE, symlinkFinder);
    }

    //?} <=1.20.1 {
    /*public static ResourcePackProvider[] add(ResourcePackProvider[] arg) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(addProvider(Paths.get(path)));
            }
        } else providedDatapacks.add(addProvider(Paths.get("datapacks")));
        return providedDatapacks.toArray(new ResourcePackProvider[0]);
    }

    private static FileResourcePackProvider addProvider(Path path) {
        return new FileResourcePackProvider(path, ResourceType.SERVER_DATA, ResourcePackSource.NONE);
    }*///?}
}

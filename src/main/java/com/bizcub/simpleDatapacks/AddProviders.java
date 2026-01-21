package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

public class AddProviders {

    public static RepositorySource[] add(RepositorySource[] arg, boolean required) {
        ArrayList<RepositorySource> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isClothConfigLoaded()) {
            List<String> paths;
            if (required) paths = Configs.getInstance().optionalDatapacksPaths;
            else paths = Configs.getInstance().requiredDatapacksPaths;

            paths.forEach(path -> providedDatapacks.add(addProvider(path)));
        } else
            providedDatapacks.add(addProvider("datapacks"));
        return providedDatapacks.toArray(new RepositorySource[0]);
    }

    private static FolderRepositorySource addProvider(String path) {
        /*? >=1.20.2*/ var validator = Minecraft.getInstance().directoryValidator();
        return new FolderRepositorySource(Paths.get(path), PackType.SERVER_DATA, PackSource.DEFAULT /*? >=1.20.2 {*/, validator /*?}*/);
    }
}

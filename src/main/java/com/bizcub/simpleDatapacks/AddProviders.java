package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
/*? >=1.20.2*/ import net.minecraft.world.level.validation.DirectoryValidator;

public class AddProviders {

    //? >=1.20.2 {
    public static RepositorySource[] add(RepositorySource[] arg, DirectoryValidator symlinkFinder) {
        ArrayList<RepositorySource> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isClothConfigLoaded()) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(addProvider(Paths.get(path), symlinkFinder));
            }
        } else providedDatapacks.add(addProvider(Paths.get("datapacks"), symlinkFinder));
        return providedDatapacks.toArray(new RepositorySource[0]);
    }

    private static FolderRepositorySource addProvider(Path path, DirectoryValidator symlinkFinder) {
        return new FolderRepositorySource(path, PackType.SERVER_DATA, PackSource.DEFAULT, symlinkFinder);
    }

    //?} <=1.20.1 {
    /*public static RepositorySource[] add(RepositorySource[] arg) {
        ArrayList<RepositorySource> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isClothConfigLoaded()) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(addProvider(Paths.get(path)));
            }
        } else providedDatapacks.add(addProvider(Paths.get("datapacks")));
        return providedDatapacks.toArray(new RepositorySource[0]);
    }

    private static FolderRepositorySource addProvider(Path path) {
        return new FolderRepositorySource(path, PackType.SERVER_DATA, PackSource.DEFAULT);
    }*///?}
}

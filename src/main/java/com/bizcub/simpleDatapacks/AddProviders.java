package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
/*? >=1.20.2*/ import net.minecraft.world.level.validation.DirectoryValidator;

public class AddProviders {

    public static RepositorySource[] add(RepositorySource[] arg) {
        ArrayList<RepositorySource> providedDatapacks = new ArrayList<>(Arrays.asList(arg));
        if (Compat.isClothConfigLoaded())
            for (String path : Configs.getInstance().datapacksPaths)
                providedDatapacks.add(addProvider(Paths.get(path)));
        else
            providedDatapacks.add(addProvider(Paths.get("datapacks")));
        return providedDatapacks.toArray(new RepositorySource[0]);
    }

    private static FolderRepositorySource addProvider(Path path) {
        //? >=1.20.2 {
        DirectoryValidator validator = Minecraft.getInstance().directoryValidator();
        return new FolderRepositorySource(path, PackType.SERVER_DATA, PackSource.DEFAULT, validator);
        //?} <=1.20.1 {
        /*return new FolderRepositorySource(path, PackType.SERVER_DATA, PackSource.DEFAULT);*///?}
    }
}

package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SimpleDatapacks {

    public static final String modId = "simple_datapacks";
    //? if fabric {
    public static final String clothConfigId = "cloth-config";
    //?} elif forge || neoforge {
    /*public static final String clothConfigId = "cloth_config";*///?}

    public static Path minecraftFolder;

    public static void init(Path path) throws SecurityException {
        path.toFile().mkdirs();
        minecraftFolder = path;
        if (Compat.isModLoaded(clothConfigId)) Configs.init();
    }

    public static void copyDatapacks(Path src, Path dest, List<String> rawDatapacks) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            if (!Configs.getInstance().copyDatapacks) return;
        } else return;

        List<String> datapacks = new ArrayList<>();
        rawDatapacks.removeIf(str -> !str.startsWith("file/"));
        rawDatapacks.forEach(s -> datapacks.add(s.substring(5)));

        String[] copiedDatapacks = dest.toFile().list();
        if (copiedDatapacks != null) datapacks.removeAll(List.of(copiedDatapacks));

        try {
            for (String str : datapacks) {
                FileUtils.copyDirectory(src.resolve(str).toFile(), dest.resolve(str).toFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

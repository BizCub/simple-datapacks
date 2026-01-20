package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDatapacks {

    public static final String modId = /*$ mod_id*/ "simple_datapacks";
    public static Path minecraftFolder;

    public static void init(Path path) throws SecurityException {
        path.toFile().mkdirs();
        minecraftFolder = path;
        if (Compat.isClothConfigLoaded()) Configs.init();
    }

    public static void copyDatapacks(Path dest, List<String> rawDatapacks) {
        if (!(Compat.isClothConfigLoaded() && Configs.getInstance().copyDatapacks)) return;

        for (String path : Configs.getInstance().datapacksPaths) {
            Path src = Paths.get(path);

            List<String> datapacks = new ArrayList<>();
            rawDatapacks.removeIf(s -> !s.startsWith("file/"));
            rawDatapacks.forEach(s -> datapacks.add(s.substring(5)));

            String[] copiedDatapacks = dest.toFile().list();
            if (copiedDatapacks != null) datapacks.removeAll(Arrays.asList(copiedDatapacks));

            try {
                for (String str : datapacks) {
                    if (!Files.exists(src.resolve(str))) continue;

                    Path pack = src.resolve(str);
                    if (Files.isRegularFile(pack) && pack.toString().endsWith(".zip"))
                        FileUtils.copyFile(pack.toFile(), dest.resolve(str).toFile());
                    else
                        FileUtils.copyDirectory(pack.toFile(), dest.resolve(str).toFile());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

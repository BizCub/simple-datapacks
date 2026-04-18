package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.ModConfig;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final String MOD_ID = /*$ mod_id*/ "simple_datapacks";
    public static boolean initialized = false;
    public static Path minecraftFolder;
    public static List<String> features = new ArrayList<>();

    public static void init(Path path) {
        initialized = true;
        minecraftFolder = path;
        getConfig();
    }

    public static ModConfig getConfig() {
        return ModConfig.CONFIG;
    }

    public static void copyDatapacks(Path dest, List<String> rawDatapacks) {
        if (!(getConfig().copyDatapacks())) return;

        List<String> allPaths = new ArrayList<>();
        allPaths.addAll(getConfig().optionalDatapacksPaths());
        allPaths.addAll(getConfig().requiredDatapacksPaths());

        for (String path : allPaths) {
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

    public static boolean isRequiredDatapack(String checkedDatapack) {
        for (String path : Main.getConfig().requiredDatapacksPaths()) {
            File[] files = new File(path).listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equals(checkedDatapack.substring(5))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

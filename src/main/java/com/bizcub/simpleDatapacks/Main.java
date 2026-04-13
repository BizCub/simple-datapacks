package com.bizcub.simpleDatapacks;

import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.ModConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.FileReader;
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

    public static List<String> getPPDatapackNames() {
        if (!Compat.isModLoaded("packed_packs")) return List.of();

        Path ppFolder = minecraftFolder.resolve("config/packed_packs");
        Path configPath = ppFolder.resolve("config.json");
        Path profilesPath = ppFolder.resolve("profiles/datapacks");
        String lastViewedProfileName = "";

        try (FileReader reader = new FileReader(configPath.toString())) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            var lastViewedProfileNameJson = jsonObject
                    .getAsJsonObject("datapacks")
                    .get("lastViewedProfile");

            if (lastViewedProfileNameJson == null) return List.of();

            lastViewedProfileName = lastViewedProfileNameJson.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(profilesPath + "\\" + lastViewedProfileName.replace("\"", "") + ".profile.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            var test = jsonObject.getAsJsonArray("packIds");

            List<String> ppDatapackNames = new ArrayList<>();
            for (JsonElement element : test)
                ppDatapackNames.add(element.getAsString());

            return ppDatapackNames.stream()
                    .filter(s -> s.contains("file/"))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of();
    }
}

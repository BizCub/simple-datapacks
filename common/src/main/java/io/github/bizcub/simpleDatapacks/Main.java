package io.github.bizcub.simpleDatapacks;

import io.github.bizcub.simpleDatapacks.config.*;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String MOD_ID = "simple_datapacks";
    public static boolean initialized = false;
    public static Path minecraftFolder;
    public static List<String> features = new ArrayList<>();
    public static String disabledDatapack = "";

    public record DatapackSource(Path path, boolean required) {}

    public interface DatapackSources {
        DatapackSource simpleDatapacks$getSource(String id);
    }

    public static void init(Path path, boolean hasSimpleConfig) {
        initialized = true;
        minecraftFolder = path;

        if (hasSimpleConfig) {
            Config.set(SimpleConfig.getInstance().get());
        }
    }

    public static void copyDatapacks(Path dest, PackRepository repository) {
        if (!Config.get().copyDatapacks()) return;

        for (Pack pack : repository.getSelectedPacks()) {
            DatapackSource source = ((DatapackSources) repository).simpleDatapacks$getSource(pack.getId());
            if (source == null) continue;
            Path target = dest.resolve(source.path().getFileName());
            try {
                if (Files.exists(target) && Files.isSameFile(source.path(), target)) continue;
                if (Files.isRegularFile(source.path()))
                    FileUtils.copyFile(source.path().toFile(), target.toFile());
                else
                    FileUtils.copyDirectory(source.path().toFile(), target.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isRequiredDatapack(PackRepository repository, String id) {
        DatapackSource source = ((DatapackSources) repository).simpleDatapacks$getSource(id);
        return source != null && source.required();
    }
}

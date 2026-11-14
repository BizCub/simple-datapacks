package io.github.bizcub.simpleDatapacks.mixin;

import com.mojang.logging.LogUtils;
import io.github.bizcub.simpleDatapacks.Main;
import io.github.bizcub.simpleDatapacks.config.Config;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(PackRepository.class)
public class PackRepositoryMixin implements Main.DatapackSources {

    @Unique private static final Logger sd$logger = LogUtils.getLogger();

    @Shadow @Final @Mutable private Set<RepositorySource> sources;

    @Unique private final Set<RepositorySource> sd$addedSources = new LinkedHashSet<>();
    @Unique private List<String> sd$appliedRequiredPaths;
    @Unique private List<String> sd$appliedOptionalPaths;
    @Unique private final Map<String, Main.DatapackSource> sd$packSources = new HashMap<>();

    @Override
    public Main.DatapackSource simpleDatapacks$getSource(String id) {
        return sd$packSources.get(id);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void sd$addSources(CallbackInfo ci) {
        if (!Main.initialized || !sd$isServerRepository()) return;
        sd$rebuild();
    }

    @Inject(method = "reload", at = @At("HEAD"))
    private void sd$refreshSources(CallbackInfo ci) {
        sd$packSources.clear();
        if (!Main.initialized || sd$appliedRequiredPaths == null) return;
        if (Config.get().requiredDatapacksPaths().equals(sd$appliedRequiredPaths)
                && Config.get().optionalDatapacksPaths().equals(sd$appliedOptionalPaths)) return;
        sd$rebuild();
    }

    @Unique
    private boolean sd$isServerRepository() {
        for (RepositorySource source : this.sources) {
            if (source instanceof ServerPacksSource) return true;
        }
        return false;
    }

    @Unique
    private void sd$rebuild() {
        Set<RepositorySource> sources = new LinkedHashSet<>(this.sources);
        sources.removeAll(sd$addedSources);
        sd$addedSources.clear();
        sd$addedSources.addAll(sd$add(false));
        sd$addedSources.addAll(sd$add(true));
        sources.addAll(sd$addedSources);
        this.sources = sources;
        sd$appliedRequiredPaths = List.copyOf(Config.get().requiredDatapacksPaths());
        sd$appliedOptionalPaths = List.copyOf(Config.get().optionalDatapacksPaths());
    }

    @Unique
    private Set<RepositorySource> sd$add(boolean required) {
        Set<RepositorySource> providedDatapacks = new LinkedHashSet<>();
        List<String> paths = required
                ? Config.get().requiredDatapacksPaths()
                : Config.get().optionalDatapacksPaths();
        paths.forEach(path -> {
            FolderRepositorySource source = new FolderRepositorySource(
                    Paths.get(path), PackType.SERVER_DATA, PackSource.DEFAULT, new DirectoryValidator(p -> true));
            providedDatapacks.add(consumer -> source.loadPacks(pack -> {
                try (var resources = pack.open()) {
                    boolean hasData = false;
                    for (var iterator = resources.iterator(); iterator.hasNext();) {
                        try (PackResources resource = iterator.next()) {
                            hasData |= !resource.getNamespaces(PackType.SERVER_DATA).isEmpty();
                        }
                    }
                    if (!hasData) return;
                    Path packPath = Paths.get(path).resolve(pack.getId().substring(5)).toAbsolutePath().normalize();
                    Main.DatapackSource previous = sd$packSources.put(pack.getId(), new Main.DatapackSource(packPath, required));
                    if (previous != null && !previous.path().equals(packPath)) {
                        sd$logger.warn("Duplicate datapack {}: using {} instead of {}", pack.getId(), packPath, previous.path());
                    }
                    consumer.accept(pack);
                }
            }));
        });
        return providedDatapacks;
    }
}

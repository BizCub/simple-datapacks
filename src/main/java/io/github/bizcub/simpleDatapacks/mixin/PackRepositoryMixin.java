package io.github.bizcub.simpleDatapacks.mixin;

import io.github.bizcub.simpleDatapacks.Main;
import io.github.bizcub.simpleDatapacks.config.Config;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
/*? >=1.20.2*/ import net.minecraft.world.level.validation.DirectoryValidator;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(PackRepository.class)
public class PackRepositoryMixin {

    @Shadow @Final @Mutable private Set<RepositorySource> sources;

    @Unique private Set<RepositorySource> sd$vanillaSources;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void sd$addSources(CallbackInfo ci) {
        if (!Main.initialized) return;
        sd$vanillaSources = new HashSet<>(this.sources);
        sd$rebuild();
    }

    @Inject(method = "reload", at = @At("HEAD"))
    private void sd$refreshSources(CallbackInfo ci) {
        if (!Main.initialized || sd$vanillaSources == null) return;
        sd$rebuild();
    }

    @Unique
    private void sd$rebuild() {
        Set<RepositorySource> sources = new HashSet<>(sd$vanillaSources);
        sources.addAll(sd$add(true));
        sources.addAll(sd$add(false));
        this.sources = sources;
    }

    @Unique
    private Set<FolderRepositorySource> sd$add(boolean required) {
        Set<FolderRepositorySource> providedDatapacks = new HashSet<>();
        List<String> paths = required
                ? Config.get().requiredDatapacksPaths()
                : Config.get().optionalDatapacksPaths();
        paths.forEach(path -> providedDatapacks.add(new FolderRepositorySource(
                Paths.get(path), PackType.SERVER_DATA, PackSource.DEFAULT /*? >=1.20.2 >>+ ')' */, new DirectoryValidator(p -> true))));
        return providedDatapacks;
    }
}

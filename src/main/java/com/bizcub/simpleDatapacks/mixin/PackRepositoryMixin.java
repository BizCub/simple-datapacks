package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(PackRepository.class)
public class PackRepositoryMixin {

    @Shadow @Final @Mutable private Set<RepositorySource> sources;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addSources(CallbackInfo ci) {
        if (!Main.initialized) return;

        Set<RepositorySource> sources = new HashSet<>(this.sources);
        sources.addAll(sd$add(true));
        sources.addAll(sd$add(false));

        this.sources = sources;
    }

    @Unique
    private Set<FolderRepositorySource> sd$add(boolean required) {
        Set<FolderRepositorySource> providedDatapacks = new HashSet<>();
        List<String> paths = required
                ? Main.getConfig().requiredDatapacksPaths()
                : Main.getConfig().optionalDatapacksPaths();
        paths.forEach(path -> providedDatapacks.add(new FolderRepositorySource(
                //~ if >=1.19.3 'new File(path)' -> 'Paths.get(path), PackType.SERVER_DATA'
                Paths.get(path), PackType.SERVER_DATA, PackSource.DEFAULT /*? >=1.20.2 >>+ 'or()' */, Minecraft.getInstance().directoryValidator())));
        return providedDatapacks;
    }
}

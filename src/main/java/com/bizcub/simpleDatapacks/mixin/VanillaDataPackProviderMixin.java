package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.resource.*;
//? if >=1.20.2 {
/*import net.minecraft.util.path.SymlinkFinder;*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mixin(VanillaDataPackProvider.class)
public abstract class VanillaDataPackProviderMixin {

    //? if >=1.20.2 {
    /*@Inject(method = "createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "RETURN"), cancellable = true)
    private static void createManager(Path dataPacksPath, SymlinkFinder symlinkFinder, CallbackInfoReturnable<ResourcePackManager> cir) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(List.of(new ResourcePackProvider[]{new VanillaDataPackProvider(symlinkFinder), new FileResourcePackProvider(Path.of("resources"), ResourceType.SERVER_DATA, ResourcePackSource.WORLD, symlinkFinder)}));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths){
                providedDatapacks.add(new FileResourcePackProvider(Path.of(path), ResourceType.SERVER_DATA, ResourcePackSource.NONE, symlinkFinder));
            }
        } else providedDatapacks.add(new FileResourcePackProvider(Path.of("datapacks"), ResourceType.SERVER_DATA, ResourcePackSource.NONE, symlinkFinder));
        providedDatapacks.add(new FileResourcePackProvider(dataPacksPath, ResourceType.SERVER_DATA, ResourcePackSource.NONE, symlinkFinder));

        cir.setReturnValue(new ResourcePackManager(providedDatapacks.toArray(new ResourcePackProvider[0])));
    }

    *///?} else {
    @Inject(method = "createManager(Ljava/nio/file/Path;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "RETURN"), cancellable = true)
    private static void createManager(Path dataPacksPath, CallbackInfoReturnable<ResourcePackManager> cir) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(List.of(new ResourcePackProvider[]{new VanillaDataPackProvider(), new FileResourcePackProvider(Path.of("resources"), ResourceType.SERVER_DATA, ResourcePackSource.WORLD)}));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(new FileResourcePackProvider(Path.of(path), ResourceType.SERVER_DATA, ResourcePackSource.NONE));
            }
        } else providedDatapacks.add(new FileResourcePackProvider(Path.of("datapacks"), ResourceType.SERVER_DATA, ResourcePackSource.NONE));
        providedDatapacks.add(new FileResourcePackProvider(dataPacksPath, ResourceType.SERVER_DATA, ResourcePackSource.NONE));

        cir.setReturnValue(new ResourcePackManager(providedDatapacks.toArray(new ResourcePackProvider[0])));
    }//?}

    @Inject(method = "create", at = @At(value = "HEAD"), cancellable = true)
    private void create(CallbackInfoReturnable<ResourcePackProfile> cir) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            if (!Configs.getInstance().showFeatures) cir.cancel();
        }
    }
}

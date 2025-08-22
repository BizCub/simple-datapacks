package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import com.bizcub.simpleDatapacks.config.Compat;
import com.bizcub.simpleDatapacks.config.Configs;
import net.minecraft.resource.*;
//? if >=1.20.2 {
/*import net.minecraft.util.path.SymlinkFinder;*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mixin(VanillaDataPackProvider.class)
public abstract class VanillaDataPackProviderMixin {

    //? if >=1.20.2 {
    /*@Unique private static SymlinkFinder symlinkFinder;

    @Inject(method = "createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "HEAD"))
    private static void getSymlinkFinder(Path dataPacksPath, SymlinkFinder symlinkFinder, CallbackInfoReturnable<ResourcePackManager> cir) {
        VanillaDataPackProviderMixin.symlinkFinder = symlinkFinder;
    }

    @ModifyArg(method = "createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"))
    private static ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(List.of(par1));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(new FileResourcePackProvider(Path.of(path), ResourceType.SERVER_DATA, ResourcePackSource.NONE, symlinkFinder));
            }
        } else providedDatapacks.add(new FileResourcePackProvider(Path.of("datapacks"), ResourceType.SERVER_DATA, ResourcePackSource.NONE, symlinkFinder));
        return providedDatapacks.toArray(new ResourcePackProvider[0]);
    }

    *///?} else {
    @ModifyArg(method = "createManager(Ljava/nio/file/Path;)Lnet/minecraft/resource/ResourcePackManager;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"))
    private static ResourcePackProvider[] addProviders(ResourcePackProvider[] par1) {
        ArrayList<ResourcePackProvider> providedDatapacks = new ArrayList<>(List.of(par1));
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            for (String path : Configs.getInstance().datapacksPaths) {
                providedDatapacks.add(new FileResourcePackProvider(Path.of(path), ResourceType.SERVER_DATA, ResourcePackSource.NONE));
            }
        } else providedDatapacks.add(new FileResourcePackProvider(Path.of("datapacks"), ResourceType.SERVER_DATA, ResourcePackSource.NONE));
        return providedDatapacks.toArray(new ResourcePackProvider[0]);
    }//?}

    @Inject(method = "create", at = @At(value = "HEAD"), cancellable = true)
    private void removeFeatures(CallbackInfoReturnable<ResourcePackProfile> cir) {
        if (Compat.isModLoaded(SimpleDatapacks.clothConfigId)) {
            if (!Configs.getInstance().showFeatures) cir.cancel();
        }
    }
}

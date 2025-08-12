package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
//? if >=1.19.4 {
import net.minecraft.client.gui.screen.world.WorldCreator;
//?}
import net.minecraft.resource.ResourcePackManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    @Shadow @Nullable private Path dataPackTempDir;

    @Final @Shadow WorldCreator worldCreator;

    @Shadow private ResourcePackManager packManager;

    @Inject(method = "openPackScreen", at = @At(value = "HEAD"))
    private void openPackScreen(CallbackInfo ci) {
        SimpleDatapacks.refreshPath();
        this.dataPackTempDir = SimpleDatapacks.datapacksFolder;
    }

    @Inject(method = "clearDataPackTempDir", at = @At(value = "HEAD"), cancellable = true)
    private void clearDataPackTempDir(CallbackInfo ci) {
        ci.cancel();
        if (packManager == null) return;
        List<String> datapacks;
        Path path;
        //? if >=1.20.5 {
        /*datapacks = new ArrayList<>(packManager.getEnabledIds());
        path = SimpleDatapacks.resolveDatapacksPath(SimpleDatapacks.minecraftFolder.resolve("saves").resolve(worldCreator.getWorldDirectoryName()));
        *///?} else {
        datapacks = new ArrayList<>(packManager.getEnabledNames());
        path = SimpleDatapacks.resolveDatapacksPath(SimpleDatapacks.minecraftFolder.resolve("saves").resolve(worldCreator.getWorldDirectoryName()));
        //?}
        SimpleDatapacks.copyDatapacks(SimpleDatapacks.datapacksFolder, path, datapacks);
    }

    @Inject(method = "copyDataPack(Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/nio/file/Path;)V", at = @At(value = "HEAD"), cancellable = true)
    private static void copyDataPack(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onCloseScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", shift = At.Shift.AFTER), cancellable = true)
    private void onCloseScreen(CallbackInfo ci) {
        ci.cancel();
    }
}

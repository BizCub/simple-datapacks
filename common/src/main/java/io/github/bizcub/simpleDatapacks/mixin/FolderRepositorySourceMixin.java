package io.github.bizcub.simpleDatapacks.mixin;

import io.github.bizcub.simpleDatapacks.config.Config;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FolderRepositorySource.class)
public class FolderRepositorySourceMixin {

    @ModifyArg(method = "discoverPacks", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"))
    private static String sd$suppressEntryLog(String message) {
        return Config.get().hideNonPackPathsInLog() ? message.replace(" '{}'", "") : message;
    }
}

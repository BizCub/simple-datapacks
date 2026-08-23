package io.github.bizcub.simpleDatapacks.mixin;

import net.minecraft.server.packs.repository.FolderRepositorySource;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FolderRepositorySource.class)
public class FolderRepositorySourceMixin {

    //~ if <1.20.2 'discoverPacks' -> 'detectPackResources'
    @Redirect(method = "discoverPacks", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"))
    private static void suppressEntryLog(Logger logger, String message, Object arg) {

    }
}

package com.bizcub.simpleDatapacks.mixin;

import com.bizcub.simpleDatapacks.Main;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.world.level.DataPackConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;

//? >=1.19.3 {
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.WorldDataConfiguration;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    //~ if >=1.21.2 'openFresh' -> 'openCreateWorldScreen'
    @ModifyArg(method = "openCreateWorldScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;createDefaultLoadConfig(Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/world/level/WorldDataConfiguration;)Lnet/minecraft/server/WorldLoader$InitConfig;"))
    private static WorldDataConfiguration addGlobalFeatures(WorldDataConfiguration worldDataConfiguration) {
        if (Main.getConfig().globalFeatures()) {
            List<String> features = new ArrayList<>(worldDataConfiguration.dataPacks().getEnabled());
            features.addAll(Main.features);
            return new WorldDataConfiguration(new DataPackConfig(features, List.of()), FeatureFlags.DEFAULT_FLAGS);
        }
        return worldDataConfiguration;
    }
}

//?} <1.19 {
/*import com.mojang.datafixers.util.Pair;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    @Shadow protected DataPackConfig dataPacks;

    @Redirect(method = "openDataPackSelectionScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/util/Pair;getSecond()Ljava/lang/Object;"))
    private Object addRequiredDatapacks(Pair instance) {
        PackRepository packRepository = (PackRepository) instance.getSecond();
        ArrayList<String> list = new ArrayList<>(dataPacks.getEnabled());
        list.addAll(Main.getRequiredDatapacks());

        packRepository.setSelected(list);
        return packRepository;
    }
}

*///?} else {
/*@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin{

}*///?}

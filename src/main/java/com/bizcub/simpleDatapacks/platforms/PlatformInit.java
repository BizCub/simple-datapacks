package com.bizcub.simpleDatapacks.platforms;

import com.bizcub.simpleDatapacks.config.Configs;
import me.shedaniel.autoconfig.AutoConfig;
/*? >=1.21.11 && !forge*/ //import me.shedaniel.autoconfig.AutoConfigClient;
import net.minecraft.client.gui.screens.Screen;

public class PlatformInit {

    //? is_cloth_config_available {
    public static Screen getScreen(Screen parent) {
        /*? >=1.21.11*/ //return AutoConfigClient.getConfigScreen(Configs.class, parent).get();
        /*? <=1.21.10*/ return AutoConfig.getConfigScreen(Configs.class, parent).get();
    }//?}
}

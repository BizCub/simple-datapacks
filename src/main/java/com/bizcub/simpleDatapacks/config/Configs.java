package com.bizcub.simpleDatapacks.config;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config(name = SimpleDatapacks.modId)
public class Configs implements ConfigData {

    @Tooltip
    public boolean copyDatapacks = false;

    @Tooltip
    public boolean showFeatures = true;

    @Tooltip
    public boolean globalDatapacks = false;

    @Tooltip
    public boolean sendRestartWarning = true;

    public List<String> datapacksPaths = new ArrayList<>(Arrays.asList("datapacks", System.getProperty("user.home") + "\\Downloads"));

    public static Configs getInstance() {
        return AutoConfig.getConfigHolder(Configs.class).getConfig();
    }

    public static void init() {
        AutoConfig.register(Configs.class, GsonConfigSerializer::new);
    }
}

package com.bizcub.simpleDatapacks.config;

import com.bizcub.simpleDatapacks.SimpleDatapacks;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = SimpleDatapacks.modId)
public class Configs implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public String datapacksPath = SimpleDatapacks.resolveDatapacksPath(SimpleDatapacks.minecraftFolder).toString();

    @ConfigEntry.Gui.Tooltip
    public boolean copyDatapacks = false;

    @ConfigEntry.Gui.Tooltip
    public boolean worldText = false;

    public static Configs getInstance() {
        return AutoConfig.getConfigHolder(Configs.class).getConfig();
    }

    public static void init() {
        AutoConfig.register(Configs.class, GsonConfigSerializer::new);
    }
}

plugins {
    id("multiloader-common")
    id("me.modmuss50.mod-publish-plugin")
}

sc.constants["is_cloth_config_available"] = isClothConfigAvailable

sc.replacements {
    string(scp >= "1.21.2") {
        replace("openFresh", "openCreateWorldScreen")
    }
}

if (isForge) {
    if (!isClothConfigAvailable) {
        setProp("cloth_config", "17.0.144")
    }
}

reps.clear()
reps.add(Repository("https://maven.shedaniel.me"))

deps.clear()
deps.add(Dependency("me.shedaniel.cloth:cloth-config-${mod.loader}:${getProp("cloth_config")}", "implementation"))

if (isFabric) {
    reps.add(Repository("https://maven.terraformersmc.com/releases"))

    deps.add(Dependency("net.fabricmc:fabric-loader:latest.release", "implementation"))
    deps.add(Dependency("com.terraformersmc:modmenu:${getProp("modmenu")}", "api"))
}

if (isNeoForge) {
    reps.add(Repository("https://maven.neoforged.net/releases"))
}

if (isForge) {

}

publishMods {
    modrinth {
        if (isClothConfigAvailable) optional("cloth-config")
        if (isFabric) optional("modmenu")
    }
    curseforge {
        if (isClothConfigAvailable) optional("cloth-config")
        if (isFabric) optional("modmenu")
    }
}

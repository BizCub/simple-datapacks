plugins {
    id("multiloader-common")
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:latest.release")
    }
}

if (isForge || isNeoForge) {
    project.extra["mc.snapshot"] = null
}

if (isForge) {
    if (!isClothConfigAvailable) {
        project.extra["mod.cloth_config"] = "17.0.144"
    }
    if (mod.mc == "1.16.5") project.extra["pub.end"] = "1.16.5"
    if (mod.mc == "1.21.1") project.extra["pub.start"] = "1.20.6"
}

if (isNeoForge) {
    val neoVers = mod.mc.substring(2)
    val neoLoader = mod.dep("neoforge_loader")
    val neoForge = if (neoVers.contains(".")) "$neoVers.$neoLoader" else "$neoVers.0.$neoLoader"
    project.setProperty("deps.neoforge_loader", neoForge)

    if (mod.mc == "1.21.1") project.extra["pub.start"] = "1.21"
}

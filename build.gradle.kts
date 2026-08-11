plugins {
    id("io.github.bizcub.multiloader")
}

multiloader {
    val isClothConfigAvailable = !(isForge && scp > "1.21.3")

    sc.constants["is_cloth_config_available"] = isClothConfigAvailable

    sc.replacements {
        string(scp >= "1.21.11" && !isForge, "auto_config") {
            replace("AutoConfig", "AutoConfigClient")
        }
    }

    setMREnvironment(mrEnvs.clientOnly)
    setCFEnvironment(cfEnvs.client)

    versionRange("26.1.2", to = "latest")
    versionRange("1.21.3", to = "1.21.11")
    versionRange("1.21.1", to = "1.20.5", loader = "fabric")
    versionRange("1.21.1", to = "1.20.6", loader = "forge")
    versionRange("1.20.2", to = "latest")
    versionRange("1.20.2", to = "1.20.4", loader = "forge")

    addDependency(
        dependency = "me.shedaniel.cloth:cloth-config-${mod.loader}:${getDep("cloth-config").split("+").first()}",
        configuration = if (isClothConfigAvailable) "implementation" else "compileOnly",
        repository = "maven.shedaniel.me",
        excludedModules = listOf("net.fabricmc.fabric-api"),
        isPublishDepEnabled = isClothConfigAvailable,
        publishProjectId = "cloth-config"
    )
    addDependency(
        dependency = "maven.modrinth:enhanced-world-creation:${getDep("enhanced-world-creation")}",
        configuration = "runtimeOnly"
    )

    if (isFabric) {
        addDependency(
            dependency = "net.fabricmc:fabric-loader:${getDep("fabric")}"
        )
        addDependency(
            dependency = "com.terraformersmc:modmenu:${getDep("modmenu")}",
            repository = "maven.terraformersmc.com/releases",
            isPublishDepEnabled = true
        )
    }
}

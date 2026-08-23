plugins {
    id("io.github.bizcub.multiloader")
}

multiloader {
    sc.replacements {
        string(scp >= "1.21.11" && !isForge, "auto_config") {
            replace("AutoConfig", "AutoConfigClient")
        }
    }

    setMREnvironment(mrEnvs.clientOnly)
    setCFEnvironment(cfEnvs.client)

    versionRange("26.1.2", to = "latest")
    versionRange("1.21.3", to = "1.21.11")
    versionRange("1.21.1", from = "1.20.6", loader = "forge")
    versionRange("1.20.2", to = "1.21.1")
    versionRange("1.20.2", to = "1.20.4", loader = "forge")

    addDependency(
        dependency = "maven.modrinth:enhanced-world-creation:${getDep("enhanced-world-creation")}",
        configuration = "runtimeOnly"
    )
    addDependency(
        dependency = "maven.modrinth:simple-config-lib:${getDep("simple-config-lib")}"
    )
    val isClothConfigAvailable = !(isForge && scp > "1.21.3")
    addDependency(
        dependency = "me.shedaniel.cloth:cloth-config-${mod.loader}:${getDep("cloth-config").split("+").first()}",
        configuration = if (isClothConfigAvailable) "implementation" else "compileOnly",
        repository = "maven.shedaniel.me",
        excludedModules = listOf("net.fabricmc.fabric-api"),
        isPublishDepEnabled = isClothConfigAvailable,
        publishProjectId = "cloth-config"
    )

    if (isFabric) {
        addDependency(
            dependency = "net.fabricmc:fabric-loader:${getDep("fabric")}"
        )
        addDependency(
            dependency = "net.fabricmc.fabric-api:fabric-api:${getDep("fabric-api")}"
        )
        addDependency(
            dependency = "com.terraformersmc:modmenu:${getDep("modmenu")}",
            repository = "maven.terraformersmc.com/releases",
            isPublishDepEnabled = true
        )
    }
}

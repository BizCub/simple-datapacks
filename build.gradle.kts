multiloader {
    setMREnvironment(mrEnvs.serverOnlyClientOptional)
    setCFEnvironment(cfEnvs.both)

    configureLoaders {
        addDependency(
            dependency = "maven.modrinth:enhanced-world-creation:${getDep("enhanced-world-creation")}",
            configuration = "runtimeOnly"
        )
        addDependency(
            dependency = getSimpleConfigLibDep(),
            isPublishDepEnabled = true
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
}

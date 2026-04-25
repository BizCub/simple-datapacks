plugins {
    multiloader
    alias(libs.plugins.loom)
}

apply(from = ml.scriptPath)

multiloader {
    repositories {
        for (rep in reps) maven(rep.repository)
    }

    dependencies {
        minecraft("com.mojang:minecraft:${mod.mcExact}")
        if (isObfuscated) "mappings"(loom.officialMojangMappings())
        for (dep in deps) {
            val configuration = if (isObfuscated) dep.modConfiguration else dep.configuration
            when (dep.id) {
                "cloth-config-fabric" -> add(configuration, dep.dependency) {
                    exclude(group = "net.fabricmc.fabric-api")
                }
                else -> add(configuration, dep.dependency)
            }
        }
    }

    loom {
        runConfigs.getByName("client") { runDir = clientRunPath }
        runConfigs.getByName("server") { runDir = serverRunPath }
    }

    val builtFile = if (isObfuscated)
        tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar").get().archiveFile
    else
        tasks.jar.get().archiveFile

    publishMods {
        file.set(builtFile)
    }

    tasks.named<Copy>("buildAndCollect") {
        from(builtFile)
    }
}

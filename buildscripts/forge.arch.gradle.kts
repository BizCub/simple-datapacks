plugins {
    multiloader
    alias(libs.plugins.loom.arch)
}

multiloader {
    setBuiltFile(tasks.remapJar.get().archiveFile)

    repositories {
        for (rep in reps) maven(rep.repository)
    }

    dependencies {
        minecraft("com.mojang:minecraft:${mod.mc}")
        mappings(loom.officialMojangMappings())
        "forge"("net.minecraftforge:forge:${getDep("forge")}")
        for (dep in deps) dep.configuration(dep.dependency) {
            for (module in eModules) exclude(module.module)
        }
    }

    loom {
        if (mixinFile.exists())
            forge.mixinConfigs(mixinFile.name)
        if (ctForgeArchFile.exists())
            accessWidenerPath.set(ctForgeArchFile)

        runConfigs {
            getByName("client") {
                runDirectory.set(clientRunFile)
            }
            getByName("server") {
                runDirectory.set(serverRunFile)
            }
        }
    }
}

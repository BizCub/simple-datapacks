plugins {
    multiloader
    alias(libs.plugins.loom.arch)
}

multiloader {
    loom.silentMojangMappingsLicense()

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(mod.javaNumber))

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
        runConfigs.getByName("client") { runDirectory.set(clientRunFile) }
        runConfigs.getByName("server") { runDirectory.set(serverRunFile) }

        forge.mixinConfigs("${mod.mixin}.mixins.json")
        val awFile = file(ctForgeArchPath)
        if (awFile.exists()) accessWidenerPath.set(awFile)

        decompilers {
            get("vineflower").apply {
                options.put("mark-corresponding-synthetics", "1")
            }
        }
    }

    val builtFile = tasks.remapJar.get().archiveFile

    publishMods {
        file.set(builtFile)
    }

    tasks {
        named<Copy>("buildAndCollect") {
            from(builtFile)
        }

        named("validateAccessWidener") {
            dependsOn("processResources")
        }
    }
}

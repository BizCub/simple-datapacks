pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.kikugie.dev/snapshots")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.10"
}

stonecutter {
    create(rootProject) {
        val fb = "fabric"; val fr = "forge"; val nf = "neoforge"
        fun match(version: String, vararg loaders: String) = loaders
            .forEach { version("$version-$it", version) }
        match("1.21.9", fb, fr, nf)
        match("1.21.3", fb, fr, nf)
        match("1.21.1", fb, fr, nf)
        match("1.20.2", fb, fr)
        match("1.19.4", fb, fr)
    }
}

rootProject.name = extra["mod.name"] as String

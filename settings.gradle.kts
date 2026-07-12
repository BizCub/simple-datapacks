pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.architectury.dev")
        maven("https://maven.fabricmc.net")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9+"
    id("com.bizcub.multiloader") version "0.7+"
}

multiloader {
    match("26.1.2", fb, fg, nf)
    match("1.21.3", fb, fg, nf)
    match("1.21.1", fg, nf)
    match("1.20.2", fb, fg)
    match("1.19.4", fb, fg)
    match("1.19",   fb, fg)
    match("1.18",   fg)
    match("1.17.1", fg)
    match("1.16.5", fb)
}

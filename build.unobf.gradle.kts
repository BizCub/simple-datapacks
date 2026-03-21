plugins {
    multiloader
    id("net.fabricmc.fabric-loom") version "1.15-SNAPSHOT"
}

repositories {
    maven("https://maven.terraformersmc.com/releases")
}

dependencies {
    minecraft("com.mojang:minecraft:${propIf("version", mod.mc)}")
    implementation("net.fabricmc:fabric-loader:latest.release")
}

loom {
    runConfigs.getByName("client") { runDir = "../../run/client" }
    runConfigs.getByName("server") { runDir = "../../run/server" }
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(tasks.jar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}"))
    dependsOn("build")
}

if (scc.isActive) {
    rootProject.tasks.register("buildActive") { dependsOn(tasks.named("buildAndCollect")) }
    rootProject.tasks.register("runActiveClient") { dependsOn(tasks.named("runClient")) }
    rootProject.tasks.register("runActiveServer") { dependsOn(tasks.named("runServer")) }
}

plugins {
    id("net.neoforged.moddev") version "2.0.80"
    id("com.almostreliable.almostgradle") version "1.3.0"
}

almostgradle.setup {
    withSourcesJar = false
}

repositories {
    maven("https://maven.blamejared.com")
    mavenLocal()
}

dependencies {
    // Almost Unified
    compileOnly("com.almostreliable.mods:almostunified-neoforge:${almostgradle.minecraftVersion}-${almostgradle.getProperty("auVersion")}:api")
    localRuntime("com.almostreliable.mods:almostunified-neoforge:${almostgradle.minecraftVersion}-${almostgradle.getProperty("auVersion")}")

    // Immersive Engineering
    localImplementation("blusunrize.immersiveengineering:ImmersiveEngineering:${almostgradle.minecraftVersion}-${almostgradle.getProperty("ieVersion")}")
}

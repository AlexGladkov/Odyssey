rootProject.name = "Odyssey"
include(
    ":odyssey:odyssey-core",
    ":odyssey:odyssey-compose",
    ":odyssey:odyssey-android",
    ":sample:shared",
    ":sample:androidApp",
    ":sample:desktopApp",
    ":sample:jsApp",
    ":sample:hiltApp"
)

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
        google()
        mavenCentral()
        mavenLocal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "Odyssey"
include(
    "odyssey-core",
    "odyssey-compose",
    "odyssey-android",
    ":sample:shared",
    ":sample:androidApp",
    ":sample:desktopApp",
    ":sample:jsApp",
//    ":sample:hiltApp"
)

//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
//}
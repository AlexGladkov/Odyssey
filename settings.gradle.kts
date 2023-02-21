pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenLocal()
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("com.android.library").version(extra["agp.version"] as String)
    }
}

include(
    ":common:common-sample",

    ":odyssey:odyssey-core",
    ":odyssey:odyssey-compose",
    ":odyssey:odyssey-android",

    ":android",
    ":desktop",
    ":uikit",
    ":hilt",
)

//includeBuild("convention-plugins")
rootProject.name = "odyssey"
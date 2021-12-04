plugins {
    `kotlin-dsl`
    id("net.rdrei.android.buildtimetracker") version "0.11.0"
}

apply(plugin = "net.rdrei.android.buildtimetracker")

val compileSdkVersion by extra(31)
val targetSdkVersion by extra(30)
val minSdkVersion by extra(21)

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
    }
}

buildscript {
    val kotlin_version by extra("1.5.31")
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("net.rdrei.android.buildtimetracker:gradle-plugin:0.11.0")
    }
}

buildtimetracker {
    reporters {
        register("summary") {
            options["ordered"] = "true"
            options["barstyle"] = "none"
            options["shortenTaskNames"] = "false"
        }
    }
}
import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(Dependencies.JetBrains.Compose.gradlePlugin)
    implementation(Dependencies.JetBrains.Kotlin.gradlePlugin)
    implementation(Dependencies.Android.Tools.Build.gradlePlugin)
    implementation(Dependencies.JetBrains.Kotlin.serializationPlugin)
}

val rootDirProject = file("../")

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}

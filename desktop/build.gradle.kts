import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":common:common-sample"))
                implementation(project(":odyssey:odyssey-core"))
                implementation(project(":odyssey:odyssey-compose"))
            }
        }

        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }

        named("jvmTest") {
            dependencies {
                implementation("junit:junit:4.12")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "ru.alexgladkov.odyssey-demo.Main_desktopKt"

        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi
            )
            packageName = "Odyssey Demo Desktop"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "Odyssey Demo Desktop"
                upgradeUuid = "7abd45e8-22c0-11ec-9621-0242ac130002"
            }
        }
    }
}
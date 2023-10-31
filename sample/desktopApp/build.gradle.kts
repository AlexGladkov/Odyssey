plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvmToolchain(17)

    jvm {
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":sample:shared"))
                implementation(project(":odyssey:odyssey-compose"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
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
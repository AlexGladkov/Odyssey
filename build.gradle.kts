import org.gradle.internal.impldep.org.codehaus.plexus.util.Os
import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.compose.experimental.dsl.IOSDevices


buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    val kotlin_version by extra("1.6.21")
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}


plugins {
//    `kotlin-dsl`
    kotlin("multiplatform")// version "1.6.21"
    id("org.jetbrains.compose")// version "1.2.0-alpha01-dev686"
}

version = "1.0-SNAPSHOT"

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
    }
}

kotlin {
    jvm("desktop")
    js(IR) {
        browser()
        binaries.executable()
    }
    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal"
                )
            }
        }
    }
    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal"
                )
            }
        }
    }
    iosX64("uikitX64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }
    iosArm64("uikitArm64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
                // TODO: the current compose binary surprises LLVM, so disable checks for now.
                freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
//                implementation(project(":common:common-root"))
//                implementation(project(":odyssey:odyssey-core"))
//                implementation(project(":common:common-root"))
                implementation(project(":odyssey:odyssey-compose"))
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }

        val jsMain by getting {
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val macosMain by creating {
            dependsOn(nativeMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
        val uikitMain by creating {
            dependsOn(nativeMain)
        }
        val uikitX64Main by getting {
            dependsOn(uikitMain)
        }
        val uikitArm64Main by getting {
            dependsOn(uikitMain)
        }
    }
}

compose.desktop {
    application {
        mainClass = "Main_desktopKt"

        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "Falling Balls"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "Compose Examples"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
        }
    }
}

compose.experimental {
    web.application {}
    uikit.application {
        bundleIdPrefix = "ru.alexgladkov"
        projectName = "OdysseyiOS"
        deployConfigurations {
            // Usage ./gradlew uikit:iosDeployIPhone13Debug
            simulator("IPhone13") {
                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_PRO
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }
}




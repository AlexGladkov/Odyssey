plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.cocoapods.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.compose.get().pluginId)
}

version = "1.0-SNAPSHOT"

kotlin {
    androidTarget()
    jvm("desktop")

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    cocoapods {
        summary = "Shared code for the sample"
        homepage = "https://github.com/AlexGladkov/Odyssey"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)

            implementation(projects.odysseyCore)
            implementation(projects.odysseyCompose)
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.activity.appcompat)
                implementation(libs.activity.compose)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    namespace = "ru.alexgladkov.shared"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }
}

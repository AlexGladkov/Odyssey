plugins {
    id(libs.plugins.multiplatform.get().pluginId)
//    alias(libs.plugins.compose)
}

kotlin {

    js {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
//                implementation(compose.ui)
//                implementation(compose.foundation)
//                implementation(compose.material)
//                implementation(compose.runtime)

                implementation(project(":odyssey-core"))
                implementation(project(":odyssey-compose"))
            }
        }

        val jsMain by getting  {
            dependencies {
//                implementation(compose.html.core)
                implementation(project(":sample:shared"))
            }
        }
    }
}

//compose.experimental {
//    web.application {}
//}
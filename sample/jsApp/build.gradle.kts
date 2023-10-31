plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    targetHierarchy.default()
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)

                implementation(project(":odyssey:odyssey-core"))
                implementation(project(":odyssey:odyssey-compose"))
            }
        }

        val jsMain by getting  {
            dependencies {
                implementation(compose.html.core)
                implementation(project(":sample:shared"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}
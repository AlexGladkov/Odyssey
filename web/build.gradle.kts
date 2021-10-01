plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser {
            useCommonJs()
            binaries.executable()
        }
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":odyssey:core"))
                implementation(project(":odyssey:compose"))
            }
        }

        named("jsMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.web.widgets)
            }
        }
    }
}
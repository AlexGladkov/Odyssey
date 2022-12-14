object Dependencies {

    val odyssey = "1.3.0"
    val odysseyPackage = "io.github.alexgladkov"

    val compileSdk = 33
    val targetSdk = 33

    object DI {
        const val kodein = "org.kodein.di:kodein-di:7.1.0"
    }

    object Images {
        const val kamel = "com.alialbaali.kamel:kamel-image:0.3.0"
    }

    object JetBrains {
        object Kotlin {
            // __KOTLIN_COMPOSE_VERSION__
            private const val VERSION = "1.7.20"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
            const val testCommon = "org.jetbrains.kotlin:kotlin-test-common:$VERSION"
            const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$VERSION"
            const val testAnnotationsCommon =
                "org.jetbrains.kotlin:kotlin-test-annotations-common:$VERSION"

            const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.2.1"

            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.0"
            const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:1.5.31"

            private const val coroutinesVersion = "1.6.4"
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
            const val coroutinesSwing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesVersion"
            const val coroutinesCommon =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion"
        }

        object Compose {
            // __LATEST_COMPOSE_RELEASE_VERSION__
            private const val VERSION = "1.2.0"
            const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$VERSION"
        }
    }

    object Android {
        object Tools {
            object Build {
                const val gradlePlugin = "com.android.tools.build:gradle:4.1.0"
            }
        }
    }

    object Utils {
        const val UUID = "com.benasher44:uuid:0.3.1"
    }

    object AndroidX {
        object AppCompat {
            const val appCompat = "androidx.appcompat:appcompat:1.3.0-beta01"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.1"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0-beta02"
        }

        object Hilt {
            const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
        }
    }

    object Google {
        object Dagger {
            const val version = 2.41
            const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
            const val hiltAndroid = "com.google.dagger:hilt-android:$version"
            const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version"
        }
    }
}
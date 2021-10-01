import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("multiplatform-setup")
    id("kotlin-parcelize")
    id("android-setup")
    kotlin("plugin.serialization")
}

kotlin {
     ios {
         binaries {
             framework {
                 transitiveExport = true
                 baseName = "OdysseyShared"
             }
         }
     }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(Dependencies.DI.kodein)
            }
        }

        val iosMain by getting

//        val iosX64Main by getting { dependsOn(iosMain) }
//        val iosArm64Main by getting { dependsOn(iosMain) }
    }
}

fun getIosTarget(): String {
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    return if (sdkName.startsWith("iphoneos")) "iosArm64" else "iosX64"
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val targetName = getIosTarget()
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from(framework.outputDirectory)
    into(targetDir)
}
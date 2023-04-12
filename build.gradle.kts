plugins {
    kotlin("multiplatform") apply false
    id("com.android.library") apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        kotlinOptions.jvmTarget = "11"
    }

    plugins.withId("maven-publish") {
        configure<PublishingExtension> {
            repositories {
                maven {
                    name = "sonatype"
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = getLocalProperty("ossrhUsername")
                        password = getLocalProperty("ossrhPassword")
                    }
                }
            }
        }
    }
}

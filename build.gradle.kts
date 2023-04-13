plugins {
    kotlin("multiplatform") apply false
    id("com.android.library") apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    `maven-publish`
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
    }
}

// Stub secrets to let the project sync and build without the publication values set up
extra.apply {
    set("signing.keyId", null)
    set("signing.password", null)
    set("signing.secretKeyRingFile", null)
    set("ossrhUsername", null)
    set("ossrhPassword", null)
}

subprojects {
    configureImplicitDependencies()

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        kotlinOptions.jvmTarget = "11"
    }

    plugins.apply("signing")
    plugins.withId("maven-publish") {
        configure<PublishingExtension> {
            extensions.configure<SigningExtension> {
                val secretPropsFile = project.rootProject.file("local.properties")
                if (secretPropsFile.exists()) {
                    secretPropsFile.reader().use {
                        java.util.Properties().apply {
                            load(it)
                        }
                    }.onEach { (name, value) ->
                        extra.apply {
                            set(name.toString(), value)
                        }
                    }
                } else {
                    extra.apply {
                        set("signing.keyId", System.getenv("SIGNING_KEY_ID"))
                        set("signing.password", System.getenv("SIGNING_PASSWORD"))
                        set(
                            "signing.secretKeyRingFile",
                            System.getenv("SIGNING_SECRET_KEY_RING_FILE")
                        )
                        set("ossrhUsername", System.getenv("OSSRH_USERNAME"))
                        set("ossrhPassword", System.getenv("OSSRH_PASSWORD"))
                    }
                }

                sign(publications)
            }

            repositories {
                maven {
                    name = "sonatype"
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = extra.get("ossrhUsername").toString()
                        password = extra.get("ossrhPassword").toString()
                    }
                }
            }
        }
    }
}

fun configureImplicitDependencies() {
    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        dependsOn(signingTasks)
    }
}

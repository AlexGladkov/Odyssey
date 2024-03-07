plugins {
    id(libs.plugins.multiplatform.get().pluginId).apply(false)
    id(libs.plugins.android.library.get().pluginId).apply(false)
    id(libs.plugins.compose.get().pluginId)
        .version(libs.versions.plugin.multiplatform.compose.get())
        .apply(false)
    id(libs.plugins.dagger.get().pluginId)
        .version(libs.versions.dagger.get())
        .apply(false)
    `maven-publish`
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
    afterEvaluate {
        afterEvaluate {
            configureImplicitDependencies()
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        kotlinOptions.jvmTarget = "17"
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
                    if (extra.has("ossrhUsername") && extra.has("ossrhPassword")) {
                        credentials {
                            username = extra.get("ossrhUsername").toString()
                            password = extra.get("ossrhPassword").toString()
                        }
                    }
                }
            }
        }
    }
}

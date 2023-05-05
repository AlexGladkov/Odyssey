import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import java.io.File

var MavenPublication.mppArtifactId: String
    get() = throw UnsupportedOperationException()
    set(value) {
        val target = this.name
        artifactId = if ("kotlinMultiplatform" in target) value else "$value-$target"
    }

fun Project.configureMavenPublication(
    groupId: String,
    artifactId: String,
    name: String
) {
    val javadocJar by tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
    }

    extensions.configure<PublishingExtension> {
        publications {
            all {
                this as MavenPublication

                this.groupId = groupId
                mppArtifactId = artifactId

                artifact(javadocJar.get())

                pom {
                    this.name.set(name)
                    this.description.set("Lightweight multiplatform navigation library (jvm, android, ios)")
                    this.url.set("https://github.com/AlexGladkov/Odyssey")

                    url.set("https://github.com/AlexGladkov/Odyssey")
                    licenses {
                        license {
                            this.name.set("MIT")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("AlexGladkov")
                            this.name.set("Alex Gladkov")
                            email.set("mobiledevelopercourse@gmail.com")
                        }
                    }
                    scm {
                        url.set("https://github.com/AlexGladkov/Odyssey")
                    }
                }
            }
        }
    }
}

fun Project.getLocalProperty(key: String, file: String = "local.properties"): String? {
    val properties = java.util.Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        java.io.InputStreamReader(java.io.FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties.getProperty(key)
}

fun Project.configureImplicitDependencies() {
    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        dependsOn(signingTasks)
    }
}
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
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
    extensions.configure<PublishingExtension> {
        publications {
            all {
                this as MavenPublication

                this.groupId = groupId
                mppArtifactId = artifactId

                pom {
                    this.name.set(name)
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
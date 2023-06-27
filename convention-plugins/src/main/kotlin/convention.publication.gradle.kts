
import java.util.Properties

plugins {
    `maven-publish`
    signing
}

extra["signing.keyId"] = null
extra["signing.password"] = null
extra["signing.secretKeyRingFile"] = null
extra["ossrhUsername"] = null
extra["ossrhPassword"] = null
extra["userEmail"] = null

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        extra[name.toString()] = value
    }
} else {
    extra["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    extra["signing.password"] = System.getenv("SIGNING_PASSWORD")
    extra["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    extra["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    extra["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

fun getExtraString(name: String) = extra[name]?.toString()

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

        pom {
            name.set("Speedometer")
            description.set("Dynamic Speedometer and Gauge for Compose Multiplatform. amazing, powerful, and multi shape.")
            url.set("https://github.com/anastr/Speedometer")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("anastr")
                    name.set("Anas Altair")
                    email.set(getExtraString("userEmail"))
                }
            }
            scm {
                connection.set("scm:git:github.com/anastr/Speedometer.git")
                developerConnection.set("scm:git:ssh://github.com/anastr/Speedometer.git")
                url.set("https://github.com/anastr/Speedometer")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

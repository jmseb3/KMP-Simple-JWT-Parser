import java.util.Properties

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

group = "io.github.jmseb3"
version = "1.0.0"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()
    android("android") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        publishLibraryVariants("release")
    }
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.wonddak.jwt"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
}


// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

fun getExtraString(name: String) = ext[name]?.toString()

val dokkaOutputDir = "$buildDir/dokka"
tasks.dokkaHtml {
    outputDirectory.set(file(dokkaOutputDir))
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

publishing {
    // Configure maven central repository
    repositories {
        maven {
            name = "sonatype"
            url = if (project.version.toString().endsWith("SNAPSHOT")) {
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            } else {
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    // Configure all publications
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("simpleJwtParser")
            description.set("Simple JWT Token parser Library for IOS and Android(KMM)")
            url.set("https://github.com/jmseb3/KMM-Simple-JWT-Parser")

            licenses {
                license {
                    name.set("Apache License 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0")
                }
            }
            developers {
                developer {
                    id.set("jmseb3")
                    name.set("WonDDak")
                    email.set("jmseb3@naver.com")
                }
            }
            scm {
                url.set("https://github.com/jmseb3/KMM-Simple-JWT-Parser.git")
                connection.set("git@github.com:jmseb3/KMM-Simple-JWT-Parser.git")
            }
        }
    }
}
signing {
    sign(publishing.publications)
}

// TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
    dependsOn(project.tasks.withType(Sign::class.java))
}
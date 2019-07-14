import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
    id("org.jetbrains.dokka") version "0.9.17"
    `maven-publish`
}

group = "cn.rexih.config"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    google()
    maven {
        // change to your target maven remote repo
        url = uri("http://your.nexus.com/nexus/content/repositories/snapshots/")
    }
    maven {
        url = uri("http://maven.aliyun.com/nexus/content/groups/public/")
    }
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
    maven {
        url = uri("https://oss.jfrog.org/libs-snapshot")
    }
    maven {
        url = uri("https://jitpack.io")
    }


}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(gradleApi())

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    from(tasks.dokka)
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

// Create sources Jar from main kotlin sources
val sourcesJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles sources JAR"
    classifier = "sources"
    from(kotlin.sourceSets["main"].kotlin, kotlin.sourceSets["main"].resources)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            artifact(dokkaJar)
        }
    }
    repositories {
        maven {
            // url = uri("$buildDir/repository")
            // change to your target maven remote repo and credentials
            url = uri("http://your.nexus.com/nexus/content/repositories/snapshots/")
            credentials {
                username = "admin"
                password = "admin"
            }
        }
    }
}

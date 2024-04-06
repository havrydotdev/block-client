plugins {
    signing
    kotlin("jvm") version "1.9.23"
    `maven-publish`
}

group = "org.havry"
val pluginVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.havry"
            artifactId = "block-client"
            version = pluginVersion

            from(components["java"])
        }
    }

}

kotlin {
    jvmToolchain(17)
}
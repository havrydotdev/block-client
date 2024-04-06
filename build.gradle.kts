plugins {
    `maven-publish`
    kotlin("jvm") version "1.9.23"
}

group = "org.havry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name = "BlockClient"
                description = "HTTP Client For Block API"
                url = "http://www.example.com/library"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "havry"
                        name = "Havrylenko Ivan"
                        email = "havrylenko.inbox@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/gavrylenkoIvan/block-client.git"
                    developerConnection = "scm:git:ssh://github.com/gavrylenkoIvan/block-client.git"
                    url = "https://github.com/gavrylenkoIvan/block-client"
                }
            }
        }
    }
}



kotlin {
    jvmToolchain(17)
}
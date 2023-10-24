import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.1.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.example.BTreeBot")
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/dev.robocode.tankroyale/robocode-tankroyale-bot-api
    implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:0.19.2")
    // Add sources so lsp can index
    implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:0.19.2:sources")

    // testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

// Invoke with ./gradlew packageBot
tasks.register<Copy>("packageBot") {
    dependsOn(tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>())

    from("src/main/java/org/example/BTreeBot.json")
    from(layout.buildDirectory.file("libs/BTreeBot.jar"))

    into("BTreeBot")
}


tasks.clean {
    delete ("BTreeBot/BTreeBot.json")
    delete ("BTreeBot/BTreeBot.jar")
}

// kotlin {
//     jvmToolchain(11)
// }

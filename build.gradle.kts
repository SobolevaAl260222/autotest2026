plugins {
    id("java")
}

group = "study.inno"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.49.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
}

tasks.test {
    useJUnitPlatform()

    doFirst {
        println("======================== Test run start ========================")
    }

    doLast {
        println("======================== Test run end ==========================")
    }
}

// Задача 1 — запуск всех тестов
tasks.register("runAllTests") {
    dependsOn("test")
}

// Задача 2 — сообщение после завершения тестов
tasks.register("afterTests") {
    doLast {
        println("Test run is over")
    }
}

tasks.named("runAllTests") {
    finalizedBy("afterTests")
}
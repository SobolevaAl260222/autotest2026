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
    testImplementation("org.assertj:assertj-core:3.27.3")
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

// Запуск всех тестов
tasks.register("runAllTests") {
    dependsOn("test")
}

// Запуск только тестов первой задачи
// Фильтрация выполняется по @Tag("task1")
tasks.register<Test>("runTask1Tests") {
    dependsOn("testClasses")

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    useJUnitPlatform {
        includeTags("task1")
    }

    doFirst {
        println("==================== Task 1 tests start ====================")
    }

    doLast {
        println("==================== Task 1 tests end ======================")
    }
}

// Запуск только тестов второго задания
// Фильтрация выполняется по @Tag("task2")
tasks.register<Test>("runTask2Tests") {

    dependsOn("testClasses")

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    useJUnitPlatform {
        includeTags("task2")
    }

    doFirst {
        println("==================== Task 2 tests start ====================")
    }

    doLast {
        println("==================== Task 2 tests end ======================")
    }
}


// Сообщение после завершения тестов
tasks.register("afterTests") {
    doLast {
        println("Test run is over")
    }
}
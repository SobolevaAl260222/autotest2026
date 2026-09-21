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
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnitPlatform()
}
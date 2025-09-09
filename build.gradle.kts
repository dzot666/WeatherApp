plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okhttp3:okhttp:4.11.0") // библиотека OkHttp
    implementation(kotlin("stdlib"))
    implementation("org.json:json:20230227")
}
application {
    mainClass.set("MainKt") // точка входа в твой проект
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

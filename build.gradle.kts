plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0" // делает бины(Component, Service, Controller) open для proxy(cjlib)
    kotlin("plugin.jpa") version "2.1.0" // генериурет конструктор без параметров для всех сущностей(требования к @Entity)
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("kapt") version "1.7.21" // генерирует мапперы в mapstruct
}

group = "ru.azati"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-validation:3.4.0")

    implementation(
        group = "org.springdoc",
        name = "springdoc-openapi-starter-webmvc-ui",
        version = "2.2.0"
    )

    implementation("org.mapstruct.extensions.spring:mapstruct-spring-annotations:0.1.2")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

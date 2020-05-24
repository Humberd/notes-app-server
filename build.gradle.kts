import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("plugin.jpa") version "1.3.70"
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.70"
    kotlin("plugin.spring") version "1.3.70"
}

group = "pl.humberd"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.flywaydb", "flyway-core", "6.3.1")
    implementation("com.vladmihalcea", "hibernate-types-52", "2.9.10")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
}

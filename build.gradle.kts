plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "io.kamaal"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["hibernateCoreVersion"] = "6.6.13.Final"
extra["hibernateValidatorVersion"] = "8.0.2.Final"
extra["modulithVersion"] = "1.3.4"
extra["securityVersion"] = "6.4.4"
extra["aspectJVersion"] = "1.9.24"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.hibernate.orm:hibernate-core:${property("hibernateCoreVersion")}")
	implementation("org.hibernate.validator:hibernate-validator:${property("hibernateValidatorVersion")}")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.security:spring-security-crypto:${property("securityVersion")}")
	implementation("org.aspectj:aspectjrt:${property("aspectJVersion")}")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.modulith:spring-modulith-bom:${property("modulithVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

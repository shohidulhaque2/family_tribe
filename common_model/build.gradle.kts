import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Kotlin DSL Gradle DSL Support
    kotlin("jvm")
    kotlin("plugin.spring")
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Spring Plugin
//    id("org.springframework.boot") version "2.4.0" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    id("org.asciidoctor.convert") version "1.5.8"

    id("idea")

    id("org.hibernate.gradle.tools") version "1.2.5"

    id("groovy")
    id("java")
//
//    id ("maven-publish")

    jacoco

    `java-library`
    `maven-publish`
    signing
    //    id("org.hibernate.orm") version "5.4.3.Final"
}
//hibernate {
//    enhance {
//        enableLazyInitialization= true
//        enableDirtyTracking = true
//        enableAssociationManagement = true
//        enableExtendedEnhancement = false
//    }
//}

group = "com.shohidulhaque"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

//task sourceJar(type: Jar, dependsOn: classes) {
//    classifier 'sources'
//    from sourceSets.main.allSource
//}
//
//task javadocJar(type: Jar, dependsOn: javadoc) {
//    classifier = 'javadoc'
//    from javadoc.destinationDir
//}
//

publishing {
    publications {

        create<MavenPublication>("CommonModel") {
            artifactId = tasks.jar.get().archiveBaseName.get()
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("Common Model Library")
                description.set("A concise description of my library")
                url.set("http://www.example.com/library")
                properties.set(mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                ))
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("johnd")
                        name.set("John Doe")
                        email.set("john.doe@example.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://example.com/my-library.git")
                    developerConnection.set("scm:git:ssh://example.com/my-library.git")
                    url.set("http://example.com/my-library/")
                }
            }
        }
    }
//    repositories {
//        maven {
//            // change URLs to point to your repos, e.g. http://my.org/repo
//            val releasesRepoUrl = uri("$buildDir/repos/releases")
//            val snapshotsRepoUrl = uri("$buildDir/repos/snapshots")
//            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
//        }
//    }
}
//signing {
//    sign(publishing.publications["CommonModel"])
//}

tasks.javadoc {
    if (JavaVersion.current().isJava11Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}


repositories {
//    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

//publishing {
//    publications {
//        myLibrary(MavenPublication::class) {
//            from {components.java}
//        }
//    }
//}

val snippetsDir by extra { file("build/generated-snippets") }
val springCloudVersion by extra("Hoxton.SR8")
val testcontainersVersion by extra("1.15.0")

//val by springCloudVersion by extra("2020.0.0-M6")
//val by testcontainersVersion by extra("1.15.0")

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
//        mavenBom("io.spring.platform:platform-bom:1.0.1.RELEASE")
        mavenBom ("org.springframework.boot:spring-boot-dependencies:2.4.0")
    }
}
dependencies {
    //******************************************************************************************************************
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    testAnnotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct:1.4.1.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.1.Final")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.4.1.Final")
    //******************************************************************************************************************
    implementation("com.shohidulhaque:common_utility:0.0.1-SNAPSHOT")
    //compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.springframework.boot:spring-boot-starter-oauth2-client")
    compileOnly("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.springframework.boot:spring-boot-starter-hateoas")
    //******************************************************************************************************************
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    //VALIDATION LIBRARIES
    compileOnly("org.springframework.boot:spring-boot-starter-validation")
//    compileOnly("com.fasterxml.jackson.core:jackson-annotations")
//    compileOnly("com.fasterxml.jackson.core:jackson-core")
//    compileOnly("com.fasterxml.jackson.core:jackson-databind")
//    compileOnly("om.fasterxml.jackson.dataformat:jackson-dataformat-xml")
//    compileOnly("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
//    compileOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
//    compileOnly("com.fasterxml.jackson.module:jackson-module-jaxb-annotations")
//    compileOnly("com.fasterxml.jackson.module:jackson-module-parameter-names")
//    compileOnly("com.fasterxml.woodstox:woodstox-core")
//    compileOnly("com.fasterxml:classmate")

//    implementation("com.fasterxml.jackson.core:jackson-annotations")
//    implementation("com.fasterxml.jackson.core:jackson-core")
//    implementation("com.fasterxml.jackson.core:jackson-databind")
//    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
//    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
//    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
//    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations")
//    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")
//    implementation("com.fasterxml.woodstox:woodstox-core")
//    implementation("com.fasterxml:classmate")
    //******************************************************************************************************************
    //VALIDATION LIBRARIES
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-hateoas")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-validation")
    //******************************************************************************************************************
    //TESTING FRAMEWORKS AND LIBRARIES
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
//    testImplementation("org.springframework.security:spring-security-test")
//
//    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
//    implementation("org.codehaus.groovy:groovy-all:3.0.2")
//    testImplementation("org.springframework.boot:spring-boot-starter-web")
//    //VALIDATION LIBRARIES
//    testImplementation("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("org.codehaus.groovy:groovy:3.0.2")
    testImplementation("org.spockframework:spock-core:2.0-M2-groovy-3.0") {
        exclude("org.codehaus.groovy")
    }
    testImplementation("org.spockframework:spock-spring:2.0-M2-groovy-3.0")
    //******************************************************************************************************************
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(snippetsDir)
    jvmArgs()
//    systemProperty("spring.profiles.active", UNIT_TESTING)
    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT,
            TestLogEvent.STANDARD_ERROR
        )
        exceptionFormat = TestExceptionFormat.FULL
        showCauses = true
        showStackTraces = true

        debug {
            events(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_OUT,
                TestLogEvent.STANDARD_ERROR
            )
            exceptionFormat = TestExceptionFormat.FULL
        }
        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat
    }

    val failedTests = mutableListOf<TestDescriptor>()
    val skippedTests = mutableListOf<TestDescriptor>()
    //afterSuite
    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
            when (result.resultType) {
                TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
            }
        }

        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) { // root suite
                logger.lifecycle("----")
                logger.lifecycle("Test result: ${result.resultType}")
                logger.lifecycle(
                    "Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeeded, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped"
                )
                if (failedTests.isNotEmpty()) {
                    logger.lifecycle("\tFailed Tests:")
                    failedTests.forEach {
                        parent?.let { parent ->
                            logger.lifecycle("\t\t${parent.name} - ${it.name}")
                        } ?: logger.lifecycle("\t\t${it.name}")
                    }
                }

                if (skippedTests.isNotEmpty()) {
                    logger.lifecycle("\tSkipped Tests:")
                    skippedTests.forEach {
                        parent?.let { parent ->
                            logger.lifecycle("\t\t${parent.name} - ${it.name}")
                        } ?: logger.lifecycle("\t\t${it.name}")
                    }
                }
            }
        }
    })

    finalizedBy(tasks.jacocoTestReport)
}


tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}


tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

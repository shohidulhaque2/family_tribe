import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
//    kotlin("jvm") version "1.4.10"
//    kotlin("plugin.spring") version "1.4.10"

    id("org.asciidoctor.convert") version "1.5.8"

    id("idea")

    id("org.hibernate.gradle.tools") version "1.2.5"
    //https://bmuschko.github.io/gradle-docker-plugin/
    id("com.bmuschko.docker-remote-api") version "6.6.1"
    //https://github.com/liquibase/liquibase-gradle-plugin
    id("org.liquibase.gradle") version "2.0.3"

    id("groovy")
    id("java")
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
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

val snippetsDir by extra { file("build/generated-snippets") }
val springCloudVersion by extra("2020.0.0-RC1")
val testcontainersVersion by extra("1.15.0")

//val by springCloudVersion by extra("2020.0.0-M6")
//val by testcontainersVersion by extra("1.15.0")

sourceSets.create("integrationTest") {
    resources.srcDir("src/integrationTest/resources")
    val mainOut = sourceSets.main.get().output
    val testOut = sourceSets.test.get().output
    kotlin {
        compileClasspath += mainOut + testOut
        runtimeClasspath += mainOut + testOut
    }
    java {
        resources.srcDir("src/integrationTest/resources")
        compileClasspath += mainOut + testOut
        runtimeClasspath += mainOut + testOut
    }
    withConvention(GroovySourceSet::class) {
        groovy {
            srcDir("src/integrationTest/groovy")
            compileClasspath += mainOut + testOut
            runtimeClasspath += mainOut + testOut
        }
    }
}
configurations{
    configurations["integrationTestImplementation"].extendsFrom(configurations["testImplementation"])
    configurations["integrationTestRuntimeOnly"].extendsFrom(configurations["testRuntimeOnly"])
}

idea {
    module {
        testSourceDirs.addAll(kotlin.sourceSets["integrationTest"].kotlin.srcDirs)
        testSourceDirs.addAll(sourceSets["integrationTest"].java.srcDirs)
        testSourceDirs.addAll(sourceSets["integrationTest"].allSource.srcDir("groovy").srcDirs)
        testSourceDirs.addAll(sourceSets["integrationTest"].resources.srcDirs)
    }
}

//tasks.named<GroovyCompile>("compileGroovy") {
//    // Groovy only needs the declared dependencies
//    // and not the output of compileJava
//    classpath = sourceSets.main.get().compileClasspath
//}
//tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin") {
//    // Kotlin also depends on the result of Groovy compilation
//    // which automatically makes it depend of compileGroovy
//    classpath += files(conventionOf(sourceSets.main.get()).getPlugin(GroovySourceSet::class.java).groovy.classesDirectory)
//}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        mavenBom("org.testcontainers:testcontainers-bom:${testcontainersVersion}")
    }
}

dependencies {
    //==================================================================================================================
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    testAnnotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct:1.4.1.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.1.Final")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.4.1.Final")
    //==================================================================================================================
    //eureka discovery server
    //==================================================================================================================
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
//    implementation("org.springframework.boot:spring-boot-starter")
//    implementation("org.springframework.boot:spring-boot-starter-security")
//
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
//
//    implementation("org.liquibase:liquibase-core")
//    runtimeOnly("com.h2database:h2")
    //==================================================================================================================

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
//    implementation("org.codehaus.groovy:groovy-all:3.0.2")
    testImplementation("org.codehaus.groovy:groovy:3.0.2")
    testImplementation("org.spockframework:spock-core:2.0-M2-groovy-3.0") {
        exclude("org.codehaus.groovy")
    }
    testImplementation("org.spockframework:spock-spring:2.0-M2-groovy-3.0")

//    testImplementation("org.testcontainers:junit-jupiter")
    //testImplementation 'org.testcontainers:elasticsearch'
    //testImplementation 'org.testcontainers:neo4j'
//    testImplementation("org.testcontainers:postgresql")
    //==================================================================================================================
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

//    afterSuite {
//        if (!desc.parent) { // will match the outermost suite
//            val output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)"
//            val startItem = '|  ', endItem = '  |'
//            val repeatLength = startItem.length() + output.length() + endItem.length()
//            println('\n' + ('-' * repeatLength) + '\n' + startItem + output + endItem + '\n' + ('-' * repeatLength))
//        }
//    }
}

val integrationTest by tasks.creating(Test::class) {
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    //testClassesDirs = sourceSets.integrationTest.output.classesDirs
    //classpath = sourceSets.integrationTest.runtimeClasspath

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

}
integrationTest.dependsOn(tasks.test)

tasks.named("check"){
    dependsOn(":integrationTest")
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

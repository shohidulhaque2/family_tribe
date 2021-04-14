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
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //IDE Plugin
    id("idea")
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Language Plugin
    id("groovy")
    id("java")
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    jacoco
    //Code Quality Plugin
//    id("jacoco")
//    id("checkstyle")
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Database Plugin
    id("org.hibernate.gradle.tools") version "1.2.5"
    //https://github.com/liquibase/liquibase-gradle-plugin
    id("org.liquibase.gradle") version "2.0.3"
    //id("org.hibernate.orm") version "5.4.3.Final"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Docker Plugin
    //https://bmuschko.github.io/gradle-docker-plugin/
    id("com.bmuschko.docker-remote-api") version "6.6.1"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Documentation Plugin
    id("org.asciidoctor.convert") version "1.5.8"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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

//bootRun {
//    systemProperties = [
//        'spring.profiles.active': propertyDrivenProfiles
//    ]
//}

//jacoco {
//    toolVersion = "0.8.5"
//    reportsDir = file("$buildDir/customJacocoReportDir")
//}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

val snippetsDir by extra { file("build/generated-snippets") }
val springCloudVersion by extra("2020.0.0-RC1")
val testcontainersVersion by extra("1.15.0")
val postgresVersion = "42.2.18"
val okatVersion by extra("3.0.0")

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
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //needs to be in every project
    implementation("com.shohidulhaque:common_model:0.0.1-SNAPSHOT"){
        isChanging = true
    }
    implementation("com.shohidulhaque:common_utility:0.0.1-SNAPSHOT"){
        isChanging = true
    }
    //>>START
    //RUNTIME
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct:1.4.1.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.1.Final")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.4.1.Final")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    //==================================================================================================================
    //******************************************************************************************************************
    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    //cloud based logging and tracing
//    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
//    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    //cloud based configuration
//    implementation("org.springframework.cloud:spring-cloud-starter-config")
    //******************************************************************************************************************
    //OKTA SDK TO CREATE USERS
//    implementation("com.okta.sdk:okta-sdk-api:${okatVersion}")
//    runtimeOnly("com.okta.sdk:okta-sdk-impl:${okatVersion}")
//    runtimeOnly("com.okta.sdk:okta-sdk-httpclient:${okatVersion}")
    //==================================================================================================================
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//    implementation("com.okta.spring:okta-spring-boot-starter:1.4.0")
    //==================================================================================================================
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    //==================================================================================================================
//    implementation("org.springframework.boot:spring-boot-starter-amqp")
    //==================================================================================================================
//    implementation "org.springframework.boot:spring-boot-starter-batch'
//    implementation "org.springframework.boot:spring-boot-starter-mail'
//    implementation "org.springframework.boot:spring-boot-starter-quartz'
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.webjars:bootstrap:4.5.3")
    implementation("org.webjars:jquery:3.5.1")
    implementation("org.webjars:webjars-locator:0.40")

    implementation("org.passay:passay:1.6.0")

    implementation("com.sendgrid:sendgrid-java:4.0.1")

    implementation("org.liquibase:liquibase-core:4.1.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //implementation("spring-boot-starter-jta-atomikos")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    runtimeOnly("mysql:mysql-connector-java")
    //==============================================================================================
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    //TEST
    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }

    //testImplementation("org.springframework.amqp:spring-rabbit-test")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    //implementation("org.codehaus.groovy:groovy-all:3.0.2")
    testImplementation("org.codehaus.groovy:groovy:3.0.4")
    testImplementation("org.spockframework:spock-core:2.0-M2-groovy-3.0") {
        exclude("org.codehaus.groovy")
    }
    testImplementation("org.spockframework:spock-spring:2.0-M2-groovy-3.0")
    testImplementation ("cglib:cglib-nodep:3.3.0")

//    runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
//    runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
//    runtimeOnly("com.fasterxml.jackson.module:jackson-module-parameter-names")


    configurations["integrationTestImplementation"]("org.testcontainers:junit-jupiter")
    //testImplementation 'org.testcontainers:elasticsearch'
    //testImplementation 'org.testcontainers:neo4j'
//    testImplementation("org.testcontainers:postgresql")

//    testCompile("org.testcontainers:spock:1.15.0")
//    implementation platform("org.testcontainers:testcontainers-bom:1.15.0")
//    configurations["integrationTestImplementation"]("org.testcontainers:postgresql")
    configurations["integrationTestImplementation"]("org.testcontainers:mysql:1.15.0")
    //END
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

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

    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run

//    afterSuite {
//        if (!desc.parent) { // will match the outermost suite
//            val output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)"
//            val startItem = '|  ', endItem = '  |'
//            val repeatLength = startItem.length() + output.length() + endItem.length()
//            println('\n' + ('-' * repeatLength) + '\n' + startItem + output + endItem + '\n' + ('-' * repeatLength))
//        }
//    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

val integrationTest by tasks.creating(Test::class) {
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    //testClassesDirs = sourceSets.integrationTest.output.classesDirs
    //classpath = sourceSets.integrationTest.runtimeClasspath
    systemProperty("spring.profiles.active", "integration")
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

    finalizedBy(tasks.jacocoTestReport,tasks.check) // report is always generated after tests run
}
integrationTest.dependsOn(tasks.test)

tasks.named("check"){
    dependsOn("integrationTest")
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

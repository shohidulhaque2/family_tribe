import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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
//    id("com.github.spotbugs") version "4.7.0"
//    pmd
//    id ("io.gitlab.arturbosch.detekt") version "1.16.0", only kotlin
    checkstyle
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Database Plugin
    id("org.hibernate.gradle.tools") version "1.2.5"
    id("org.liquibase.gradle") version "2.0.3"
    //id("org.hibernate.orm") version "5.4.3.Final"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Docker Plugin
    id("com.bmuschko.docker-remote-api") version "6.6.1"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Documentation Plugin
    id("org.asciidoctor.convert") version "1.5.8"
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}

group = "com.shohidulhaque"
version = "0.0.1-SNAPSHOT"

checkstyle {
    configProperties = mapOf("config_loc" to file("${rootProject.rootDir}/config/checkstyle"))
    configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
}

//pmd {
//    isConsoleOutput = true
//    toolVersion = "6.21.0"
//    ruleSetFiles =  rootProject.files("config/pmd/pmd-rules.xml") //layout.files( file("team-props/static-analysis/pmd-rules.xml"))
//    //rulesMinimumPriority.set(5)
//    ruleSets = listOf()
//}

//tasks.withType<Checkstyle>().configureEach {
//        description = "Check code standard"
//        group = "verification"
//        configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
//        source(fileTree(baseDir = "src"))
//        include("**/*.java")
//        exclude("**/gen/**")
//        ignoreFailures = false
//    reports {
//        xml.isEnabled = false
//        html.isEnabled = true
////        html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-custom.xsl")
//    }
//}

//tasks.withType<Checkstyle>().configureEach {
//    reports {
//        xml.isEnabled = false
//        html.isEnabled = true
//    }
//}

//checkstyle {
////	toolVersion = '7.1'
//
//    toolVersion = "8.20"
//	//configFile = rootProject.file("config/checkstyle/checkstyle.xml")
//    config = "${rootDir}/config/checkstyle/checkstyle.xml"
//}


tasks {

//    val findbugs by registering(FindBugs::class) {
//        ignoreFailures = false
//        effort = "max"
//        reportLevel = "low"
//        classes = files("$project.buildDir/intermediates/javac")
//        setExcludeFilter(file("$rootProject/config/findbugs/findbugs-exclude.xml"))
//        source = fileTree("src/main/java/")
//        classpath = files()
//        reports {
//            xml.isEnabled = false
//            html.isEnabled = true
//            html.destination = file("$project.buildDir/outputs/findbugs/findbugs-output.html")
//        }
//    }

//    val pmd by registering(Pmd::class) {
//        ruleSetFiles = files("${project.rootDir}/config/pmd/pmd-rules.xml")
//        ignoreFailures = false
//        ruleSets = listOf<String>()
//        source(fileTree(baseDir = "src/main/java"))
//        include("**/*.kt")
//        exclude("**/gen/**")
//        reports {
//            xml.isEnabled = false
//            html.isEnabled = true
//            html.destination = file("$project.buildDir/outputs/pmd/pmd.html")
//        }
//    }

//    val checkstyle by registering(Checkstyle::class) {
//        description = "Check code standard"
//        group = "verification"
//        configFile = file("$project.rootDir/config/checkstyle/checkstyle.xml")
//        source(fileTree(baseDir = "src"))
//        include("**/*.kt")
//        exclude("**/gen/**")
//        ignoreFailures = false
//    }

}


//
//detekt {
//    buildUponDefaultConfig = true // preconfigure defaults
//    allRules = false // activate all available (even unstable) rules.
//    config = files("$projectDir/config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
//    baseline = file("$projectDir/config/detekt/baseline.xml") // a way of suppressing issues before introducing detekt
//
//    reports {
//        html.enabled = true // observe findings in your browser with structure and code snippets
//        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
//        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
//        sarif.enabled = true // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
//    }
//}
//
//repositories {
//    jcenter() // jcenter is needed https://github.com/Kotlin/kotlinx.html/issues/81
//}


//val detektProjectBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
//    description = "Overrides current baseline."
//    buildUponDefaultConfig.set(true)
//    ignoreFailures.set(true)
//    parallel.set(true)
//    setSource(files(rootDir))
//    config.setFrom(files("$projectDir/config/detekt/detekt.yml"))
//    baseline.set(file("$projectDir/config/detekt/baseline.xml"))
//    include("**/*.kt")
//    include("**/*.kts")
//    exclude("**/resources/**")
//    exclude("**/build/**")
//}


//tasks.withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
//    effort.set(com.github.spotbugs.snom.Effort.MAX)
//    reports.maybeCreate("xml").isEnabled = false
//    reports.maybeCreate("html").isEnabled = true
//}

//detekt {
//    // Version of Detekt that will be used. When unspecified the latest detekt
//    // version found will be used. Override to stay on the same version.
//    toolVersion = "1.16.0"
//    config = files("config/detekt/detekt.yml")
//    reports.html.enabled = true
//    reports.xml.enabled = true
//}
//
//
//
//
//tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
//    // Target version of the generated JVM bytecode. It is used for type resolution.
//    this.jvmTarget = "11"
//}


//hibernate {
//    enhance {
//        enableLazyInitialization= true
//        enableDirtyTracking = true
//        enableAssociationManagement = true
//        enableExtendedEnhancement = false
//    }
//}


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
    targetCompatibility = JavaVersion.VERSION_11
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

val integrationTestSourceSet = sourceSets.create("integrationTest") {
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

configurations {
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
    implementation("com.shohidulhaque:common_model:0.0.1-SNAPSHOT") {
        isChanging = true
    }
    implementation("com.shohidulhaque:common_utility:0.0.1-SNAPSHOT") {
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
    //******************************************************************************************************************
    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    //cloud based logging and tracing
//    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
//    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    //cloud based configuration
//    implementation("org.springframework.cloud:spring-cloud-starter-config")
    //******************************************************************************************************************

    //==================================================================================================================
    //OKTA SDK TO CREATE USERS
    //==================================================================================================================
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//    implementation("com.okta.spring:okta-spring-boot-starter:1.4.0")
    //==================================================================================================================
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    //==================================================================================================================
//    runtimeOnly("com.h2database:h2")

    //TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    //implementation("org.codehaus.groovy:groovy-all:3.0.2")
    testImplementation("org.codehaus.groovy:groovy:3.0.4")
    testImplementation("org.spockframework:spock-core:2.0-M2-groovy-3.0") {
        exclude("org.codehaus.groovy")
    }
    testImplementation("org.spockframework:spock-spring:2.0-M2-groovy-3.0")
    testImplementation("cglib:cglib-nodep:3.3.0")

    testImplementation("com.h2database:h2")
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
//    implementation "org.springframework.boot:spring-boot-starter-batch'
//    implementation "org.springframework.boot:spring-boot-starter-mail'
//    implementation "org.springframework.boot:spring-boot-starter-quartz'
    implementation("org.springframework.boot:spring-boot-starter-validation")


    implementation("org.liquibase:liquibase-core:4.1.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    runtimeOnly("mysql:mysql-connector-java")
    //implementation("spring-boot-starter-jta-atomikos")

    //==============================================================================================
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

tasks.named("check") {
    dependsOn("integrationTest")
}

val integrationTest by tasks.creating(Test::class) {
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    //testClassesDirs = sourceSets.integrationTest.output.classesDirs
    //classpath = sourceSets.integrationTest.runtimeClasspath
    systemProperty("spring.profiles.active", "integrationTest")
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

    finalizedBy(tasks.jacocoTestReport, tasks.check) // report is always generated after tests run
}
integrationTest.dependsOn(tasks.test)




tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

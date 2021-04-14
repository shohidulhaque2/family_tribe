//https://kotlindays.com/2019/12/06/multi-module-spring-boot-in-kotlin-dsl/index.html

buildscript {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "microservices"

plugins {
    kotlin("jvm") version "1.4.10" apply false
    kotlin("plugin.spring") version "1.4.10" apply false

}

//infrastructure services
//TODO: create cloud config service. i should have one somewhere that I have already implemented. have a look.
include("discovery_service")
include("edge_service")
include("config_cloud_service")
//include("transaction_server")
//TODO: add cloud config

include("api_rest_service")
//TODO: change to register_service
include("user_registry_service")
//TODO: might be better to change a general group service that it able to group people and give them different roles in the system.
//TODO: not sure how roles in this needs to relate to oauth2 scopes and how to relate them. front end will also need to be notified of any role changes as well.
//TODO: large piece of work though. requires more thinking. do after all services implemented.
include("chat_service")

include("profile_service")
include("storage_service")
include("mail_service")
include("email_service")
include("basket_service")
include("security_information_service")

//include("entry_request_response_logging_service")

include("personal_diary_service")
//TODO: NEXT TO IMPLEMENT. provides use case to add initial functionality to storage service. MinIO - uses SW3 Bucket API
//
    //text
    //audio
    //video

include("family_memories_service")

//family_memories_service
//add posts
//remove posts
//edit changes
//order album

//family_gifts_service
    //keep a list of gifts for an individual

//family_gathering_service
    //organise trips
    //book holidays together


include("common_model")
include("common_utility")
include("common_utility_test")

enableFeaturePreview("ONE_LOCKFILE_PER_PROJECT")

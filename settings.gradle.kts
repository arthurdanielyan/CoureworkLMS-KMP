rootProject.name = "LMS"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":corePresentation")
include(":domain")
include(":utils")
include(":data")
include(":featureLogin")
include(":featureHome")
include(":featureSearchBooks")
include(":featureBookDetails")
include(":featureEditBook")
include(":featureMyAddedBooks")
include(":featureFavorites")
include(":featureSignup")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = ("mapbox")
                // Use the secret token you stored in (global) gradle.properties as the password
                password = extra["MAPBOX_DOWNLOADS_TOKEN"].toString()
            }
        }
    }
}

//Added these to try to allow pulling these values in Kotlin string code (didn't work):
val mapbox_downloads_property by extra("MAPBOX_DOWNLOADS_TOKEN")
val mapbox_public_property by extra("MAPBOX_PUBLIC_TOKEN")
val aerisweather_id_property by extra("AERISWEATHER_ID")
val aerisweather_secret_property by extra("AERISWEATHER_SECRET")

rootProject.name = "MapsTest"
include(":app")

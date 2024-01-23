pluginManagement {
    repositories {
        jcenter() // Warning: this repository is going to shut down soon
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        jcenter() // Warning: this repository is going to shut down soon
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials.username = "mapbox"
            credentials.password = ""
            authentication.create<BasicAuthentication>("basic")
        }


    }
}

rootProject.name = "NCM UW V2"
include(":app", ":NCMCommons")

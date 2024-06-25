pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        maven {
            setUrl("https://repo1.maven.org/maven2/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven {
            setUrl("https://repo1.maven.org/maven2/")
        }
    }
}

rootProject.name = "AndroidReflectionLearn"
include(":app")
include(":java_lib")
include(":plugin")

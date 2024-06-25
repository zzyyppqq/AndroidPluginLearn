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

rootProject.name = "AndroidPluginLearn"
include(":host")
include(":reflection_java")
include(":plugin_apk")
include(":plugin_core")
include(":reflection_sample")
include(":plugin_skin_core")
include(":plugin_skin_apk")

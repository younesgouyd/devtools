rootProject.name = "devtools"

include(":main")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val versions = object {
                val java = version("java", "20")
                val kotlin = version("kotlin", "1.9.24")
                val junit = version("junit", "5.11.0")
                val coroutines = version("coroutines", "1.8.1")
                val compose = version("compose", "1.6.11")
            }

            plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm").versionRef(versions.kotlin)
            plugin("compose", "org.jetbrains.compose").versionRef(versions.compose)

            library("junit.api", "org.junit.jupiter", "junit-jupiter-api").versionRef(versions.junit)
            library("junit.engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef(versions.junit)

            library("coroutines.core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef(versions.coroutines)
        }
    }
}
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
                val kotlin = version("kotlin", "2.0.20")
                val junit = version("junit", "5.11.0")
                val coroutines = version("coroutines", "1.8.1")
                val compose = version("compose.jetbrains", "1.6.11")
                val serialization = object {
                    val plugin = version("serialization.plugin", "2.0.20")
                    val dependency = version("serialization.dependency", "1.7.1")
                }
                val jdom = version("jdom", "2.0.6.1")
            }

            plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm").versionRef(versions.kotlin)
            plugin("compose.jetbrains", "org.jetbrains.compose").versionRef(versions.compose)
            plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef(versions.serialization.plugin)
            plugin("compose.compiler", "org.jetbrains.kotlin.plugin.compose").versionRef(versions.kotlin)

            library("junit.api", "org.junit.jupiter", "junit-jupiter-api").versionRef(versions.junit)
            library("junit.engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef(versions.junit)

            library("coroutines.core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef(versions.coroutines)
            library("serialization", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef(versions.serialization.dependency)
            library("jdom", "org.jdom", "jdom2").versionRef(versions.jdom)
        }
    }
}
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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MoneyTracker"
include(":app")
include(":core:common")
include(":core:designsystem")
include(":core:model")
include(":core:datastore")
include(":core:database")
include(":core:testing")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":feature:setting")
include(":feature:home")
include(":feature:transaction")

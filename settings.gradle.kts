pluginManagement {
    repositories {
        google()
        google()
        google()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        google()
        google()
        google()
        mavenCentral()
    }
}
rootProject.name = "Vespa"
include(
    ":app",
    ":benchmark:baselineprofile",
    ":core:common",
    ":core:network",
    ":core:database",
    ":core:datastore",
    ":data:repository",
    ":domain",
    ":feature:main",
    ":feature:chaos",
    ":feature:simulador",
    ":feature:diagnostico",
    ":feature:entrevista",
    ":feature:investigador"
)

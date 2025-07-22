rootProject.name = "com.spoonofcode.dojopro.ktor-dojo-pro"

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            from(files("gradle/libs.versions.toml"))
        }
        create("plugins") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}
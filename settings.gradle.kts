rootProject.name = "com.spoonofcode.ktor-dojo-pro"

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
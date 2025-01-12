plugins {
    id("org.spongepowered.gradle.vanilla").version("0.2")
}

repositories {
    maven {
        url = uri("https://repo-new.spongepowered.org/repository/maven-public/")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

dependencies {
    "shadedApi"(project(":common:implementation"))
    "annotationProcessor"("org.spongepowered:spongeapi:9.0.0-SNAPSHOT")
    "shadedImplementation"("org.spongepowered:spongeapi:9.0.0-SNAPSHOT")
    "annotationProcessor"("org.spongepowered:mixin:0.8.2:processor")
}

minecraft {
    version("1.17.1")
    runs {
        server()
        client()
    }
}

tasks.named<Jar>("jar") {
    manifest {
        //attributes(
        //        mapOf("MixinConfigs" to "terra.mixins.json")
        //)
    }
}
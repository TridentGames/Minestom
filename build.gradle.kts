plugins {
    `maven-publish`
    id("minestom.common-conventions")
}

allprojects {
    group = "net.minestom.server"
    version = "1.0"
    description = "Lightweight and multi-threaded Minecraft server implementation"
}

sourceSets {
    main {
        java {
            srcDir(file("src/autogenerated/java"))
        }
    }
}

tasks {
    withType<Javadoc> {
        (options as? StandardJavadocDocletOptions)?.apply {
            encoding = "UTF-8"

            // Custom options
            addBooleanOption("html5", true)
            addStringOption("-release", "17")
            // Links to external javadocs
            links("https://docs.oracle.com/en/java/javase/17/docs/api/")
            links("https://jd.adventure.kyori.net/api/${libs.versions.adventure.get()}/")
        }
    }
    withType<Zip> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

dependencies {
    // Junit Testing Framework
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    // Only here to ensure J9 module support for extensions and our classloaders
    testCompileOnly(libs.mockito.core)


    // Logging
    api(libs.log4j.core)
    api(libs.log4j.slf4j)

    // Libraries required for the terminal
    implementation(libs.bundles.terminal)

    // Performance improving libraries
    api(libs.caffeine)
    api(libs.fastutil)

    // Code modification
    api(libs.bundles.asm)
    api(libs.mixin)

    // Libraries
    api(libs.guava)
    api(libs.gson)
    api(libs.sparseBitSet)
    api(libs.jcTools)
    // Path finding
    api(libs.hydrazine)

    // Adventure, for user-interface
    api(libs.bundles.adventure)

    // Kotlin Libraries
    api(libs.bundles.kotlin)

    // Extension Management System dependency handler.
    api(libs.dependencyGetter)

    // Minestom Data (From MinestomDataGenerator)
    api(libs.minestomData)

    // NBT parsing/manipulation/saving
    api("com.github.jglrxavpok:Hephaistos:${libs.versions.hephaistos.get()}")
    implementation("com.github.jglrxavpok:Hephaistos:${libs.versions.hephaistos.get()}:gson")
    implementation("com.github.jglrxavpok:Hephaistos:${libs.versions.hephaistos.get()}") {
        capabilities {
            requireCapability("org.jglrxavpok.nbt:Hephaistos-gson")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

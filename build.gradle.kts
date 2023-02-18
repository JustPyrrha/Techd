import org.jetbrains.changelog.date
import org.jetbrains.changelog.Changelog

val isCI = System.getenv("CI") != null
val actionsRunNumber = "GITHUB_RUN_NUMBER"

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    java
    alias(libs.plugins.loom)
    alias(libs.plugins.licenser)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.changelog)
    alias(libs.plugins.githooks)
    `maven-publish`
    signing
}

val buildNumber: String = if (isCI && System.getenv(actionsRunNumber) != null) {
    System.getenv(actionsRunNumber)
} else {
    "local"
}
val modVersion = "0.1.0-a.$buildNumber"
val isSnapshot = modVersion.contains("-a.") || modVersion.contains("-b.")
group = "gay.pyrrha"
version = "$modVersion+${libs.versions.minecraft.get()}"
println("Techd: $version")

repositories {
    mavenCentral()
    maven("https://maven.quiltmc.org/repository/snapshot/") {
        name = "Quilt (Snapshots)"
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(variantOf(libs.quilt.mappings) { classifier("intermediary-v2") })
    modImplementation(libs.quilt.loader)
    modImplementation(libs.quilt.qsl.all)
    modImplementation(libs.quilt.fapi.datagen)

    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}

sourceSets {
    main {
        resources {
            srcDir("src/main/generated")
        }
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("quilt.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    wrapper {
        gradleVersion = "7.6"
        distributionType = Wrapper.DistributionType.BIN
    }

    getByName<Test>("test") {
        useJUnitPlatform()
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName}" }
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
}

loom {
    runtimeOnlyLog4j.set(true)
    runs {
        create("datagen") {
            client()
            name("Datagen")
            runDir("run")
            vmArgs(
                "-Dfabric-api.datagen",
                "-Dfabric-api.datagen.output-dir=${file("src/main/generated")}",
                "-Dfabric-api.datagen.modid=techd"
            )
        }
    }
}

license {
    rule(files("codeformat/HEADER"))
    include("**/*.java")
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("Ij0pCD2g")
    versionNumber.set(modVersion)
    versionType.set(
        if (modVersion.contains("-a.")) {
            "alpha"
        } else if (modVersion.contains("-b.")) {
            "beta"
        } else {
            "release"
        }
    )
    uploadFile.set(tasks.remapJar.get())
    additionalFiles.add(tasks.remapSourcesJar.get())
    dependencies {
        required.project("qsl")
    }
    changelog.set(provider {
        if(isSnapshot) {
            "No changelog was specified." // default listed in minotaur readme.
        } else {
            project.changelog.renderItem(
                project.changelog
                    .getUnreleased()
                    .withHeader(false)
                    .withEmptySections(false),
                Changelog.OutputType.MARKDOWN
            )
        }
    })
    syncBodyFrom.set(rootProject.file("README.md").readText())
}

changelog {
    version.set(modVersion)
    path.set(file("CHANGELOG.md").canonicalPath)
    header.set(provider { "[${modVersion}] - ${date()}" })
    headerParserRegex.set("""(\d+\.\d+)""")
    introduction.set(
        """
            Tech'd - t e c h n o l o g y
        """.trimIndent()
    )
    itemPrefix.set("-")
    keepUnreleasedSection.set(true)
    unreleasedTerm.set("[Unreleased]")
    groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Security"))
    lineSeparator.set("\n")
    combinePreReleases.set(true)
}

gitHooks {
    setHooks(mapOf("pre-commit" to "checkLicenses"))
}

publishing {
    publications {
        create("mvn", MavenPublication::class.java) {
            from(components["java"])
        }
    }
    repositories {
        val repo = if(isSnapshot) { "snapshots" } else { "releases" }
        maven("https://mvn.pyrrha.gay/$repo/") {
            name = "mvn"
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

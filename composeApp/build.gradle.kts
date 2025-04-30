import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        val desktopTest by getting

        val desktopChainTest by creating {
            kotlin.srcDir("src/desktopChainTest/kotlin")
            resources.srcDir("src/desktopChainTest/resources")
        }

        afterEvaluate {
            val desktopMainCompilation = kotlin.targets.getByName("desktop").compilations.getByName("main")
            val desktopChainTestCompilation = kotlin.targets.getByName("desktop").compilations.create("chainTest")
            desktopChainTestCompilation.defaultSourceSet.dependsOn(desktopChainTest)
            desktopChainTestCompilation.associateWith(desktopMainCompilation)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
            implementation(libs.kbsky.core)


        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }

        desktopTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.junit5)
        }

        desktopChainTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.junit5)
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }

    }
}

afterEvaluate {
    tasks.register<Test>("desktopChainTest") {
        description = "Runs full chain tests"

        val fullChainTestCompilation = kotlin.targets.getByName("desktop").compilations.getByName("chainTest")
        testClassesDirs = fullChainTestCompilation.output.classesDirs
        classpath = fullChainTestCompilation.output.classesDirs +
                fullChainTestCompilation.compileDependencyFiles +
                fullChainTestCompilation.output.resourcesDir.let { if (it.exists()) files(it) else files() }

        useJUnitPlatform()
    }
}


compose.desktop {
    application {
        mainClass = "nl.lengrand.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "nl.lengrand"
            packageVersion = "1.0.0"
        }
    }
}

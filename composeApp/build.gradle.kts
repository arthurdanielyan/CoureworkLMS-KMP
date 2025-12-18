import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.filekit.dialogs)

            implementation(projects.utils)
            implementation(projects.data)
            implementation(projects.domain)
            implementation(projects.corePresentation)
            implementation(projects.featureLogin)
            implementation(projects.featureHome)
            implementation(projects.featureSearchBooks)
            implementation(projects.featureBookDetails)
            implementation(projects.featureEditBook)
            implementation(projects.featureMyAddedBooks)
            implementation(projects.featureFavorites)
            implementation(projects.featureSignup)
            implementation(projects.featureRecoverPassword)
        }
    }
}

tasks.register("packForXcode") {
    group = "build"
    dependsOn("linkDebugFrameworkIosArm64", "linkDebugFrameworkIosSimulatorArm64")

    doLast {
        val targetDir = buildDir.resolve("xcode-frameworks")
        targetDir.mkdirs()

        copy {
            from(buildDir.resolve("bin/iosArm64/debugFramework/ComposeApp.framework"))
            into(targetDir)
        }
        copy {
            from(buildDir.resolve("bin/iosSimulatorArm64/debugFramework/ComposeApp.framework"))
            into(targetDir)
        }
    }
}


android {
    namespace = "com.coursework.lms"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.coursework.lms"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


plugins {
    alias(libs.plugins.android.dynamic.feature)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.michaelbel.movies.instant"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    flavorDimensions += "version"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
    }

    buildFeatures {
        compose = true
    }

    productFlavors {
        create("foss") {
            dimension = "version"
        }
        create("hms") {
            dimension = "version"
        }
        create("gms") {
            dimension = "version"
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(project(":androidApp"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(libs.bundles.androidx.appcompat)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.google.services.instantapps)
}
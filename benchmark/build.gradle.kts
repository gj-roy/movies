plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.michaelbel.movies.benchmark"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = getByName("debug").signingConfig
            matchingFallbacks.add("release")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.benchmark.macro.ExperimentalBaselineProfilesApi"
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
    }

    targetProjectPath = ":androidApp"

    experimentalProperties["android.experimental.self-instrumenting"] = true
}

/*androidComponents {
    beforeVariants(selector().all()) {
        it.enabled = it.buildType == "benchmark"
    }
}*/

dependencies {
    implementation(libs.bundles.androidx.test.espresso)
    implementation(libs.androidx.benchmark.macro.junit)
    implementation(libs.androidx.test.ext.junit.ktx)
    implementation(libs.androidx.test.uiautomator)
}
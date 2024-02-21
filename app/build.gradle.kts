import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val keysPropertiesFile: File = rootProject.file("keys.properties")
val keysProperties = Properties()
keysProperties.load(FileInputStream(keysPropertiesFile))


android {
    namespace = "com.karokojnr.tchatter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.karokojnr.tchatter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "DITTO_APP_ID", keysProperties["DITTO_APP_ID"] as String)
            buildConfigField("String", "DITTO_TOKEN", keysProperties["DITTO_TOKEN"] as String)
        }
        debug {
            buildConfigField("String", "DITTO_APP_ID", keysProperties["DITTO_APP_ID"] as String)
            buildConfigField("String", "DITTO_TOKEN", keysProperties["DITTO_TOKEN"] as String)
            applicationIdSuffix = ".debug"
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)

    implementation(composeBom)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.ditto)
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.5.4")



    testImplementation(libs.bundles.unit.tests)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.instrumented.tests)

    debugImplementation(libs.bundles.compose.debug)
}


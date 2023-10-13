import org.apache.xerces.xs.StringList
import org.gradle.internal.impldep.com.sun.jna.StringArray

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kkyoungs.webview"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kkyoungs.webview"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        allWarningsAsErrors = false
        freeCompilerArgs = listOf("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }



    dependencies {

        implementation("androidx.core:core-ktx:1.7.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
        implementation("androidx.compose.runtime:runtime-livedata:1.2.0")
        implementation("androidx.activity:activity-compose:1.3.1")
        implementation(platform("androidx.compose:compose-bom:2023.03.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")

        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-rc01")
    }
}

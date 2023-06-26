plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.github.anastr.speedometer.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.github.anastr.speedometer.android"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))

    implementation("androidx.activity:activity-compose:1.7.2")

    val composeBom = platform("androidx.compose:compose-bom:2023.04.01")
    implementation (composeBom)
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-tooling")
    implementation ("androidx.compose.foundation:foundation")
    implementation ("androidx.compose.material:material")
    // For compose preview
    implementation ("androidx.compose.ui:ui-tooling-preview")
    debugImplementation ("androidx.compose.ui:ui-tooling")
}
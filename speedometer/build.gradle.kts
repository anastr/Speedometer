plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("convention.publication")
}

group = "com.github.anastr"
version = "1.0.0-ALPHA02"

kotlin {
    jvm("desktop") {
        jvmToolchain(11)
    }

    js(IR) {
        browser()
    }

    androidTarget {
        publishLibraryVariants("release", "debug")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Speedometer Module"
        homepage = "https://github.com/anastr/Speedometer"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        authors = "Anas Altair"
        license = "MIT"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "speedometer"
            isStatic = true
        }
//        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                api("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
            }
        }
        val desktopMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.github.anastr.speedometer"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
}
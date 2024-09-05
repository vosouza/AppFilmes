import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.vosouza.appfilmes"
    compileSdk = 34

    buildFeatures{
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.vosouza.appfilmes"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val prop = Properties()
            prop.load(project.rootProject.file("local.properties").reader())
            buildConfigField("String", "API_KEY", "\"${prop.getProperty("API_KEY")}\"")
        }

        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true

            val prop = Properties()
            prop.load(project.rootProject.file("local.properties").reader())
            buildConfigField("String", "API_KEY", "\"${prop.getProperty("API_KEY")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    configurations.implementation{
        exclude(group = "com.intellij", module = "annotations")
    }
}

dependencies {

    //ROOM
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.compiler)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    ksp(libs.androidx.room.ktx)

    //hilt
    implementation(libs.androidx.hilt)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)

    //network
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.converter)

    //gson
    implementation(libs.google.gson)

    //okhttp
    api(libs.squareup.okhttp)
    api(libs.squareup.okhttp.logging)
    api(platform(libs.squareup.okhttp.bom))

    //mockk
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.agent)

    //coroutine test
    testImplementation(libs.kotlinx.coroutines)

    //splash
    implementation(libs.androidx.core.splashscreen)

    //coil - image loader
    implementation(libs.io.coil)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
import java.util.Properties

val properties = Properties()
properties.load(project.rootProject.file("local.properties").reader())

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // com.google.dagger.hilt.android
    alias(libs.plugins.hilt.android)
    // com.google.devtools.ksp
    alias(libs.plugins.google.devtools.ksp)
    // androidx.room
    alias(libs.plugins.androidx.room)
}

android {
    namespace = libs.versions.namespace.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    room {
        schemaDirectory("$projectDir/schemas")
    }

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        defaultConfig {
            buildConfigField(
                "String",
                "API_KEY",
                "\"${properties.getProperty("api.key") ?: ""}\""
            )
        }
        debug {
            this.buildConfigField(
                "String",
                "BASE_URL",
                "\"${properties.getProperty("base.url.debug") ?: ""}\""
            )
        }
        release {
            this.buildConfigField(
                "String",
                "BASE_URL",
                "\"${properties.getProperty("base.url.release") ?: ""}\""
            )
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

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

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android.tooling)
    ksp(libs.kapt.android.hilt)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.retrofit.http)
    implementation(libs.okhttp3.logging)

    implementation(libs.coil.image)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}
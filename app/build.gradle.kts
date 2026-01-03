plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.2.21"
}

android {
    namespace = "com.honoursigbeku.studyhubapp"
    compileSdk = 36
    ndkVersion = "29.0.14206865"

    packaging {
        resources {
            excludes += "**/dump_syms/**"
        }
    }

    defaultConfig {
        applicationId = "com.honoursigbeku.studyhubapp"
        minSdk = 34
        targetSdk = 36
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"https://rxyknyacnoayixkhdpnl.supabase.co\""
        )
        buildConfigField(
            "String",
            "SUPABASE_KEY",
            "\"sb_publishable_bgETqLXfxtGLr2r72GYAlA_RAN1Dvzy\""
        )
    }
    kotlin {
        jvmToolchain(17)
    }
    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk.debugSymbolLevel = "FULL"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}


dependencies {
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.media3.database)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(platform(libs.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.ktor.client.android)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.room.runtime)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.firebase.ui.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.play.services.auth)
    implementation(libs.coil.compose)
    implementation(libs.androidx.credentials)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui.text.google.fonts)
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
    implementation(kotlin("test"))
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("com.android.application")
    kotlin ("android")
    kotlin ("kapt")
    id ("dagger.hilt.android.plugin")
}

android {
//    don't know why but it comes with error when using [BuildConfig.compileSdkVersion]!
    compileSdk = 30
    buildToolsVersion = "30.0.3"
    ndkVersion = "21.1.6352462"

    defaultConfig {
        applicationId = BuildConfig.applicationId
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    tasks.withType(KotlinCompile::class.java) {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":feature:generator")))
    implementation(project(mapOf("path" to ":feature:cleaner")))

    implementation(Dependencies.kotlin_stdlib)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.fragment_ktx)

    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_tooling)
    implementation(Dependencies.compose_compiler)
    implementation(Dependencies.compose_material)
    implementation(Dependencies.compose_activity)

    implementation(Dependencies.hilt_android)
    kapt(Dependencies.hilt_compiler)

    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_extensions)
    implementation(Dependencies.lifecycle_viewModel)
    implementation(Dependencies.lifecycle_common)

    implementation(Dependencies.coroutines)
    implementation(Dependencies.coroutines_android)

//    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.7")
}

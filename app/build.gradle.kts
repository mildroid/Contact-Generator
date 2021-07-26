import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("com.android.application")
    kotlin ("android")
    kotlin ("kapt")
    id ("dagger.hilt.android.plugin")
}

android {
//    don't know why but it comes with error when using [BuildConfig.compileSdkVersion]!
    compileSdkVersion (30)

    defaultConfig {
        applicationId = BuildConfig.applicationId
        minSdkVersion (BuildConfig.minSdkVersion)
        targetSdkVersion (BuildConfig.targetSdkVersion)
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName
        buildFeatures.viewBinding = true

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
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":feature:generator")))

    implementation(Dependencies.kotlin_stdlib)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.fragment_ktx)

    implementation(Dependencies.hilt_android)
    kapt(Dependencies.hilt_compiler)

    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_extensions)
    implementation(Dependencies.lifecycle_viewModel)
    implementation(Dependencies.lifecycle_common)

    implementation(Dependencies.coroutines)
    implementation(Dependencies.coroutines_android)
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("com.android.application")
    kotlin ("android")
    kotlin ("kapt")
//    id "dagger.hilt.android.plugin"
}

android {
    compileSdkVersion (30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId = "com.mildroid.contactgenerator"
        minSdkVersion (21)
        targetSdkVersion (30)
        versionCode = 1
        versionName = "1.0"

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
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            sourceCompatibility = JavaVersion.VERSION_1_8.toString()
            targetCompatibility = JavaVersion.VERSION_1_8.toString()
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
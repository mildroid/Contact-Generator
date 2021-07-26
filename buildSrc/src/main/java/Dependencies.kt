object Versions {
    const val kotlin = "1.5.21"
    const val core_ktx = "1.5.0"
    const val appcompat = "1.3.0"
    const val material = "1.3.0"
    const val constraint_layout = "2.0.4"
    const val work = "2.5.0"
    const val coroutines = "1.5.1"
    const val hilt = "2.35"
    const val lifecycle = "2.3.1"
    const val fragment = "1.3.6"

    const val gradle_plugin = "4.2.2"
}

object Dependencies {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.work}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha02"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_common = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
}

object Plugins {
    const val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle_plugin}"
    const val hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

/*
*
*       test Libraries
*
*     testImplementation("junit:junit:4.13.2")
      androidTestImplementation("androidx.test.ext:junit:1.1.2")
      androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    *
    *
    * */
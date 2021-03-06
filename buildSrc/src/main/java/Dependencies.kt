/**
 * Versions.
 */
object Versions {
    const val kotlin = "1.5.21"
    const val core_ktx = "1.5.0"
    const val appcompat = "1.3.0"
    const val material = "1.3.0"
    const val constraint_layout = "2.0.4"
    const val work = "2.5.0"
    const val coroutines = "1.5.1"
    const val hilt = "2.37"
    const val hilt_work = "1.0.0"
    const val lifecycle = "2.3.1"
    const val fragment = "1.3.6"
    const val compose = "1.0.1"
    const val compose_activity = "1.3.1"

    const val gradle_plugin = "7.0.1"
}

/**
 * Dependencies.
 */
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

    const val hilt_work = "androidx.hilt:hilt-work:${Versions.hilt_work}"
    const val hilt_work_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_work}"

    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha02"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_common = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    const val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val compose_io_tolling_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val compose_compiler = "androidx.compose.compiler:compiler:${Versions.compose}"
    const val compose_material = "androidx.compose.material:material:${Versions.compose}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.compose_activity}"

    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
}

/**
 * Plugins.
 */
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
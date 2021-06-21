object Versions {
    const val kotlin = "1.5.10"
    const val core_ktx = "1.5.0"
    const val appcompat = "1.3.0"
    const val material = "1.3.0"
    const val constraint_layout = "2.0.4"
    const val work = "2.5.0"
    const val coroutines = "1.5.0"

    const val gradle_plugin = "4.2.1"
}

object Dependencies {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.work}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val android_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object Plugins {
    const val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle_plugin}"
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
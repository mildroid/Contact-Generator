plugins {
    id ("com.android.library")
    kotlin ("android")
    kotlin ("kapt")
    id ("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/common-library-config.gradle")
dependencies {
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":core")))

    implementation(Dependencies.kotlin_stdlib)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.lifecycle_extensions)

    api(Dependencies.work_manager)

    implementation(Dependencies.hilt_android)
    kapt(Dependencies.hilt_compiler)

    api(Dependencies.hilt_work)
    kapt(Dependencies.hilt_work_compiler)
}
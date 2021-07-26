plugins {
    id ("com.android.library")
    kotlin ("android")
}

apply(from = "$rootDir/common-library-config.gradle")

dependencies {
    implementation(Dependencies.kotlin_stdlib)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
}
plugins {
    id ("java-library")
    id ("kotlin")
    id ("kotlin-kapt")
}

java.apply {
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
}

dependencies {
    implementation (Dependencies.kotlin_stdlib)

    implementation("com.google.dagger:dagger:2.37")
    kapt("com.google.dagger:dagger-compiler:2.37")
}
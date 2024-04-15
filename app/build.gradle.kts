plugins {
//    alias(libs.plugins.androidApplication)
//    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)

    id("module-plugin")
}

android {
    namespace = "com.jerry.moneytracker"
    defaultConfig {
        applicationId = "com.jerry.moneytracker"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {

    //compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.compose)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)
}
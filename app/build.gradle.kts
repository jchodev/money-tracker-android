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

    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    //implementation(project(":core:testing"))
    implementation(project(":feature:home"))
    implementation(project(":feature:setting"))
    implementation(project(":feature:transaction"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)

    //compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.compose)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    //timber
    implementation(libs.timber)
}
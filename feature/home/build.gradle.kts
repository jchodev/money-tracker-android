@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-plugin")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)

    id("kotlin-parcelize")
}

android {
    namespace = "com.jerry.moneytracker.feature.home"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
    implementation(project(":core:database"))
    implementation(project(":core:testing"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)

    //jetpack compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.compose)
    debugImplementation(libs.compose.ui.tooling.debug)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    //timber
    implementation(libs.timber)

    //junit5
    testImplementation(libs.bundles.junit5.test.implementation)
    testRuntimeOnly(libs.bundles.junit5.test.runtime.only)
    //turbine
    testImplementation(libs.turbine)
    testImplementation(project(":core:testing"))

    //UI testing
    androidTestImplementation (libs.compose.ui.junitfour)
    debugImplementation (libs.compose.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
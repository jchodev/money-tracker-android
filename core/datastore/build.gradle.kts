plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-without-jetpack-plugin")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)

    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.jerry.moneytracker.core.datastore"
}

dependencies {

    implementation(project(":core:model"))

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    //datastore
    implementation (libs.androidx.datastore.core)
    implementation (libs.androidx.datastore)

    implementation (libs.kotlinx.serialization.json)

    //junit5
    testImplementation(libs.bundles.junit5.test.implementation)
    testRuntimeOnly(libs.bundles.junit5.test.runtime.only)

//    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.2")
}
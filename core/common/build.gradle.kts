plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-without-jetpack-plugin")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)

    id("kotlin-parcelize")
}

android {
    namespace = "com.jerry.moneytracker.core.common"
}

dependencies {

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

}
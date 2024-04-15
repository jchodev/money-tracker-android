plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-plugin")


    id("kotlin-parcelize")
}

android {
    namespace = "com.jerry.moneytracker.core.designsystem"
}

dependencies {

    //jetpack compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.androidx.compose)
    debugImplementation(libs.compose.ui.tooling.debug)

}
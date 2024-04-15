// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //alias(libs.plugins.android.application).apply(false)
    //alias(libs.plugins.android.library) apply false
    //alias(libs.plugins.android.kotlin).apply(false)
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false

    //ksp
    alias(libs.plugins.google.ksp).apply(false)
    //hilt
    alias(libs.plugins.google.hilt).apply(false)

}
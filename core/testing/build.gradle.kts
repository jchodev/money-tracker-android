@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-without-jetpack-plugin")

}

android {
    namespace = "com.jerry.moneytracker.core.testing"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
}
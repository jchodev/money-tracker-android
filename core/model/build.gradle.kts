plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")

    id("module-without-jetpack-plugin")

    id("kotlin-parcelize")
}

android {
    namespace = "com.jerry.moneytracker.core.model"
}

dependencies {

}
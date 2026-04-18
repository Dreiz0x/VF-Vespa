plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.baselineprofile)
}
android {
    namespace = "dev.vskelk.cdf.benchmark"
    compileSdk = 35
    targetProjectPath = ":app"
    defaultConfig {
        minSdk = 28
        targetSdk = 35
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
baselineProfile { useConnectedDevices = true }
dependencies {
    implementation(libs.junit4)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.benchmark.macro.junit4)
}

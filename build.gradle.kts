plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.serialization).apply(false)
    id("org.jetbrains.dokka") version "1.9.20"
}
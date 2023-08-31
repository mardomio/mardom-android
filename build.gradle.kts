// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-rc01" apply false

    val kotlinVersion = "1.8.22"
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion apply false
    id("com.google.devtools.ksp") version "$kotlinVersion-1.0.11" apply false

    id("com.google.dagger.hilt.android") version "2.44" apply false
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.0").apply(false)
    id("com.android.library").version("8.1.0").apply(false)
    id("org.jetbrains.compose").apply(false)
    kotlin("android").apply(false)
    kotlin("multiplatform").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

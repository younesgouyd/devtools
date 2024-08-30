group = "dev.younesgouyd"
version = "0.1.0"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.jetbrains)
    alias(libs.plugins.serialization)
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(libs.coroutines.core)

    implementation(compose.desktop.currentOs) {
        exclude("org.jetbrains.compose.material") // todo
    }
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(libs.serialization)
    implementation(libs.jdom)
}
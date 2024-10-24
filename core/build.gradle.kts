plugins {
    // Apply the common conventions plugin for the URL shortener project
    id("urlshortener-common-conventions")
}

dependencies {
    // Add ZXing dependencies for QR code generation
    implementation("com.google.zxing:core:3.4.1") 
    implementation("com.google.zxing:javase:3.4.1") 

    // Add Kotlin test library for unit testing
    testImplementation(libs.kotlin.test)

    // Add Mockito Kotlin library for mocking in tests
    testImplementation(libs.mockito.kotlin)

    // Add JUnit Jupiter library for writing and running tests
    testImplementation(libs.junit.jupiter)

    // Add JUnit Platform Launcher for launching tests
    testRuntimeOnly(libs.junit.platform.launcher)
}

import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // dagger-hilt plugins
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.gradle.plugin)
}

android {
    namespace = "com.example.gitgo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gitgo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        val githubTokenValue = getLocalProperty("GITHUB_TOKEN", project)
        buildConfigField("String", "GITHUB_TOKEN", "\"${githubTokenValue.replace("\"", "\\\"")}\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)

    // dagger-hilt dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //http logging interceptor dependency
    implementation(libs.logging.interceptor)

    // for hiltViewModel()
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.compose)
    implementation (libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.paging.compose)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}


fun getLocalProperty(key: String, project: Project): String {
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        val properties = Properties()
        try {
            FileInputStream(localPropertiesFile).use { input ->
                properties.load(input)
            }
            return properties.getProperty(key, "")
        } catch (e: Exception) {
            project.logger.warn("Could not load local.properties", e)
        }
    } else {
        project.logger.warn("local.properties file not found in root project.")
    }
    return ""
}


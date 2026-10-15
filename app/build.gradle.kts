
plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
}

android {
  namespace = "com.example"

  compileSdk = 36

  defaultConfig {
    applicationId = "com.aistudio.casacolina.vprxqd"

    minSdk = 24
    targetSdk = 36

    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner =
      "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false

      proguardFiles(
        getDefaultProguardFile(
          "proguard-android-optimize.txt"
        ),
        "proguard-rules.pro"
      )
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    isCoreLibraryDesugaringEnabled = true
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  // Compatibilidad de fechas con Android API 24

  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

  // =====================================
  // JETPACK COMPOSE
  // =====================================

  implementation(
    platform(libs.androidx.compose.bom)
  )

  implementation(
    libs.androidx.activity.compose
  )

  implementation(
    libs.androidx.compose.ui
  )

  implementation(
    libs.androidx.compose.ui.graphics
  )

  implementation(
    libs.androidx.compose.ui.tooling.preview
  )

  implementation(
    libs.androidx.compose.material3
  )

  implementation(
    libs.androidx.compose.material.icons.core
  )

  implementation(
    libs.androidx.compose.material.icons.extended
  )

  // =====================================
  // ANDROID CORE
  // =====================================

  implementation(
    libs.androidx.core.ktx
  )

  // =====================================
  // LIFECYCLE Y VIEWMODEL
  // =====================================

  implementation(
    libs.androidx.lifecycle.runtime.ktx
  )

  implementation(
    libs.androidx.lifecycle.runtime.compose
  )

  implementation(
    libs.androidx.lifecycle.viewmodel.compose
  )

  // =====================================
  // COROUTINES
  // =====================================

  implementation(
    libs.kotlinx.coroutines.android
  )

  implementation(
    libs.kotlinx.coroutines.core
  )

  // =====================================
  // RETROFIT - API GATEWAY
  // =====================================

  implementation(
    libs.retrofit
  )

  // =====================================
  // CONVERSION JSON - MOSHI
  // =====================================

  implementation(
    libs.converter.moshi
  )

  implementation(
    libs.moshi.kotlin
  )

  // =====================================
  // OKHTTP - CONEXION HTTPS
  // =====================================

  implementation(
    libs.okhttp
  )

  implementation(
    libs.logging.interceptor
  )

  // =====================================
  // COIL - IMAGENES DEL MENU
  // =====================================

  implementation(
    libs.coil.compose
  )

  // =====================================
  // PRUEBAS UNITARIAS
  // =====================================

  testImplementation(
    libs.junit
  )

  testImplementation(
    libs.androidx.junit
  )

  testImplementation(
    libs.kotlinx.coroutines.test
  )

  // =====================================
  // PRUEBAS ANDROID
  // =====================================

  androidTestImplementation(
    platform(libs.androidx.compose.bom)
  )

  androidTestImplementation(
    libs.androidx.compose.ui.test.junit4
  )

  androidTestImplementation(
    libs.androidx.espresso.core
  )

  androidTestImplementation(
    libs.androidx.junit
  )

  // =====================================
  // DEBUG
  // =====================================

  debugImplementation(
    libs.androidx.compose.ui.tooling
  )

  debugImplementation(
    libs.androidx.compose.ui.test.manifest
  )
}

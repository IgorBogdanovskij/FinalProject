plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.rickyandmorty"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rickyandmorty"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.activity)

    implementation(libs.androidx.compat)
    implementation(libs.material)
    implementation(libs.constraint.layout)
    implementation(libs.live.data.ktx)
    implementation(libs.view.model)
    implementation(libs.swipe.refresh.layout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // Glide
    implementation(libs.glide)

    // Room
    implementation(libs.room)
    ksp(libs.room.compiler)

    // ktx android
    implementation(libs.androidx.core)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    // RxJava
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    implementation(libs.room.rxjava3)
    implementation(libs.rxjava3.retrofit.adapter)

    // Blur
    implementation(libs.blurry)
    implementation(libs.glide.transform)

    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Dagger
    ksp(libs.google.dagger.compiler)
    implementation(libs.google.dagger)

    // Modules
    api(project(":domain"))
    api(project(":data"))
}

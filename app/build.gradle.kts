plugins {
    alias(libs.plugins.android.application)
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.serdararici.dronemarket"
    compileSdk = 34

    buildFeatures{
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.serdararici.dronemarket"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    val lifecycle_version = "2.8.4"
    val arch_version = "2.2.0"

    // ViewModel
    //implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation(libs.lifecycle.viewmodel.ktx)

    // LiveData
    //implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation(libs.lifecycle.livedata.ktx)

    //Room Library
    //implementation ("androidx.room:room-runtime:2.6.1")
    implementation (libs.androidx.room.runtime)
    //annotationProcessor ("androidx.room:room-compiler:2.6.1")
    annotationProcessor (libs.androidx.room.room.compiler)
    //implementation ("androidx.room:room-rxjava2:2.6.1")
    implementation (libs.androidx.room.room.rxjava2)
    //implementation ("io.reactivex.rxjava2:rxandroid:2.0.1")
    implementation (libs.rxjava2.rxandroid)

    //dagger-hilt
    //implementation("com.google.dagger:hilt-android:2.44")
    implementation(libs.google.hilt.android)
    //annotationProcessor("com.google.dagger:hilt-android-compiler:2.44")
    annotationProcessor(libs.google.hilt.android.compiler)

    //Google maps
    //implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation (libs.play.services.maps)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}
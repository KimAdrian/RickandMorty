plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.kimadrian.rickandmorty"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kimadrian.rickandmorty"
        minSdk = 21
        targetSdk = 33
        versionCode =  1
        versionName  = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled  = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility (JavaVersion.VERSION_1_8)
        targetCompatibility (JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.appcompat:appcompat:1.5.1")
    implementation ("com.google.android.material:material:1.7.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.4")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")

    //Glide
    val glideVersion = "4.13.2"
    implementation("com.github.bumptech.glide:glide:${glideVersion}")
    kapt("com.github.bumptech.glide:compiler:${glideVersion}")

    //Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-moshi:${retrofitVersion}")

    //Moshi
    val moshiVersion = "1.13.0"
    implementation("com.squareup.moshi:moshi-kotlin:${moshiVersion}")

    //Navigation
    val navigationVersion = "2.5.3"
    implementation("androidx.navigation:navigation-fragment-ktx:${navigationVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${navigationVersion}")

    //Hilt
    val hiltVersion = "2.44"
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${hiltVersion}")

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //SplashScreen
    implementation ("androidx.core:core-splashscreen:1.0.0")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}


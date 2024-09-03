plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.app.kalyanbusiness"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.kalyanbusiness"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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

    viewBinding{
        enable = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    android.buildFeatures.dataBinding = true
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Networking...
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.0.5")
    implementation ("com.hbb20:ccp:2.6.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")


    implementation ("com.github.michael-rapp:android-bottom-sheet:2.0.0")
    implementation ("net.danlew:android.joda:2.10.7")
    implementation ("com.wdullaer:materialdatetimepicker:4.2.3")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    //imagePicker
    implementation ("com.github.esafirm.android-image-picker:imagepicker:2.4.5")
    //circleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //webView
    implementation ("com.github.delight-im:Android-AdvancedWebView:v3.2.1")
    //ripple
    implementation("com.balysv:material-ripple:1.0.2")
    //dialogShow
    implementation ("com.github.f0ris.sweetalert:library:1.6.2")
    //slider
    implementation ("com.github.smarteist:autoimageslider:1.4.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
}
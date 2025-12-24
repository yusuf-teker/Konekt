import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    //alias(libs.plugins.ksp)
    //alias(libs.plugins.room)

    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    jvm()


    sourceSets {

        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.multiplatform.settings)
            api(libs.multiplatform.settings.coroutines)
            implementation(libs.napier.logging)

            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)
            implementation(libs.kotlinx.datetime)



            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
   }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.androidx.room.sqlite.wrapper)

        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }



    }
}

android {
    namespace = "org.yusufteker.konekt.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

dependencies{
    ksp(libs.androidx.room.compiler)
}
room {
    schemaDirectory("$projectDir/schemas")
}
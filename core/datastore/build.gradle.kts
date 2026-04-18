plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.vskelk.cdf.core.datastore"
    compileSdk = 35
    defaultConfig { minSdk = 26 }
}

androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            val variantName = variant.name.replaceFirstChar { it.uppercase() }
            val protoTask = project.tasks.findByName("generate${variantName}Proto")
            val kspTask = project.tasks.findByName("ksp${variantName}Kotlin")
            if (protoTask != null && kspTask != null) {
                kspTask.dependsOn(protoTask)
            }
        }
    }
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.java.get()}" }
    generateProtoTasks {
        all().forEach { task -> task.builtins { create("java") { option("lite") } } }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.hilt.android)
    implementation(libs.datastore)
    implementation(libs.protobuf.javalite)
    implementation(libs.security.crypto)
    implementation(libs.kotlinx.coroutines.android)
    ksp(libs.hilt.compiler)
}

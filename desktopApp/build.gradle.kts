import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    sourceSets {
        val ktorVersion = extra["ktor.version"] as String
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
                implementation(compose.material3)

                // desktop window styler
                implementation("com.mayakapps.compose:window-styler:0.3.2")

                // previews
                implementation(compose.preview)
                implementation(compose.uiTooling)

                // ktor server
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinMultiplatformComposeDesktopApplication"
            packageVersion = "1.0.0"
        }
    }
}

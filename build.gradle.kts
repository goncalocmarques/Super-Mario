plugins {
    id("java")
    id("application")
    id ("info.solidsoft.pitest") version "1.15.0"
}

group = "uni.ldts"
version = "dev"

pitest {
    junit5PluginVersion = "1.2.0"
}

application {
    mainClass = "uni.ldts.mario.SuperMario"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.mockito:mockito-core:3.7.7")
    implementation("com.googlecode.lanterna:lanterna:3.1.1")
}

tasks.test {
    useJUnitPlatform()
}

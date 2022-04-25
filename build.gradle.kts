plugins {
    id("java")
}

allprojects {

    apply(plugin = "java")

    repositories {
        mavenCentral()
        mavenLocal()

        dependencies {
            testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
            testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
            testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
        }

    }

    tasks {

        named<JavaCompile>("compileJava") {
            options.encoding = "UTF-8"
            options.release.set(17)
        }

        named<Test>("test") {
            useJUnitPlatform()
        }

    }

}

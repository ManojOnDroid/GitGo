import org.gradle.api.Project
import java.util.Properties
import java.io.FileInputStream

object BuildUtils { 

    fun getLocalProperty(key: String, project: Project): String {
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            val properties = Properties()
            try {
                FileInputStream(localPropertiesFile).use { input ->
                    properties.load(input)
                }
                return properties.getProperty(key, "")
            } catch (e: Exception) {
                project.logger.warn("Could not load local.properties from BuildUtils", e)
            }
        } else {
            project.logger.warn("local.properties file not found in root project (checked from BuildUtils).")
        }
        return ""
    }
}
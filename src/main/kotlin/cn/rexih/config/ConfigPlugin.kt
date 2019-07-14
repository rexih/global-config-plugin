package cn.rexih.plugin.config

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import com.sun.javafx.scene.CameraHelper.project
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.*


class ConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extraProperties = target.extensions.findByType(ExtraPropertiesExtension::class.java)
        if (null == configContent) {
            loadProperties();
        }
        configContent?.let {
            target.logger.warn("==========当前版本管理插件的配置==========\n${it}==========当前版本管理插件的配置==========")
        }
        configProperties?.stringPropertyNames()?.forEach {
            val propertyValue = configProperties!!.getProperty(it)
            extraProperties?.set(it, propertyValue)
//                target.logger.warn("properties :${it}:${propertyValue}")
        }
    }

    private fun loadProperties() {
        val inStream = this.javaClass.getResourceAsStream("/version.properties")
        val readBytes = inStream.readBytes()
        configContent = ByteArrayInputStream(readBytes).bufferedReader().readText()
        if (null == configContent) {
            configContent = ""
        }
        val props = Properties()
        val inputStreamReader = InputStreamReader(ByteArrayInputStream(readBytes), "UTF-8")
        props.load(inputStreamReader)
        configProperties = props
    }

    companion object {
        var configContent: String? = null
        var configProperties: Properties? = null
    }
}
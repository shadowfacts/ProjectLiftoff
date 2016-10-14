package net.shadowfacts.projectliftoff

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.File

/**
 * @author shadowfacts
 */
object ModConfig {

	var config: Config = ConfigFactory.load("assets/projectliftoff/reference.conf")
		private set

	fun init(configDir: File) {
		config = ConfigFactory.parseFile(File(configDir, "shadowfacts/ProjectLiftoff.conf")).withFallback(config)
	}

}
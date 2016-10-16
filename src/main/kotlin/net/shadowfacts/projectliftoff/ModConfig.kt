package net.shadowfacts.projectliftoff

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import java.io.File

/**
 * @author shadowfacts
 */
object ModConfig {

	var config: Config = ConfigFactory.load("assets/projectliftoff/reference.conf")
		private set

	fun init(configDir: File) {
		val f = File(configDir, "shadowfacts/ProjectLiftoff.conf")
		if (!f.exists()) {
			f.createNewFile()
		}

		config = ConfigFactory.parseFile(f).withFallback(config)

		val toRender = config.root().withOnlyKey("projectliftoff")
		val s = toRender.render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false))
		f.writeText(s)
	}

}
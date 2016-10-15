package net.shadowfacts.projectliftoff.block

import net.shadowfacts.shadowmc.block.ModBlocks

/**
 * @author shadowfacts
 */
object ModBlocks : ModBlocks() {

	val asteroid = BlockBase("asteroid")

	override fun init() {
		register(asteroid)
	}

}
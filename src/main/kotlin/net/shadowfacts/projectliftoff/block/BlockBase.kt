package net.shadowfacts.projectliftoff.block

import net.minecraft.block.material.Material
import net.shadowfacts.shadowmc.block.BlockBase

/**
 * Because the ShadowMC one doesn't use a namespaced unlocalized name
 *
 * @author shadowfacts
 */
open class BlockBase(name: String, material: Material) : BlockBase(material, name) {

	constructor(name: String): this(name, Material.ROCK)

	init {
		unlocalizedName = registryName.toString()
	}

}
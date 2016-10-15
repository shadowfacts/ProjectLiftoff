package net.shadowfacts.projectliftoff.block

import net.minecraft.block.Block
import net.shadowfacts.shadowmc.block.ModBlocks

/**
 * @author shadowfacts
 */
object ModBlocks : ModBlocks() {

//	Ores
	val oreCopper = BlockOre("oreCopper")
	val oreAluminum = BlockOre("oreAluminum")

	val asteroid = BlockBase("asteroid")

	override fun init() {
		javaClass.declaredFields.filter {
			Block::class.java.isAssignableFrom(it.type)
		}.forEach {
			register(it.get(this) as Block)
		}
	}

}
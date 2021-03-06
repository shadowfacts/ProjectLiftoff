package net.shadowfacts.projectliftoff.block

import net.minecraft.block.Block
import net.shadowfacts.projectliftoff.block.tank.BlockTank
import net.shadowfacts.projectliftoff.block.tank.ItemBlockTank
import net.shadowfacts.projectliftoff.util.NoRegister
import net.shadowfacts.shadowmc.block.ModBlocks

/**
 * @author shadowfacts
 */
object ModBlocks : ModBlocks() {

//	Ores
	val oreCopper = BlockOre("oreCopper")
	val oreAluminum = BlockOre("oreAluminum")

	val asteroid = BlockBase("asteroid")

	@NoRegister val tank =  BlockTank()

	override fun init() {
		javaClass.declaredFields.filter {
			Block::class.java.isAssignableFrom(it.type) && !it.isAnnotationPresent(NoRegister::class.java)
		}.forEach {
			register(it.get(this) as Block)
		}

		register(tank, ItemBlockTank)
	}

}
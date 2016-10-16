package net.shadowfacts.projectliftoff.gui

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.shadowfacts.projectliftoff.block.tank.ContainerTank
import net.shadowfacts.projectliftoff.block.tank.GUITank

/**
 * @author shadowfacts
 */
object GUIHandler : IGuiHandler {

	val TANK = 0

	override fun getClientGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
		val pos = BlockPos(x, y, z)
		return when (id) {
			TANK -> GUITank.create(pos, player.inventory, getTileEntity(world, pos))
			else -> null
		}
	}

	override fun getServerGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
		val pos = BlockPos(x, y, z)
		return when (id) {
			TANK -> ContainerTank(pos, player.inventory, getTileEntity(world, pos))
			else -> null
		}
	}

	private fun <T : TileEntity> getTileEntity(world: World, pos: BlockPos): T {
		@Suppress("UNCHECKED_CAST")
		return world.getTileEntity(pos) as T
	}

}
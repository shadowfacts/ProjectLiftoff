package net.shadowfacts.projectliftoff.block.tank

import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.shadowfacts.projectliftoff.block.ModBlocks
import net.shadowfacts.shadowmc.capability.CapHolder
import net.shadowfacts.shadowmc.oxygen.OxygenCaps
import net.shadowfacts.shadowmc.oxygen.OxygenHandler
import net.shadowfacts.shadowmc.oxygen.OxygenProvider
import net.shadowfacts.shadowmc.oxygen.OxygenReceiver
import net.shadowfacts.shadowmc.oxygen.impl.OxygenTank
import net.shadowfacts.shadowmc.tileentity.BaseTileEntity

/**
 * @author shadowfacts
 */
class TileEntityTank : BaseTileEntity(), ITickable {

	@CapHolder(capabilities = arrayOf(OxygenHandler::class, OxygenProvider::class, OxygenReceiver::class))
	private val oxygen = OxygenTank(20000f, 20f) { oxygenChanged(it) }
	@CapHolder(capabilities = arrayOf(IItemHandler::class))
	private val inventory = ItemStackHandler(2)

	private var level: Int = 0

	internal fun loadOxygen(oxygen: OxygenHandler) {
		this.oxygen.load(oxygen)
	}

	private fun calcLevel(): Int {
		return (oxygen.stored / oxygen.capacity * 10).toInt()
	}

	private fun oxygenChanged(tank: OxygenTank) {
		markDirty()
		val newLevel = calcLevel()
		if (level != newLevel) {
			level = newLevel
			world.setBlockState(pos, ModBlocks.tank.defaultState.withProperty(BlockTank.LEVEL, level))
		}
	}

	override fun update() {
		if (!world.isRemote) {
			if (canReceiveOxygenFromItem()) {
				receiveOxygenFromItem()
			}

			if (canSendOxygenToItem()) {
				sendOxygenToItem()
			}
		}
	}

	override fun shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newState: IBlockState): Boolean {
		return oldState.block != newState.block
	}

	private fun canReceiveOxygenFromItem(): Boolean {
		val stack = inventory.getStackInSlot(0)
		return stack != null && stack.hasCapability(OxygenCaps.PROVIDER, null)
	}

	private fun receiveOxygenFromItem() {
		val stack = inventory.getStackInSlot(0)
		val provider = stack.getCapability(OxygenCaps.PROVIDER, null)
		if (provider.stored > 0) {
			oxygen.receive(provider.extract(oxygen.receive(provider.stored, true), false), false)
		}
	}

	private fun canSendOxygenToItem(): Boolean {
		val stack = inventory.getStackInSlot(1)
		return stack != null && stack.hasCapability(OxygenCaps.RECEIVER, null)
	}

	private fun sendOxygenToItem() {
		val stack = inventory.getStackInSlot(1)
		val receiver = stack.getCapability(OxygenCaps.RECEIVER, null)
		if (receiver.stored < receiver.capacity) {
			oxygen.extract(receiver.receive(oxygen.extract(oxygen.stored, true), false), false)
			oxygenChanged(oxygen)
		}
	}

	override fun writeToNBT(tag: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(tag)
		tag.setTag("Oxygen", oxygen.serializeNBT())
		tag.setTag("Inventory", inventory.serializeNBT())

		return tag
	}

	override fun readFromNBT(tag: NBTTagCompound) {
		super.readFromNBT(tag)
		oxygen.deserializeNBT(tag.getCompoundTag("Oxygen"))
		inventory.deserializeNBT(tag.getCompoundTag("Inventory"))

		level = calcLevel()
	}

}
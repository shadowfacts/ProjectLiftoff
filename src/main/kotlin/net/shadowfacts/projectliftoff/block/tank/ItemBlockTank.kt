package net.shadowfacts.projectliftoff.block.tank

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.shadowfacts.projectliftoff.block.ModBlocks
import net.shadowfacts.shadowmc.oxygen.OxygenCaps
import net.shadowfacts.shadowmc.oxygen.impl.OxygenHandlerImpl
import net.shadowfacts.shadowmc.oxygen.impl.OxygenTankProvider

/**
 * @author shadowfacts
 */
object ItemBlockTank : ItemBlock(ModBlocks.tank) {

	init {
		registryName = block.registryName
		hasSubtypes = true
	}

	override fun getSubItems(item: Item, tab: CreativeTabs?, subItems: MutableList<ItemStack>) {
		subItems.add(ItemStack(this))
		val stack2 = ItemStack(this)
		(stack2.getCapability(OxygenCaps.HANDLER, null) as OxygenHandlerImpl).stored = 20000f
		subItems.add(stack2)
	}

	override fun addInformation(stack: ItemStack, player: EntityPlayer, tooltip: MutableList<String>, advanced: Boolean) {
		val handler = stack.getCapability(OxygenCaps.HANDLER, null)
		tooltip.add(String.format("Oxygen: %.0f / %.0f", handler.stored, handler.capacity))
	}

	override fun initCapabilities(stack: ItemStack?, nbt: NBTTagCompound?): ICapabilityProvider {
		return OxygenTankProvider(20000f, 20f)
	}

}
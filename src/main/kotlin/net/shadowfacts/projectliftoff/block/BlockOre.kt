package net.shadowfacts.projectliftoff.block

import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.IStringSerializable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.oredict.OreDictionary
import net.shadowfacts.projectliftoff.MOD_ID
import net.shadowfacts.shadowmc.block.SubtypeBlock
import net.shadowfacts.shadowmc.item.OreDictItem

/**
 * @author shadowfacts
 */
class BlockOre(name: String) : BlockBase(name, Material.ROCK), OreDictItem, SubtypeBlock {

	companion object {

		val VARIANT: PropertyEnum<OreVariant> = PropertyEnum.create("variant", OreVariant::class.java)
		enum class OreVariant : IStringSerializable {

			OVERWORLD,
			NETHER,
			END,
			ASTEROID;
			override fun getName(): String = name.toLowerCase()

		}
	}

	init {
		defaultState = defaultState.withProperty(VARIANT, OreVariant.OVERWORLD)

	}

	override fun initItemModel() {
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this)) {
			ModelResourceLocation("$MOD_ID:$name", "variant=${OreVariant.values()[it.metadata].name.toLowerCase()}")
		}
	}

	override fun registerOreDict() {
		OreDictionary.registerOre(name, this)
	}

	override fun getSubBlocks(item: Item, tab: CreativeTabs?, list: MutableList<ItemStack>) {
		OreVariant.values().forEach {
			val stack = ItemStack(this)
			stack.itemDamage = it.ordinal
			list.add(stack)
		}
	}

	override fun createBlockState(): BlockStateContainer {
		return BlockStateContainer(this, VARIANT)
	}

	override fun getMetaFromState(state: IBlockState): Int {
		return state.getValue(VARIANT).ordinal
	}

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState {
		return defaultState.withProperty(VARIANT, OreVariant.values()[meta])
	}

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, stack: ItemStack): IBlockState {
		return defaultState.withProperty(VARIANT, OreVariant.values()[stack.itemDamage])
	}

}

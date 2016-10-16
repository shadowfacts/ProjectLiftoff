package net.shadowfacts.projectliftoff.block.tank

import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.shadowfacts.projectliftoff.MOD_ID
import net.shadowfacts.projectliftoff.ProjectLiftoff
import net.shadowfacts.projectliftoff.gui.GUIHandler
import net.shadowfacts.shadowmc.block.BlockTE
import net.shadowfacts.shadowmc.oxygen.OxygenCaps
import net.shadowfacts.shadowmc.oxygen.impl.OxygenHandlerImpl

/**
 * @author shadowfacts
 */
class BlockTank : BlockTE<TileEntityTank>(Material.ROCK, "oxygenTank") {

	companion object {
		val LEVEL: PropertyInteger = PropertyInteger.create("level", 0, 10)

		private val BOX = AxisAlignedBB(4/16.0, 0.0, 4/16.0, 12/16.0, 13/16.0, 12/16.0)
	}

	init {
		unlocalizedName = registryName.toString()
		defaultState = defaultState.withProperty(LEVEL, 0)
	}

	override fun initItemModel() {
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this)) {
			val handler = it.getCapability(OxygenCaps.HANDLER, null)
			val level = (handler.stored / handler.capacity * 10).toInt()
			ModelResourceLocation("$MOD_ID:oxygenTank", "level=$level")
		}
	}

	@Deprecated("")
	override fun getBoundingBox(state: IBlockState?, source: IBlockAccess?, pos: BlockPos?): AxisAlignedBB = BOX

	@Deprecated("")
	override fun getCollisionBoundingBox(blockState: IBlockState?, worldIn: World?, pos: BlockPos?): AxisAlignedBB? = BOX

	@Deprecated("")
	override fun getSelectedBoundingBox(state: IBlockState?, worldIn: World?, pos: BlockPos?): AxisAlignedBB = BOX

	override fun isSideSolid(base_state: IBlockState?, world: IBlockAccess?, pos: BlockPos?, side: EnumFacing?): Boolean = false

	override fun isOpaqueCube(state: IBlockState?): Boolean = false

	override fun isFullCube(state: IBlockState?): Boolean = false

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, stack: ItemStack): IBlockState {
		val handler = stack.getCapability(OxygenCaps.HANDLER, null)
		val level = (handler.stored / handler.capacity * 10).toInt()
		return defaultState.withProperty(LEVEL, level)
	}

	override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
		val tank = getTileEntity(world, pos)
		tank.loadOxygen(stack.getCapability(OxygenCaps.HANDLER, null))
	}

	override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, heldItem: ItemStack?, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		player.openGui(ProjectLiftoff, GUIHandler.TANK, world, pos.x, pos.y, pos.z)
		return true
	}

	@Deprecated("")
	override fun createBlockState(): BlockStateContainer {
		return BlockStateContainer(this, LEVEL)
	}

	override fun getMetaFromState(state: IBlockState): Int {
		return state.getValue(LEVEL)
	}

	@Deprecated("")
	override fun getStateFromMeta(meta: Int): IBlockState {
		return defaultState.withProperty(LEVEL, meta)
	}

	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val stack = ItemStack(this)
		(stack.getCapability(OxygenCaps.HANDLER, null) as OxygenHandlerImpl).load(getTileEntity(world, pos).getCapability(OxygenCaps.HANDLER, EnumFacing.NORTH))

		val item = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
		world.spawnEntityInWorld(item)

		super.breakBlock(world, pos, state)
	}

	override fun getDrops(world: IBlockAccess?, pos: BlockPos?, state: IBlockState?, fortune: Int): MutableList<ItemStack> {
		return mutableListOf()
	}

	override fun createTileEntity(): TileEntityTank {
		return TileEntityTank()
	}

	override fun getTileEntityClass(): Class<TileEntityTank> {
		return TileEntityTank::class.java
	}

}
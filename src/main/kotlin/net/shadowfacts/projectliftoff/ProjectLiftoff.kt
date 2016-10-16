package net.shadowfacts.projectliftoff

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.shadowfacts.projectliftoff.block.ModBlocks
import net.shadowfacts.projectliftoff.event.ClientEventHandler
import net.shadowfacts.projectliftoff.gui.GUIHandler
import net.shadowfacts.projectliftoff.item.ModItems

/**
 * @author shadowfacts
 */
@Mod(modid = MOD_ID, name = NAME, version = VERSION, dependencies = "required-after:tesla;required-after:shadowmc@[3.4.8,);", acceptedMinecraftVersions = "[1.10.2]",  modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object ProjectLiftoff {

	val blocks = ModBlocks
	val items = ModItems
	val config = ModConfig

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		config.init(event.modConfigurationDirectory)

		blocks.init()
		items.init()

		NetworkRegistry.INSTANCE.registerGuiHandler(this, GUIHandler)
	}

	@Mod.EventHandler
	@SideOnly(Side.CLIENT)
	fun preInitClient(event: FMLPreInitializationEvent) {
		MinecraftForge.EVENT_BUS.register(ClientEventHandler)
	}

	@Mod.EventHandler
	fun init(event: FMLInitializationEvent) {

	}

	@Mod.EventHandler
	fun postInit(event: FMLPostInitializationEvent) {

	}

}
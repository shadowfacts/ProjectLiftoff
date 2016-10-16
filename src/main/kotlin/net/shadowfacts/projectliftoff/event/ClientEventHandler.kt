package net.shadowfacts.projectliftoff.event

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraft.world.DimensionType
import net.minecraftforge.client.GuiIngameForge
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.shadowfacts.projectliftoff.MOD_ID
import net.shadowfacts.projectliftoff.api.ProjectLiftoffAPI
import net.shadowfacts.projectliftoff.util.drawTexturedModalRect
import net.shadowfacts.shadowmc.oxygen.OxygenCaps

/**
 * @author shadowfacts
 */
object ClientEventHandler {

	private val OVERLAY_TEX = ResourceLocation(MOD_ID, "textures/gui/hud.png")

	@SubscribeEvent
	fun onRenderAir(event: RenderGameOverlayEvent.Pre) {
		if (event.type != RenderGameOverlayEvent.ElementType.AIR) {
			return
		}

		val player = Minecraft.getMinecraft().thePlayer

		if (ProjectLiftoffAPI.getDimensionInfoRegistry().hasBreathableAir(DimensionType.getById(player.dimension))) {
			val stack = player.inventory.armorItemInSlot(2)
			if (stack != null && stack.hasCapability(OxygenCaps.HANDLER, null)) {
				event.isCanceled = true

				GlStateManager.enableBlend()
				val res = ScaledResolution(Minecraft.getMinecraft())
				val left = res.scaledWidth / 2 + 91
				val top = res.scaledHeight - GuiIngameForge.right_height

				val handler = stack.getCapability(OxygenCaps.HANDLER, null)
				val full = MathHelper.ceiling_double_int((handler.stored - 2) * 10.0 / handler.capacity)
				val partial = MathHelper.ceiling_double_int(handler.stored * 10.0 / handler.capacity) - full

				for (i in 0..full + partial - 1) {
					Minecraft.getMinecraft().textureManager.bindTexture(OVERLAY_TEX)
					drawTexturedModalRect(left - i * 8 - 9, top, 0.0, if (i < full) 0 else 9, 0, 9, 9)
				}

				GuiIngameForge.right_height += 10

				GlStateManager.disableBlend()
			}
		}
	}

}
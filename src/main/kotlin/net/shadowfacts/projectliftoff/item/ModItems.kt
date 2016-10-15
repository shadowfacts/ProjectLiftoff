package net.shadowfacts.projectliftoff.item

import net.minecraft.item.Item
import net.shadowfacts.shadowmc.item.ModItems

/**
 * @author shadowfacts
 */
object ModItems : ModItems() {

	override fun init() {
		javaClass.declaredFields.filter {
			Item::class.java.isAssignableFrom(it.type)
		}.forEach {
			register(it.get(this) as Item)
		}
	}

}
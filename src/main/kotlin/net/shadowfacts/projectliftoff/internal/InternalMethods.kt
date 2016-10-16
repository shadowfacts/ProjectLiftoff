package net.shadowfacts.projectliftoff.internal

import net.shadowfacts.projectliftoff.api.dimension.DimensionInfoRegistry
import net.shadowfacts.projectliftoff.api.internal.InternalMethods
import net.shadowfacts.projectliftoff.dimension.DimensionRegistry

/**
 * @author shadowfacts
 */
object InternalMethods : InternalMethods {

	override fun getDimensionInfoRegistry(): DimensionInfoRegistry {
		return DimensionRegistry
	}

}
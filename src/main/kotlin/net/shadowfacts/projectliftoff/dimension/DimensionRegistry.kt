package net.shadowfacts.projectliftoff.dimension

import net.minecraft.world.DimensionType
import net.shadowfacts.projectliftoff.api.dimension.DimensionInfoRegistry

/**
 * @author shadowfacts
 */
object DimensionRegistry : DimensionInfoRegistry {

	private val info = mutableMapOf<DimensionType, DimensionInfo>()

	override fun hasBreathableAir(dimension: DimensionType): Boolean {
		return if (info.contains(dimension)) info[dimension]!!.breathableAir else super.hasBreathableAir(dimension)
	}

	override fun getSurfaceTemperature(dimension: DimensionType): Float {
		return if (info.contains(dimension)) info[dimension]!!.surfaceTemperature else super.getSurfaceTemperature(dimension)
	}

	override fun setHasBreathableAir(dimension: DimensionType, breathableAir: Boolean) {
		if (!info.contains(dimension)) {
			info[dimension] = DimensionInfo(breathableAir = breathableAir)
		} else {
			info[dimension]!!.breathableAir = breathableAir
		}
	}

	override fun setSurfaceTemperature(dimension: DimensionType, temperature: Float) {
		if (!info.contains(dimension)) {
			info[dimension] = DimensionInfo(surfaceTemperature = temperature)
		} else {
			info[dimension]!!.surfaceTemperature = temperature
		}
	}

	fun initDefaults() {
		info[DimensionType.OVERWORLD]	= DimensionInfo(breathableAir = true,	surfaceTemperature = 288.15f	)
		info[DimensionType.NETHER]		= DimensionInfo(breathableAir = true,	surfaceTemperature = 315f		)
		info[DimensionType.THE_END]		= DimensionInfo(breathableAir = true,	surfaceTemperature = 250f		)
	}

}

data class DimensionInfo(var breathableAir: Boolean = true,
						 var surfaceTemperature: Float = 288.15f)

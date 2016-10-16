package net.shadowfacts.projectliftoff.api.dimension;

import net.minecraft.world.DimensionType;

import javax.annotation.Nonnull;

/**
 * Registry for holding information about different dimensions
 *
 * @author shadowfacts
 */
public interface DimensionInfoRegistry {

	DimensionInfoRegistry NO_OP = new DimensionInfoRegistry() {};

	/**
	 * Checks if the dimension has player-breathable air
	 * @param dimension The dimension
	 * @return If the dimension has player-breathable air
	 */
	default boolean hasBreathableAir(@Nonnull DimensionType dimension) {
		return false;
	}

	/**
	 * Set if the dimension has player-breathable air
	 * @param dimension The dimension
	 * @param breathableAir If the dimension has player-breathable air
	 */
	default void setHasBreathableAir(@Nonnull DimensionType dimension, boolean breathableAir) {}

	/**
	 * Gets the average surface temperature of the dimension
	 * @param dimension The dimension
	 * @return The temperature in degrees Kelvin
	 */
	default float getSurfaceTemperature(@Nonnull DimensionType dimension) {
		return 288.15f;
	}

	/**
	 * Set the average surface temperature of the dimension
	 * @param dimension The dimension
	 * @param temperature The temperature in degrees Kelvin
	 */
	default void setSurfaceTemperature(@Nonnull DimensionType dimension, float temperature) {}

}

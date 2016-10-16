package net.shadowfacts.projectliftoff.api.internal;

import net.shadowfacts.projectliftoff.api.dimension.DimensionInfoRegistry;

/**
 * @author shadowfacts
 */
public interface InternalMethods {

	InternalMethods NO_OP = new InternalMethods() {};

	default DimensionInfoRegistry getDimensionInfoRegistry() {
		return DimensionInfoRegistry.NO_OP;
	}

}

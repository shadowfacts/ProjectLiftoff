package net.shadowfacts.projectliftoff.api;

import net.shadowfacts.projectliftoff.api.dimension.DimensionInfoRegistry;
import net.shadowfacts.projectliftoff.api.internal.InternalMethods;

/**
 * @author shadowfacts
 */
public class ProjectLiftoffAPI {

	public static InternalMethods internalMethods = InternalMethods.NO_OP;

	/**
	 * @return The dimension info registry that is used to store information about dimensions
	 */
	public static DimensionInfoRegistry getDimensionInfoRegistry() {
		return internalMethods.getDimensionInfoRegistry();
	}

}

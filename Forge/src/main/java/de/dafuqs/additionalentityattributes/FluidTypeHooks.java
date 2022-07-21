package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.fluids.FluidType;

public class FluidTypeHooks {
	/**
	 * Injected in {@link IForgeLivingEntity#jumpInFluid(FluidType)}
	 */
	public static double jumpHook(LivingEntity living, FluidType type, double original) {
		if (ForgeMod.WATER_TYPE.get() == type)
			return AttributeUtils.getAttribute(living, AdditionalEntityAttributes.WATER_SPEED, original);
		else
			return original;
	}

	/**
	 * Injected in {@link IForgeLivingEntity#sinkInFluid(FluidType)}
	 */
	public static double sinkHook(LivingEntity living, FluidType type, double original) {
		if (ForgeMod.WATER_TYPE.get() == type)
			return -AttributeUtils.getAttribute(living, AdditionalEntityAttributes.WATER_SPEED, -original);
		else
			return original;
	}
}

package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.additionalentityattributes.AttributeUtils;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LivingEntity.class)
public abstract class WaterSpeedMixin {

	@ModifyConstant(method = "jumpInLiquid", constant = @Constant(doubleValue = 0.03999999910593033D))
	public double modifyUpwardSwimming(double original, TagKey<Fluid> fluid) {
		if (FluidTags.WATER == fluid)
			return AttributeUtils.getAttribute((LivingEntity) (Object) this, AdditionalEntityAttributes.WATER_SPEED, original);
		else
			return original;
	}

	@ModifyConstant(method = "goDownInWater", constant = @Constant(doubleValue = -0.03999999910593033D))
	public double knockDownwards(double original) {
		return -AttributeUtils.getAttribute((LivingEntity) (Object) this, AdditionalEntityAttributes.WATER_SPEED, -original);
	}
}
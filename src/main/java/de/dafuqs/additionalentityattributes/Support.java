package de.dafuqs.additionalentityattributes;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.math.*;

public class Support {

	private static final double MIN_SCALE = 0.0625;
	private static final double MAX_SCALE = 16.0;
	
	public static float getExperienceMod(LivingEntity entity) {
		if (entity == null) {
			return 1.0F;
		}
		EntityAttributeInstance attributeInstance = entity.getAttributeInstance(AdditionalEntityAttributes.DROPPED_EXPERIENCE);
		if (attributeInstance == null) {
			return 1.0F;
		}
		return (float) attributeInstance.getValue();
	}

	public static double getHitboxWidth(LivingEntity entity, double original) {
		return MathHelper.clamp(original * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.WIDTH) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.HITBOX_SCALE) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.HITBOX_WIDTH), MIN_SCALE, MAX_SCALE);
	}

	public static double getModelWidth(LivingEntity entity, double original) {
		return MathHelper.clamp(original * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.WIDTH) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.MODEL_SCALE) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.MODEL_WIDTH), MIN_SCALE, MAX_SCALE);
	}

	public static double getHitboxHeight(LivingEntity entity, double original) {
		return MathHelper.clamp(original * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.HEIGHT) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.HITBOX_SCALE) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.HITBOX_HEIGHT), MIN_SCALE, MAX_SCALE);
	}

	public static double getModelHeight(LivingEntity entity, double original) {
		return MathHelper.clamp(original * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.HEIGHT) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.MODEL_SCALE) * getScaleAttributeModifierValue(entity, AdditionalEntityAttributes.MODEL_HEIGHT), MIN_SCALE, MAX_SCALE);
	}

	public static double getScaleAttributeModifierValue(LivingEntity entity, RegistryEntry<EntityAttribute> attribute) {
		double value = 1.0F;

		if (entity == null) {
			return value;
		}

		EntityAttributeInstance instance = entity.getAttributeInstance(attribute);
		if (instance != null) {
			value = instance.getValue();
		}

		return MathHelper.clamp(value, MIN_SCALE, MAX_SCALE);
	}

	public static double getMobDetectionValue(LivingEntity entity, double original) {
		if (entity == null) {
			return original;
		}

		EntityAttributeInstance instance = entity.getAttributeInstance(AdditionalEntityAttributes.MOB_DETECTION_RANGE);
		if (instance != null) {
			for (EntityAttributeModifier modifier : instance.getModifiers()) {
				float amount = (float) modifier.value();

				if (modifier.operation() == EntityAttributeModifier.Operation.ADD_VALUE)
					original += amount;
				else
					original *= (amount + 1);
			}
		}

		return original;
	}

}

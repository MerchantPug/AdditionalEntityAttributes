package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class AttributeUtils {
	public static double getAttribute(LivingEntity living, Attribute attribute, double original) {
		AttributeInstance instance = living.getAttribute(attribute);
		if (instance == null) {
			return original;
		} else {
			if (instance.getBaseValue() != original)
				instance.setBaseValue(original);
			return instance.getValue();
		}

	}
}

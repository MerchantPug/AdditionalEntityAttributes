package de.dafuqs.additionalentityattributes.mixin.client;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.additionalentityattributes.*;
import net.minecraft.client.render.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
	
	@ModifyExpressionValue(method = "applyFog", at = @At(value = "CONSTANT", args = "floatValue=0.25F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMinWithoutFireResistance(float original, Camera camera) {
		EntityAttributeInstance lavaVisibilityAttribute = camera.getFocusedEntity() instanceof LivingEntity living ? living.getAttributeInstance(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return original - (float) lavaVisibilityAttribute.getValue() * 0.25F;
		}
	}
	
	@ModifyExpressionValue(method = "applyFog", at = @At(value = "CONSTANT", args = "floatValue=1.0F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMaxWithoutFireResistance(float original, Camera camera) {
		EntityAttributeInstance lavaVisibilityAttribute = camera.getFocusedEntity() instanceof LivingEntity living ? living.getAttributeInstance(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return (float) lavaVisibilityAttribute.getValue();
		}
	}
	
	@ModifyExpressionValue(method = "applyFog", at = @At(value = "CONSTANT", args = "floatValue=0.0F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMinFireResistance(float original, Camera camera) {
		EntityAttributeInstance lavaVisibilityAttribute = camera.getFocusedEntity() instanceof LivingEntity living ? living.getAttributeInstance(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return original - (float) lavaVisibilityAttribute.getValue();
		}
	}

	@ModifyExpressionValue(method = "applyFog", at = @At(value = "CONSTANT", args = "floatValue=5.0F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMaxWithFireResistance(float original, Camera camera) {
		EntityAttributeInstance lavaVisibilityAttribute = camera.getFocusedEntity() instanceof LivingEntity living ? living.getAttributeInstance(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return (float) lavaVisibilityAttribute.getValue();
		}
	}
	
	@ModifyExpressionValue(method = "applyFog", at = @At(value = "CONSTANT", args = "floatValue=96F", ordinal = 0))
	private static float additionalEntityAttributes$modifyWaterVisibility(float original, Camera camera) {
		EntityAttributeInstance waterVisibilityAttribute = camera.getFocusedEntity() instanceof LivingEntity living ? living.getAttributeInstance(AdditionalEntityAttributes.WATER_VISIBILITY) : null;
		if (waterVisibilityAttribute == null) {
			return original;
		} else {
			if (waterVisibilityAttribute.getBaseValue() != original) {
				waterVisibilityAttribute.setBaseValue(original);
			}
			return (float) waterVisibilityAttribute.getValue();
		}
	}
	
}
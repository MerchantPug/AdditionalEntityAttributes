package de.dafuqs.additionalentityattributes.mixin.client;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.additionalentityattributes.AttributeUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FogRenderer.class)
public abstract class BackgroundRendererMixin {
	@ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 0.25F, ordinal = 0))
	private static float modifyLavaVisibilityMinWithoutFireResistance(float original, Camera camera) {
		AttributeInstance lavaVisibilityAttribute = Minecraft.getInstance().player.getAttribute(AdditionalEntityAttributes.LAVA_VISIBILITY);
		if(lavaVisibilityAttribute == null) {
			return original;
		} else {
			if(lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return original - (float) lavaVisibilityAttribute.getValue() * 0.25F;
		}
	}
	
	@ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 1.0F, ordinal = 0))
	private static float modifyLavaVisibilityMaxWithoutFireResistance(float original, Camera camera) {
		return (float) AttributeUtils.getAttribute(Minecraft.getInstance().player, AdditionalEntityAttributes.LAVA_VISIBILITY, original);
	}
	
	@ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 0.0F, ordinal = 0))
	private static float modifyLavaVisibilityMinFireResistance(float original, Camera camera) {
		return (float) AttributeUtils.getAttribute(Minecraft.getInstance().player, AdditionalEntityAttributes.LAVA_VISIBILITY, original);
	}
	
	@ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 3.0F, ordinal = 0))
	private static float modifyLavaVisibilityMaxWithFireResistance(float original, Camera camera) {
		return (float) AttributeUtils.getAttribute(Minecraft.getInstance().player, AdditionalEntityAttributes.LAVA_VISIBILITY, original);
	}
	
	@ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 96F, ordinal = 0))
	private static float modifyWaterVisibility(float original, Camera camera) {
		return (float) AttributeUtils.getAttribute(Minecraft.getInstance().player, AdditionalEntityAttributes.WATER_VISIBILITY, original);
	}
	
}
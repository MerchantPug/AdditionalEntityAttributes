package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class PlayerEntityMixin {

	/**
	 * By default, the additional crit damage is a 50% bonus
	 */
	@ModifyConstant(method = "attack", constant = @Constant(floatValue = 1.5F))
	public float applyCriticalDamageMultiplierAttribute(float original) {
		AttributeInstance criticalDamageMultiplier = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
		if (criticalDamageMultiplier == null) {
			return original;
		} else {
			return 1 + (float) criticalDamageMultiplier.getValue();
		}
	}

	@ModifyVariable(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectUtil;hasDigSpeed(Lnet/minecraft/world/entity/LivingEntity;)Z"), index = 2)
	private float getBlockBreakingSpeedMixin(float f) {
		AttributeInstance instance = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.DIG_SPEED);

		if (instance != null) {
			for (AttributeModifier modifier : instance.getModifiers()) {
				float amount = (float) modifier.getAmount();

				if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
					f += amount;
				else
					f *= (amount + 1);
			}
		}

		return f;
	}
}
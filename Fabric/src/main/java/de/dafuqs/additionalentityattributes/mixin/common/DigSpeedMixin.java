package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class DigSpeedMixin {

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

package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Player.class)
public class PlayerEntityMixin {
	
	/**
	 * By default, the additional crit damage is a 50% bonus
	 */
	@ModifyConstant(method = "attack", constant = @Constant(floatValue = 1.5F))
	public float applyCriticalDamageMultiplierAttribute(float original) {
		AttributeInstance criticalDamageMultiplier = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
		if(criticalDamageMultiplier == null) {
			return original;
		} else {
			return 1 + (float) criticalDamageMultiplier.getValue();
		}
	}

}
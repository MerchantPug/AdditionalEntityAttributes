package de.dafuqs.additionalentityattributes.mixin.common;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.additionalentityattributes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	@Inject(method = "createPlayerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", require = 1, allow = 1, at = @At("RETURN"))
	private static void additionalEntityAttributes$addPlayerAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		info.getReturnValue().add(AdditionalEntityAttributes.WATER_VISIBILITY);
		info.getReturnValue().add(AdditionalEntityAttributes.LAVA_VISIBILITY);
		info.getReturnValue().add(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
		info.getReturnValue().add(AdditionalEntityAttributes.BONUS_LOOT_COUNT_ROLLS);
		info.getReturnValue().add(AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS);
		info.getReturnValue().add(AdditionalEntityAttributes.DROPPED_EXPERIENCE);
		info.getReturnValue().add(AdditionalEntityAttributes.COLLECTION_RANGE);
		info.getReturnValue().add(AdditionalEntityAttributes.WIDTH);
		info.getReturnValue().add(AdditionalEntityAttributes.HEIGHT);
		info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_SCALE);
		info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_WIDTH);
		info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_HEIGHT);
		info.getReturnValue().add(AdditionalEntityAttributes.MODEL_SCALE);
		info.getReturnValue().add(AdditionalEntityAttributes.MODEL_WIDTH);
		info.getReturnValue().add(AdditionalEntityAttributes.MODEL_HEIGHT);
		info.getReturnValue().add(AdditionalEntityAttributes.MOB_DETECTION_RANGE);
	}
	
	/**
	 * By default, the additional crit damage is a 50% bonus
	 */
	@ModifyExpressionValue(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "CONSTANT", args = "floatValue=1.5F"))
	public float additionalEntityAttributes$applyCriticalBonusDamage(float original) {
		EntityAttributeInstance criticalDamageMultiplier = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
		if (criticalDamageMultiplier == null) {
			return original;
		} else {
			return 1 + (float) criticalDamageMultiplier.getValue();
		}
	}
	
	@ModifyVariable(method = "tickMovement", at = @At("STORE"))
	private Box additionalEntityAttributes$adjustCollectionRange(Box original) {
		PlayerEntity thisPlayer = (PlayerEntity)(Object) this;
		
		EntityAttributeInstance instance = thisPlayer.getAttributeInstance(AdditionalEntityAttributes.COLLECTION_RANGE);
		if (instance != null) {
			double value = instance.getValue();
			
			// if the box is getting too small: returns a box of size 0
			if (original.getLengthX() + value < 0) {
				Vec3d center = original.getCenter();
				return new Box(center.x, center.y, center.z, center.x, center.y, center.z);
			}
			
			return original.expand(value, value / 2, value);
		}
		
		return original;
	}
	
}
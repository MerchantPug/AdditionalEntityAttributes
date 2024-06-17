package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.provider.number.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(RandomChanceLootCondition.class)
public abstract class RandomChanceLootConditionMixin {

	@Shadow
	@Final
	private LootNumberProvider chance;

	@Inject(at = @At("RETURN"), method = "test(Lnet/minecraft/loot/context/LootContext;)Z", cancellable = true)
	public void additionalEntityAttributes$applyBonusLoot(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
		// if the result was to not drop a drop before reroll
		if (!cir.getReturnValue() && lootContext.get(LootContextParameters.ATTACKING_ENTITY) instanceof LivingEntity livingEntity) {
			EntityAttributeInstance attributeInstance = livingEntity.getAttributeInstance(AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS);
			if (attributeInstance != null) {
				cir.setReturnValue(lootContext.getRandom().nextFloat() < this.chance.nextFloat(lootContext) * (float) attributeInstance.getValue());
			}
		}
	}
	
}

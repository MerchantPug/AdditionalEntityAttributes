package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.registry.entry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(RandomChanceWithEnchantedBonusLootCondition.class)
public abstract class RandomChanceWithLootingLootConditionMixin {
	
	@Shadow
	@Final
	private RegistryEntry<Enchantment> enchantment;
	
	@Shadow
	@Final
	private EnchantmentLevelBasedValue enchantedChance;
	
	@Inject(at = @At("RETURN"), method = "test(Lnet/minecraft/loot/context/LootContext;)Z", cancellable = true)
	public void additionalEntityAttributes$applyBonusLoot(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
		// if the result was to not drop a drop before reroll
		if (!cir.getReturnValue() && lootContext.get(LootContextParameters.ATTACKING_ENTITY) instanceof LivingEntity livingEntity) {
			EntityAttributeInstance attributeInstance = livingEntity.getAttributeInstance(AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS);
			if (attributeInstance != null) {
				int level = EnchantmentHelper.getEquipmentLevel(this.enchantment, livingEntity);
				cir.setReturnValue(lootContext.getRandom().nextFloat() < this.enchantedChance.getValue(level) * (float) attributeInstance.getValue());
			}
		}
	}
	
}

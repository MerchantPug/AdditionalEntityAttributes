package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.function.*;
import net.minecraft.registry.entry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ApplyBonusLootFunction.class)
public abstract class ApplyBonusLootFunctionMixin {

	@Shadow
	@Final
	private ApplyBonusLootFunction.Formula formula;

	@Shadow
	@Final
	private RegistryEntry<Enchantment> enchantment;

	@ModifyVariable(method = "process(Lnet/minecraft/item/ItemStack;Lnet/minecraft/loot/context/LootContext;)Lnet/minecraft/item/ItemStack;", at = @At("STORE"), ordinal = 1)
	public int additionalEntityAttributes$applyBonusLoot(int original, ItemStack stack, LootContext context) {
		// the bonus loot of this method gets rerolled x times
		// and the highest result will be returned
		ItemStack itemStack = context.get(LootContextParameters.TOOL);
		Entity entity = context.get(LootContextParameters.THIS_ENTITY);
		if (itemStack != null && entity instanceof LivingEntity livingEntity) {
			int enchantmentLevel = EnchantmentHelper.getLevel(this.enchantment, itemStack);
			if (enchantmentLevel > 0) {
				EntityAttributeInstance attributeInstance = livingEntity.getAttributeInstance(AdditionalEntityAttributes.BONUS_LOOT_COUNT_ROLLS);
				if (attributeInstance != null) {
					int bonusRollCount = (int) attributeInstance.getValue();
					int highestRoll = original;
					for (int i = 0; i < bonusRollCount; i++) {
						int thisRoll = this.formula.getValue(context.getRandom(), stack.getCount(), enchantmentLevel);
						highestRoll = Math.max(highestRoll, thisRoll);
					}
					return highestRoll;
				}
			}
		}
		return original;
	}
	
}

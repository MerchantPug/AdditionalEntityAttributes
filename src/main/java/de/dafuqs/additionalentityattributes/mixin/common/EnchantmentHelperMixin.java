package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.server.world.*;
import org.apache.commons.lang3.mutable.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	
	@Inject(method = "getMobExperience(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;I)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;forEachEnchantment(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/enchantment/EnchantmentHelper$ContextAwareConsumer;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void additionalEntityAttributes$modifyMobExperience(ServerWorld world, Entity attacker, Entity mob, int baseMobExperience, CallbackInfoReturnable<Integer> cir, LivingEntity livingAttacker, MutableFloat mutableFloat) {
		mutableFloat.setValue(mutableFloat.floatValue() * Support.getExperienceMod(livingAttacker));
	}
	
}
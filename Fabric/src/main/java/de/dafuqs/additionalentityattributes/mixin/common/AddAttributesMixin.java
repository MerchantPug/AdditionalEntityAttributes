package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.RegistryService;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class AddAttributesMixin {

	@Inject(method = "createLivingAttributes", require = 1, allow = 1, at = @At("RETURN"))
	private static void addAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> info) {
		for (Attribute attribute : RegistryService.INSTANCE.getAttributes()) {
			info.getReturnValue().add(attribute);
		}
	}
}

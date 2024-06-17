package de.dafuqs.additionalentityattributes.mixin.common;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.additionalentityattributes.*;
import net.fabricmc.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.*;
import net.minecraft.fluid.*;
import net.minecraft.registry.tag.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", require = 1, allow = 1, at = @At("RETURN"))
    private static void additionalEntityAttributes$addAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.getReturnValue().add(AdditionalEntityAttributes.WATER_SPEED);
        info.getReturnValue().add(AdditionalEntityAttributes.LAVA_SPEED);
        info.getReturnValue().add(AdditionalEntityAttributes.LUNG_CAPACITY);
        info.getReturnValue().add(AdditionalEntityAttributes.WIDTH);
        info.getReturnValue().add(AdditionalEntityAttributes.HEIGHT);
        info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_SCALE);
        info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_WIDTH);
        info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_HEIGHT);
        info.getReturnValue().add(AdditionalEntityAttributes.MODEL_SCALE);
        info.getReturnValue().add(AdditionalEntityAttributes.MODEL_WIDTH);
        info.getReturnValue().add(AdditionalEntityAttributes.MODEL_HEIGHT);
        info.getReturnValue().add(AdditionalEntityAttributes.MOB_DETECTION_RANGE);
        info.getReturnValue().add(AdditionalEntityAttributes.MAGIC_PROTECTION);
    }

    @ModifyArg(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V", ordinal = 0))
    public float additionalEntityAttributes$waterSpeed(float original) {
        EntityAttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.WATER_SPEED);
        if (waterSpeed == null) {
            return original;
        } else {
            if (waterSpeed.getBaseValue() != original) {
                waterSpeed.setBaseValue(original);
            }
            return (float) waterSpeed.getValue();
        }
    }

    @ModifyExpressionValue(method = "swimUpward", at = @At(value = "CONSTANT", args = "doubleValue=0.03999999910593033D"))
    public double additionalEntityAttributes$modifyUpwardSwimming(double original, TagKey<Fluid> fluid) {
        if (fluid == FluidTags.WATER) {
            EntityAttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.WATER_SPEED);
            if (waterSpeed == null) {
                return original;
            } else {
                if (waterSpeed.getBaseValue() != original) {
                    waterSpeed.setBaseValue(original);
                }
                return waterSpeed.getValue();
            }
        } else {
            return original;
        }
    }

    @Environment(EnvType.CLIENT)
    @ModifyExpressionValue(method = "knockDownwards", at = @At(value = "CONSTANT", args = "doubleValue=-0.03999999910593033D"))
    public double additionalEntityAttributes$knockDownwards(double original) {
        EntityAttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.WATER_SPEED);
        if (waterSpeed == null) {
            return original;
        } else {
            if (waterSpeed.getBaseValue() != -original) {
                waterSpeed.setBaseValue(-original);
            }
            return -waterSpeed.getValue();
        }
    }

    @ModifyExpressionValue(method = "travel", at = {@At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0), @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1), @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2)})
    private double additionalEntityAttributes$increasedLavaSpeed(double original) {
        EntityAttributeInstance lavaSpeed = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.LAVA_SPEED);
        if (lavaSpeed == null) {
            return original;
        } else {
            if (lavaSpeed.getBaseValue() != original) {
                lavaSpeed.setBaseValue(original);
            }
            return lavaSpeed.getValue();
        }
    }

    @ModifyVariable(method = "modifyAppliedDamage", at = @At(value = "LOAD", ordinal = 4), argsOnly = true)
    private float additionalEntityAttributes$reduceMagicDamage(float damage, DamageSource source) {
        EntityAttributeInstance magicProt = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.MAGIC_PROTECTION);

        if (magicProt == null) {
            return damage;
        }

        if (source.isIn(DamageTypeTags.WITCH_RESISTANT_TO) && magicProt.getValue() > 0) {
            damage = (float) Math.max(damage - magicProt.getValue(), 0);
        }
        return damage;
    }
}
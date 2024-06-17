package de.dafuqs.additionalentityattributes.mixin.client;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.client.render.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

// We run at a lower priority because we clamp values.
// This is so Pehkui and other mods that may touch this may run without limits.
@Mixin(value = Camera.class, priority = 500)
public class CameraMixin {
    
    @Shadow private Entity focusedEntity;
    
    @ModifyVariable(method = "update", at = @At("STORE"), ordinal = 1)
    private float additionalEntityAttributes$updateCameraScaleValue(float scale) {
        if (this.focusedEntity instanceof LivingEntity living) {
            return (float) Support.getModelHeight(living, scale);
        }
        return scale;
    }
    
}

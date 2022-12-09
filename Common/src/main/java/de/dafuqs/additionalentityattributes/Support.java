package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public class Support {
	
	public static float getExperienceMod(Player player) {
		AttributeInstance attributeInstance = player.getAttribute(AdditionalEntityAttributes.DROPPED_EXPERIENCE);
		if (attributeInstance == null) {
			return 1.0F;
		}
		return (float) attributeInstance.getValue();
	}
	
}

package de.dafuqs.additionalentityattributes;

import net.fabricmc.api.ModInitializer;

public class AdditionalEntityAttributesMod implements ModInitializer {
	@Override
	public void onInitialize() {
		AdditionalEntityAttributes.initialize();
	}
}
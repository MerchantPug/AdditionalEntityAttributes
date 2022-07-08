package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(AdditionalEntityAttributes.MOD_ID)
public class AdditionalEntityAttributesMod {
	public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, AdditionalEntityAttributes.MOD_ID);

	private static void registerAttributes(EntityAttributeModificationEvent event) {
		for (Attribute attribute : RegistryService.INSTANCE.getAttributes()) {
			event.getTypes().forEach(type -> event.add(type, attribute));
		}
	}

	public AdditionalEntityAttributesMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ATTRIBUTE.register(bus);
		bus.addListener(AdditionalEntityAttributesMod::registerAttributes);
		AdditionalEntityAttributes.initialize();
	}
}
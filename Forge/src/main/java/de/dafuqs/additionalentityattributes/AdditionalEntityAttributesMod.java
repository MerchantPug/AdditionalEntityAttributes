package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(AdditionalEntityAttributes.MOD_ID)
public class AdditionalEntityAttributesMod {
	public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, AdditionalEntityAttributes.MOD_ID);

	public AdditionalEntityAttributesMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ATTRIBUTE.register(bus);
		bus.addListener((EntityAttributeModificationEvent event) -> RegistryService.INSTANCE.getAttributes().forEach(attribute -> event.getTypes().forEach(type -> event.add(type, attribute))));
		AdditionalEntityAttributes.initialize();
	}
}
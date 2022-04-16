package de.dafuqs.additionalentityattributes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.LinkedList;
import java.util.List;

public class FabricRegistryService implements RegistryService {
	private final List<Attribute> registered = new LinkedList<>();
	public void registerAttribute(String name, Attribute value) {
		Registry.register(Registry.ATTRIBUTE, new ResourceLocation(name), value);
		this.registered.add(value);
	}

	@Override
	public Iterable<Attribute> getAttributes() {
		return this.registered;
	}
}

package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.LinkedList;
import java.util.List;

public class ForgeRegistryService implements RegistryService {
	private final List<Attribute> registered = new LinkedList<>();

	@Override
	public void registerAttribute(String name, Attribute value) {
		AdditionalEntityAttributesMod.ATTRIBUTE.register(name, () -> value);
		this.registered.add(value);
	}

	@Override
	public Iterable<Attribute> getAttributes() {
		return this.registered;
	}
}

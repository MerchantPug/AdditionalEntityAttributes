package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.ServiceLoader;

public interface RegistryService {

	RegistryService INSTANCE = load(RegistryService.class);

	static <T> T load(Class<T> clazz) {
		return ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
	}

	void registerAttribute(String name, Attribute value);

	Iterable<Attribute> getAttributes();
}
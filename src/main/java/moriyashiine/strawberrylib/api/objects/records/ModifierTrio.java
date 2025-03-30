/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.objects.records;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.HashSet;
import java.util.Set;

public record ModifierTrio(RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier,
						   AttributeModifierSlot slot) {
	public static ModifierTrio[] current = null;

	public static AttributeModifiersComponent editModifiers(AttributeModifiersComponent original) {
		Set<ModifierTrio> removed = new HashSet<>();
		AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
		original.modifiers().forEach(entry -> {
			EntityAttributeModifier modifier = entry.modifier();
			for (ModifierTrio trio : current) {
				if (trio.modifier().id().equals(modifier.id())) {
					modifier = trio.modifier();
					removed.add(trio);
					break;
				}
			}
			builder.add(entry.attribute(), modifier, entry.slot());
		});
		for (ModifierTrio trio : current) {
			if (!removed.contains(trio)) {
				builder.add(trio.attribute(), trio.modifier(), trio.slot());
			}
		}
		current = null;
		return builder.build();
	}
}
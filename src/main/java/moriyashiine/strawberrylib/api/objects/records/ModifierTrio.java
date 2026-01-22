/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.objects.records;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.HashSet;
import java.util.Set;

public record ModifierTrio(Holder<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
	public static ModifierTrio[] current = null;

	public static ItemAttributeModifiers editAttributes(ItemAttributeModifiers original) {
		Set<ModifierTrio> removed = new HashSet<>();
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
		original.modifiers().forEach(entry -> {
			AttributeModifier modifier = entry.modifier();
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
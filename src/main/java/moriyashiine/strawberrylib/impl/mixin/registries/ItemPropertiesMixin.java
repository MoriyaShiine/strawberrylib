/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.registries;

import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Item.Properties.class)
public class ItemPropertiesMixin {
	@ModifyVariable(method = "attributes", at = @At("HEAD"), argsOnly = true)
	private ItemAttributeModifiers slib$editAttributes(ItemAttributeModifiers attributes) {
		if (ModifierTrio.current != null) {
			return ModifierTrio.editAttributes(attributes);
		}
		return attributes;
	}
}

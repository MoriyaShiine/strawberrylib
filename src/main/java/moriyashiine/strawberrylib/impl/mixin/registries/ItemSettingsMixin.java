/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.registries;

import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Item.Settings.class)
public class ItemSettingsMixin {
	@ModifyVariable(method = "attributeModifiers", at = @At("HEAD"), argsOnly = true)
	private AttributeModifiersComponent slib$editModifiers(AttributeModifiersComponent original) {
		if (ModifierTrio.current != null) {
			return ModifierTrio.editModifiers(original);
		}
		return original;
	}
}

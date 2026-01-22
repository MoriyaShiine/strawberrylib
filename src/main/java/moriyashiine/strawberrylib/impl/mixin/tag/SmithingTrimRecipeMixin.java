/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.tag;

import moriyashiine.strawberrylib.api.module.tag.SLibItemTags;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTrimRecipe.class)
public class SmithingTrimRecipeMixin {
	@Inject(method = "applyTrim", at = @At("HEAD"), cancellable = true)
	private static void slib$untrimmableArmor(ItemStack baseItem, ItemStack materialItem, Holder<TrimPattern> pattern, CallbackInfoReturnable<ItemStack> cir) {
		if (baseItem.is(SLibItemTags.UNTRIMMABLE_ARMOR)) {
			cir.setReturnValue(ItemStack.EMPTY);
		}
	}
}

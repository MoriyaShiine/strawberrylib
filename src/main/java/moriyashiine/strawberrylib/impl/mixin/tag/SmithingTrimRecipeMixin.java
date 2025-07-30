/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.tag;

import moriyashiine.strawberrylib.api.module.tag.SLibItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.recipe.SmithingTrimRecipe;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTrimRecipe.class)
public class SmithingTrimRecipeMixin {
	@Inject(method = "craft(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	private static void slib$untrimmableArmor(RegistryWrapper.WrapperLookup registries, ItemStack base, ItemStack addition, RegistryEntry<ArmorTrimPattern> pattern, CallbackInfoReturnable<ItemStack> cir) {
		if (base.isIn(SLibItemTags.UNTRIMMABLE_ARMOR)) {
			cir.setReturnValue(ItemStack.EMPTY);
		}
	}
}

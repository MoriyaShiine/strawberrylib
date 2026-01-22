/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.modifystackdamage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.ModifyStackDamageEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	@ModifyReturnValue(method = "modifyDamage", at = @At("RETURN"))
	private static float slib$modifyStackDamage(float original, ServerLevel serverLevel, ItemStack itemStack, Entity victim, DamageSource damageSource) {
		return ModifyStackDamageEvent.getModifiedDamage(original, serverLevel, itemStack, victim, damageSource);
	}
}

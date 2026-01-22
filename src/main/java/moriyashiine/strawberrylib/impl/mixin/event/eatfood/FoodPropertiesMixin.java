/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.eatfood;

import moriyashiine.strawberrylib.api.event.EatFoodEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodProperties.class)
public class FoodPropertiesMixin {
	@Inject(method = "onConsume", at = @At("HEAD"))
	private void slib$eatFood(Level level, LivingEntity user, ItemStack stack, Consumable consumable, CallbackInfo ci) {
		EatFoodEvent.EVENT.invoker().eat(level, user, stack, (FoodProperties) (Object) this);
	}
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.eatfood;

import moriyashiine.strawberrylib.api.event.EatFoodEvent;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodComponent.class)
public class FoodComponentMixin {
	@Inject(method = "onConsume", at = @At("HEAD"))
	private void slib$eatFood(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable, CallbackInfo ci) {
		EatFoodEvent.EVENT.invoker().eat(world, user, stack, (FoodComponent) (Object) this);
	}
}

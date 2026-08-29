package moriyashiine.strawberrylib.impl.mixin.event.food;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.api.event.FoodEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
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
	private void slib$foodEat(Level level, LivingEntity user, ItemStack stack, Consumable consumable, CallbackInfo ci) {
		FoodEvents.EAT.invoker().eat(level, user, stack);
	}

	@WrapOperation(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V"))
	private void slib$foodModify(FoodData instance, FoodProperties foodProperties, Operation<Void> original, Level level, LivingEntity user, ItemStack stack, @Local(name = "player") Player player) {
		int nutrition = FoodEvents.MODIFY_NUTRITION.invoker().modify(foodProperties.nutrition(), level, player, stack);
		float saturation = FoodEvents.MODIFY_SATURATION.invoker().modify(foodProperties.saturation(), level, player, stack);
		original.call(instance, new FoodProperties(nutrition, saturation, foodProperties.canAlwaysEat()));
	}
}

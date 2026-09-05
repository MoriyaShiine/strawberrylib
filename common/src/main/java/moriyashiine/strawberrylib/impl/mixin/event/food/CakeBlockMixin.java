package moriyashiine.strawberrylib.impl.mixin.event.food;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.api.event.FoodEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.CakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CakeBlock.class)
public class CakeBlockMixin {
	@WrapOperation(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"))
	private static void slib$food(FoodData instance, int food, float saturationModifier, Operation<Void> original, @Local(argsOnly = true) Player player) {
		food = FoodEvents.MODIFY_NUTRITION.invoker().modify(food, player.level(), player, Items.CAKE.getDefaultInstance());
		saturationModifier = FoodEvents.MODIFY_SATURATION.invoker().modify(saturationModifier, player.level(), player, Items.CAKE.getDefaultInstance());
		original.call(instance, food, saturationModifier);
	}
}

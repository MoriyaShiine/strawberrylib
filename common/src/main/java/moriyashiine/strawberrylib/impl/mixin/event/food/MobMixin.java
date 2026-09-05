package moriyashiine.strawberrylib.impl.mixin.event.food;

import moriyashiine.strawberrylib.api.event.FoodEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin {
	@SuppressWarnings("ConstantValue")
	@Inject(method = "usePlayerItem", at = @At("HEAD"))
	private void slib$food(Player player, InteractionHand hand, ItemStack itemStack, CallbackInfo ci) {
		if ((Object) this instanceof Animal animal && animal.isFood(itemStack)) {
			FoodEvents.EAT.invoker().eat(animal.level(), animal, itemStack);
		}
	}
}

package moriyashiine.strawberrylib.impl.mixin.event.food;

import moriyashiine.strawberrylib.api.event.FoodEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal {
	protected AbstractHorseMixin(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Inject(method = "handleEating", at = @At("HEAD"))
	private void slib$food(Player player, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
		FoodEvents.EAT.invoker().eat(level(), this, itemStack);
	}
}

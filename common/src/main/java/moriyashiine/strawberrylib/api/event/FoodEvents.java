package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class FoodEvents {
	private FoodEvents() {
	}

	public static final Event<Eat> EAT = EventFactory.createArrayBacked(Eat.class, events -> (level, user, stack) -> {
		for (Eat event : events) {
			event.eat(level, user, stack);
		}
	});

	public static final Event<ModifyNutrition> MODIFY_NUTRITION = EventFactory.createArrayBacked(ModifyNutrition.class, events -> (nutrition, level, user, stack) -> {
		for (ModifyNutrition event : events) {
			nutrition = event.modify(nutrition, level, user, stack);
		}
		return nutrition;
	});

	public static final Event<ModifySaturation> MODIFY_SATURATION = EventFactory.createArrayBacked(ModifySaturation.class, events -> (saturation, level, user, stack) -> {
		for (ModifySaturation event : events) {
			saturation = event.modify(saturation, level, user, stack);
		}
		return saturation;
	});

	@FunctionalInterface
	public interface Eat {
		void eat(Level level, LivingEntity user, ItemStack stack);
	}

	@FunctionalInterface
	public interface ModifyNutrition {
		int modify(int nutrition, Level level, Player user, ItemStack stack);
	}

	@FunctionalInterface
	public interface ModifySaturation {
		float modify(float saturation, Level level, Player user, ItemStack stack);
	}
}

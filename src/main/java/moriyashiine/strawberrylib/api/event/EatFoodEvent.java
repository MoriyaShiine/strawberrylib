/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface EatFoodEvent {
	Event<EatFoodEvent> EVENT = EventFactory.createArrayBacked(EatFoodEvent.class, events -> (level, user, stack, properties) -> {
		for (EatFoodEvent event : events) {
			event.eat(level, user, stack, properties);
		}
	});

	void eat(Level level, LivingEntity user, ItemStack stack, FoodProperties properties);
}

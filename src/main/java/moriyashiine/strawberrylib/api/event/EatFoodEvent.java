/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface EatFoodEvent {
	Event<EatFoodEvent> EVENT = EventFactory.createArrayBacked(EatFoodEvent.class, events -> (world, entity, stack, foodComponent) -> {
		for (EatFoodEvent event : events) {
			event.eat(world, entity, stack, foodComponent);
		}
	});

	void eat(World world, LivingEntity entity, ItemStack stack, FoodComponent foodComponent);
}

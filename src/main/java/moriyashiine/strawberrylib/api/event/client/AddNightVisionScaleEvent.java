/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface AddNightVisionScaleEvent {
	Event<AddNightVisionScaleEvent> EVENT = EventFactory.createArrayBacked(AddNightVisionScaleEvent.class, events -> entity -> {
		float modifier = 0;
		for (AddNightVisionScaleEvent event : events) {
			modifier += event.addScale(entity);
		}
		return modifier;
	});

	float addScale(LivingEntity entity);
}

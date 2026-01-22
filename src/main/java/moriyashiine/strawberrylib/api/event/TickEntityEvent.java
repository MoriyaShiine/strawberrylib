/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface TickEntityEvent {
	Event<TickEntityEvent> EVENT = EventFactory.createArrayBacked(TickEntityEvent.class, events -> (level, entity) -> {
		for (TickEntityEvent event : events) {
			event.tick(level, entity);
		}
	});

	void tick(Level level, Entity entity);
}

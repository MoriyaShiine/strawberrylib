/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

@FunctionalInterface
public interface TickEntityEvent {
	Event<TickEntityEvent> EVENT = EventFactory.createArrayBacked(TickEntityEvent.class, events -> (world, entity) -> {
		for (TickEntityEvent event : events) {
			event.tick(world, entity);
		}
	});

	void tick(ServerWorld world, Entity entity);
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public interface ModifyMovementSpeedEvent {
	Event<ModifyMovementSpeedEvent> EVENT = EventFactory.createArrayBacked(ModifyMovementSpeedEvent.class, events -> (velocity, entity) -> {
		for (ModifyMovementSpeedEvent event : events) {
			velocity = event.modify(velocity, entity);
		}
		return velocity;
	});

	Vec3d modify(Vec3d velocity, LivingEntity entity);
}

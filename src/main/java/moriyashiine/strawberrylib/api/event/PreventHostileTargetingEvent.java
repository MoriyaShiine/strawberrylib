/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface PreventHostileTargetingEvent {
	Event<PreventHostileTargetingEvent> EVENT = EventFactory.createArrayBacked(PreventHostileTargetingEvent.class, events -> (attacker, target) -> {
		for (PreventHostileTargetingEvent event : events) {
			if (event.shouldNotTarget(attacker, target)) {
				return true;
			}
		}
		return false;
	});

	boolean shouldNotTarget(LivingEntity attacker, LivingEntity target);
}

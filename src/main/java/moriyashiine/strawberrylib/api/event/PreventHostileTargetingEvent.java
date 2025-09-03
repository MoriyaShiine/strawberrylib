/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface PreventHostileTargetingEvent {
	Event<PreventHostileTargetingEvent> EVENT = EventFactory.createArrayBacked(PreventHostileTargetingEvent.class, events -> (attacker, target) -> {
		List<PreventHostileTargetingEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(PreventHostileTargetingEvent::getPriority));
		for (PreventHostileTargetingEvent event : sortedEvents) {
			TriState state = event.preventsTargeting(attacker, target);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	default int getPriority() {
		return 1000;
	}

	TriState preventsTargeting(LivingEntity attacker, LivingEntity target);
}

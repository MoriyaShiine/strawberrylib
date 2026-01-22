/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface DisableContextualInfoEvent {
	Event<DisableContextualInfoEvent> EVENT = EventFactory.createArrayBacked(DisableContextualInfoEvent.class, events -> player -> {
		List<DisableContextualInfoEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(DisableContextualInfoEvent::getPriority));
		for (DisableContextualInfoEvent event : sortedEvents) {
			TriState state = event.shouldDisable(player);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	default int getPriority() {
		return 1000;
	}

	TriState shouldDisable(Player player);
}

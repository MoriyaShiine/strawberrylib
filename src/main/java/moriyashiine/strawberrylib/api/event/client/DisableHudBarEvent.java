/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface DisableHudBarEvent {
	Event<DisableHudBarEvent> EVENT = EventFactory.createArrayBacked(DisableHudBarEvent.class, events -> player -> {
		List<DisableHudBarEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(DisableHudBarEvent::getPriority));
		for (DisableHudBarEvent event : sortedEvents) {
			TriState state = event.shouldDisableHudBar(player);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	default int getPriority() {
		return 1000;
	}

	TriState shouldDisableHudBar(PlayerEntity player);
}

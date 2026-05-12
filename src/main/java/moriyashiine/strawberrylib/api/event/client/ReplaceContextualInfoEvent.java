/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface ReplaceContextualInfoEvent {
	Event<ReplaceContextualInfoEvent> EVENT = EventFactory.createArrayBacked(ReplaceContextualInfoEvent.class, events -> player -> {
		List<ReplaceContextualInfoEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(ReplaceContextualInfoEvent::getPriority));
		for (ReplaceContextualInfoEvent event : sortedEvents) {
			ContextualInfo info = event.getInfo(player);
			if (info != null) {
				return info;
			}
		}
		return null;
	});

	default int getPriority() {
		return 1000;
	}

	ContextualInfo getInfo(Player player);

	record ContextualInfo(Identifier backgroundTexture, Identifier progressTexture, double progress) {
	}
}

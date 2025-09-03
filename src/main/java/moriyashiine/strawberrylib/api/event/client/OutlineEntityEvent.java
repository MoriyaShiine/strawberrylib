/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface OutlineEntityEvent {
	Event<OutlineEntityEvent> EVENT = EventFactory.createArrayBacked(OutlineEntityEvent.class, events -> entity -> {
		List<OutlineEntityEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(OutlineEntityEvent::getPriority));
		for (OutlineEntityEvent event : sortedEvents) {
			@Nullable OutlineData data = event.getOutlineData(entity);
			if (data != null) {
				return data;
			}
		}
		return null;
	});

	default int getPriority() {
		return 1000;
	}

	@Nullable OutlineData getOutlineData(Entity entity);

	record OutlineData(TriState state, OptionalInt color) {
	}
}

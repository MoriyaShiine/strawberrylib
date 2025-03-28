/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;

import java.util.Optional;

public interface OutlineEntityEvent {
	Event<HasOutline> HAS_OUTLINE = EventFactory.createArrayBacked(HasOutline.class, events -> entity -> {
		for (HasOutline event : events) {
			TriState state = event.hasOutline(entity);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	Event<OutlineColor> OUTLINE_COLOR = EventFactory.createArrayBacked(OutlineColor.class, events -> entity -> {
		for (OutlineColor event : events) {
			Optional<Integer> color = event.getOutlineColor(entity);
			if (color.isPresent()) {
				return color;
			}
		}
		return Optional.empty();
	});

	interface HasOutline {
		TriState hasOutline(Entity entity);
	}

	interface OutlineColor {
		Optional<Integer> getOutlineColor(Entity entity);
	}
}

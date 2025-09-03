/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;

public interface OutlineEntityEvent {
	Event<OutlineEntityEvent> EVENT = EventFactory.createArrayBacked(OutlineEntityEvent.class, events -> entity -> {
		for (OutlineEntityEvent event : events) {
			OutlineData data = event.getOutlineData(entity);
			if (data.state() != TriState.DEFAULT) {
				return data;
			}
		}
		return OutlineData.EMPTY;
	});

	OutlineData getOutlineData(Entity entity);

	record OutlineData(TriState state, int color) {
		public static OutlineData EMPTY = new OutlineData(TriState.DEFAULT, 0);
	}
}

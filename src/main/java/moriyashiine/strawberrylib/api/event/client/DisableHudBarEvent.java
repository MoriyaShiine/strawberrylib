/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface DisableHudBarEvent {
	Event<DisableHudBarEvent> EVENT = EventFactory.createArrayBacked(DisableHudBarEvent.class, events -> player -> {
		for (DisableHudBarEvent event : events) {
			if (event.shouldDisableHudBar(player)) {
				return true;
			}
		}
		return false;
	});

	boolean shouldDisableHudBar(PlayerEntity player);
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface ModifyCriticalStatusEvent {
	Event<ModifyCriticalStatusEvent> EVENT = EventFactory.createArrayBacked(ModifyCriticalStatusEvent.class, events -> (attacker, target, attackCooldownProgress) -> {
		List<ModifyCriticalStatusEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(ModifyCriticalStatusEvent::getPriority));
		for (ModifyCriticalStatusEvent event : sortedEvents) {
			TriState state = event.isCritical(attacker, target, attackCooldownProgress);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	default int getPriority() {
		return 1000;
	}

	TriState isCritical(PlayerEntity attacker, Entity target, float attackCooldownProgress);
}

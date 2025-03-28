/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface ModifyCriticalStatusEvent {
	Event<ModifyCriticalStatusEvent> EVENT = EventFactory.createArrayBacked(ModifyCriticalStatusEvent.class, events -> (attacker, target, attackCooldownProgress) -> {
		for (ModifyCriticalStatusEvent event : events) {
			TriState state = event.isCritical(attacker, target, attackCooldownProgress);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	TriState isCritical(PlayerEntity attacker, Entity target, float attackCooldownProgress);
}

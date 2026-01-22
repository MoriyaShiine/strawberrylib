/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface PreventFallDamageEvent {
	Event<PreventFallDamageEvent> EVENT = EventFactory.createArrayBacked(PreventFallDamageEvent.class, events -> (level, entity, fallDistance, damageModifier, source) -> {
		List<PreventFallDamageEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(PreventFallDamageEvent::getPriority));
		for (PreventFallDamageEvent event : sortedEvents) {
			TriState state = event.preventsFallDamage(level, entity, fallDistance, damageModifier, source);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	default int getPriority() {
		return 1000;
	}

	TriState preventsFallDamage(Level level, LivingEntity entity, double fallDistance, float damageModifier, DamageSource source);
}

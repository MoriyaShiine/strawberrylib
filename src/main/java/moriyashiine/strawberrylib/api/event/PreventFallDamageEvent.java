/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface PreventFallDamageEvent {
	Event<PreventFallDamageEvent> EVENT = EventFactory.createArrayBacked(PreventFallDamageEvent.class, events -> (world, entity, fallDistance, damagePerDistance, damageSource) -> {
		List<PreventFallDamageEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(PreventFallDamageEvent::getPriority));
		for (PreventFallDamageEvent event : sortedEvents) {
			TriState state = event.preventsFallDamage(world, entity, fallDistance, damagePerDistance, damageSource);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	default int getPriority() {
		return 1000;
	}

	TriState preventsFallDamage(World world, LivingEntity entity, double fallDistance, float damagePerDistance, DamageSource damageSource);
}

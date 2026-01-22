/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface AfterDamageIncludingDeathEvent {
	Event<AfterDamageIncludingDeathEvent> EVENT = EventFactory.createArrayBacked(AfterDamageIncludingDeathEvent.class, events -> (victim, source, dealt, amount, blocked) -> {
		for (AfterDamageIncludingDeathEvent event : events) {
			event.afterDamage(victim, source, dealt, amount, blocked);
		}
	});

	void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked);
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

@FunctionalInterface
public interface AfterDamageIncludingDeathEvent {
	Event<AfterDamageIncludingDeathEvent> EVENT = EventFactory.createArrayBacked(AfterDamageIncludingDeathEvent.class, events -> (entity, source, baseDamageTaken, damageTaken, blocked) -> {
		for (AfterDamageIncludingDeathEvent callback : events) {
			callback.afterDamage(entity, source, baseDamageTaken, damageTaken, blocked);
		}
	});

	void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked);
}

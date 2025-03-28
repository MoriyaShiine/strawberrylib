/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

public interface PreventFallDamageEvent {
	Event<PreventFallDamageEvent> EVENT = EventFactory.createArrayBacked(PreventFallDamageEvent.class, events -> (world, entity, fallDistance, damagePerDistance, damageSource) -> {
		for (PreventFallDamageEvent event : events) {
			if (event.shouldNotTakeFallDamage(world, entity, fallDistance, damagePerDistance, damageSource)) {
				return true;
			}
		}
		return false;
	});

	boolean shouldNotTakeFallDamage(World world, LivingEntity entity, double fallDistance, float damagePerDistance, DamageSource damageSource);
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface ModifyDamageTakenEvent {
	Event<ModifyDamageTakenEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (phase, victim, level, source) -> {
		float modifier = 1;
		for (ModifyDamageTakenEvent event : events) {
			modifier *= event.modify(phase, victim, level, source);
		}
		return modifier;
	});

	Event<ModifyDamageTakenEvent> ADD = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (phase, victim, level, source) -> {
		float modifier = 0;
		for (ModifyDamageTakenEvent event : events) {
			modifier += event.modify(phase, victim, level, source);
		}
		return modifier;
	});

	Event<ModifyDamageTakenEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (phase, victim, level, source) -> {
		float modifier = 1;
		for (ModifyDamageTakenEvent event : events) {
			modifier *= event.modify(phase, victim, level, source);
		}
		return modifier;
	});

	static float getModifiedDamage(Phase phase, float damage, LivingEntity victim, ServerLevel level, DamageSource source) {
		damage *= MULTIPLY_BASE.invoker().modify(phase, victim, level, source);
		damage += ADD.invoker().modify(phase, victim, level, source);
		damage *= MULTIPLY_TOTAL.invoker().modify(phase, victim, level, source);
		return damage;
	}

	float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source);

	enum Phase {
		BASE, FINAL
	}
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;

public interface ModifyDamageTakenEvent {
	Event<ModifyDamageTakenEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (phase, amount, world, source, victim) -> {
		for (ModifyDamageTakenEvent event : events) {
			amount *= event.modify(phase, amount, world, source, victim);
		}
		return amount;
	});

	Event<ModifyDamageTakenEvent> ADD = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (phase, amount, world, source, victim) -> {
		for (ModifyDamageTakenEvent event : events) {
			amount += event.modify(phase, amount, world, source, victim);
		}
		return amount;
	});

	Event<ModifyDamageTakenEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (phase, amount, world, source, victim) -> {
		for (ModifyDamageTakenEvent event : events) {
			amount *= event.modify(phase, amount, world, source, victim);
		}
		return amount;
	});

	static float getModifiedDamage(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
		amount = MULTIPLY_BASE.invoker().modify(phase, amount, world, source, victim);
		amount = ADD.invoker().modify(phase, amount, world, source, victim);
		amount = MULTIPLY_TOTAL.invoker().modify(phase, amount, world, source, victim);
		return amount;
	}

	float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim);

	enum Phase {
		BASE, FINAL
	}
}

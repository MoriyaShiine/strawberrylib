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
	interface Base {
		Event<ModifyDamageTakenEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (amount, world, source, victim) -> {
			for (ModifyDamageTakenEvent event : events) {
				amount *= event.modify(amount, world, source, victim);
			}
			return amount;
		});

		Event<ModifyDamageTakenEvent> ADD = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (amount, world, source, victim) -> {
			for (ModifyDamageTakenEvent event : events) {
				amount += event.modify(amount, world, source, victim);
			}
			return amount;
		});

		Event<ModifyDamageTakenEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (amount, world, source, victim) -> {
			for (ModifyDamageTakenEvent event : events) {
				amount *= event.modify(amount, world, source, victim);
			}
			return amount;
		});

		static float getModifiedDamage(float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
			amount = MULTIPLY_BASE.invoker().modify(amount, world, source, victim);
			amount = ADD.invoker().modify(amount, world, source, victim);
			amount = MULTIPLY_TOTAL.invoker().modify(amount, world, source, victim);
			return amount;
		}
	}

	interface Final {
		Event<ModifyDamageTakenEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (amount, world, source, victim) -> {
			for (ModifyDamageTakenEvent event : events) {
				amount *= event.modify(amount, world, source, victim);
			}
			return amount;
		});

		Event<ModifyDamageTakenEvent> ADD = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (amount, world, source, victim) -> {
			for (ModifyDamageTakenEvent event : events) {
				amount += event.modify(amount, world, source, victim);
			}
			return amount;
		});

		Event<ModifyDamageTakenEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyDamageTakenEvent.class, events -> (amount, world, source, victim) -> {
			for (ModifyDamageTakenEvent event : events) {
				amount *= event.modify(amount, world, source, victim);
			}
			return amount;
		});

		static float getModifiedDamage(float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
			amount = MULTIPLY_BASE.invoker().modify(amount, world, source, victim);
			amount = ADD.invoker().modify(amount, world, source, victim);
			amount = MULTIPLY_TOTAL.invoker().modify(amount, world, source, victim);
			return amount;
		}
	}

	float modify(float amount, ServerWorld world, DamageSource source, LivingEntity victim);
}

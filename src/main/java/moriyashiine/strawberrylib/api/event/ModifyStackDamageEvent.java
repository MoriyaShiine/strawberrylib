/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

@FunctionalInterface
public interface ModifyStackDamageEvent {
	Event<ModifyStackDamageEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyStackDamageEvent.class, events -> (amount, world, stack, target, source) -> {
		for (ModifyStackDamageEvent event : events) {
			amount *= event.modify(amount, world, stack, target, source);
		}
		return amount;
	});

	Event<ModifyStackDamageEvent> ADD = EventFactory.createArrayBacked(ModifyStackDamageEvent.class, events -> (amount, world, stack, target, source) -> {
		for (ModifyStackDamageEvent event : events) {
			amount += event.modify(amount, world, stack, target, source);
		}
		return amount;
	});

	Event<ModifyStackDamageEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyStackDamageEvent.class, events -> (amount, world, stack, target, source) -> {
		for (ModifyStackDamageEvent event : events) {
			amount *= event.modify(amount, world, stack, target, source);
		}
		return amount;
	});

	static float getModifiedDamage(float amount, ServerWorld world, ItemStack stack, Entity target, DamageSource source) {
		amount = MULTIPLY_BASE.invoker().modify(amount, world, stack, target, source);
		amount = ADD.invoker().modify(amount, world, stack, target, source);
		amount = MULTIPLY_TOTAL.invoker().modify(amount, world, stack, target, source);
		return amount;
	}

	float modify(float amount, ServerWorld world, ItemStack stack, Entity target, DamageSource source);
}

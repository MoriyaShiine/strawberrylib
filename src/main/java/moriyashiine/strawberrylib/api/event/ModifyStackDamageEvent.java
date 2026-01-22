/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ModifyStackDamageEvent {
	Event<ModifyStackDamageEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyStackDamageEvent.class, events -> (level, stack, victim, source, damage) -> {
		float modifier = 1;
		for (ModifyStackDamageEvent event : events) {
			modifier *= event.modify(level, stack, victim, source, damage);
		}
		return modifier;
	});

	Event<ModifyStackDamageEvent> ADD = EventFactory.createArrayBacked(ModifyStackDamageEvent.class, events -> (level, stack, victim, source, damage) -> {
		float modifier = 0;
		for (ModifyStackDamageEvent event : events) {
			modifier += event.modify(level, stack, victim, source, damage);
		}
		return modifier;
	});

	Event<ModifyStackDamageEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyStackDamageEvent.class, events -> (level, stack, victim, source, damage) -> {
		float modifier = 1;
		for (ModifyStackDamageEvent event : events) {
			modifier *= event.modify(level, stack, victim, source, damage);
		}
		return modifier;
	});

	static float getModifiedDamage(float damage, ServerLevel level, ItemStack stack, Entity victim, DamageSource source) {
		damage *= MULTIPLY_BASE.invoker().modify(level, stack, victim, source, damage);
		damage += ADD.invoker().modify(level, stack, victim, source, damage);
		damage *= MULTIPLY_TOTAL.invoker().modify(level, stack, victim, source, damage);
		return damage;
	}

	float modify(ServerLevel level, ItemStack stack, Entity victim, DamageSource source, float damage);
}

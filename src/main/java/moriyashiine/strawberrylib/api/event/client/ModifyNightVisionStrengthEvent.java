/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface ModifyNightVisionStrengthEvent {
	Event<ModifyNightVisionStrengthEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyNightVisionStrengthEvent.class, events -> (original, entity) -> {
		for (ModifyNightVisionStrengthEvent event : events) {
			original *= event.modify(original, entity);
		}
		return original;
	});

	Event<ModifyNightVisionStrengthEvent> ADD = EventFactory.createArrayBacked(ModifyNightVisionStrengthEvent.class, events -> (original, entity) -> {
		for (ModifyNightVisionStrengthEvent event : events) {
			original += event.modify(original, entity);
		}
		return original;
	});

	Event<ModifyNightVisionStrengthEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyNightVisionStrengthEvent.class, events -> (original, entity) -> {
		for (ModifyNightVisionStrengthEvent event : events) {
			original *= event.modify(original, entity);
		}
		return original;
	});

	static float getModifiedNightVisionStrength(float original, LivingEntity entity) {
		original = MULTIPLY_BASE.invoker().modify(original, entity);
		original = ADD.invoker().modify(original, entity);
		original = MULTIPLY_TOTAL.invoker().modify(original, entity);
		return original;
	}

	float modify(float strength, LivingEntity entity);
}

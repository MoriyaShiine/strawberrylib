/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class ModifyMovementEvents {
	private ModifyMovementEvents() {
	}

	public static final Event<JumpDelta> JUMP_DELTA = EventFactory.createArrayBacked(JumpDelta.class, events -> (delta, entity) -> {
		List<JumpDelta> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(JumpDelta::getPriority));
		for (JumpDelta event : sortedEvents) {
			delta = event.modify(delta, entity);
		}
		return delta;
	});

	public static final Event<MovementDelta> MOVEMENT_DELTA = EventFactory.createArrayBacked(MovementDelta.class, events -> (delta, entity) -> {
		List<MovementDelta> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(MovementDelta::getPriority));
		for (MovementDelta event : sortedEvents) {
			delta = event.modify(delta, entity);
		}
		return delta;
	});

	@FunctionalInterface
	public interface JumpDelta {
		default int getPriority() {
			return 1000;
		}

		Vec3 modify(Vec3 delta, LivingEntity entity);
	}

	@FunctionalInterface
	public interface MovementDelta {
		default int getPriority() {
			return 1000;
		}

		Vec3 modify(Vec3 delta, LivingEntity entity);
	}
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class ModifyMovementEvents {
	private ModifyMovementEvents() {
	}

	public static final Event<JumpVelocity> JUMP_VELOCITY = EventFactory.createArrayBacked(JumpVelocity.class, events -> (velocity, entity) -> {
		List<JumpVelocity> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(JumpVelocity::getPriority));
		for (JumpVelocity event : sortedEvents) {
			velocity = event.modify(velocity, entity);
		}
		return velocity;
	});

	public static final Event<MovementVelocity> MOVEMENT_VELOCITY = EventFactory.createArrayBacked(MovementVelocity.class, events -> (velocity, entity) -> {
		List<MovementVelocity> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(MovementVelocity::getPriority));
		for (MovementVelocity event : sortedEvents) {
			velocity = event.modify(velocity, entity);
		}
		return velocity;
	});

	@FunctionalInterface
	public interface JumpVelocity {
		default int getPriority() {
			return 1000;
		}

		Vec3d modify(Vec3d velocity, LivingEntity entity);
	}

	@FunctionalInterface
	public interface MovementVelocity {
		default int getPriority() {
			return 1000;
		}

		Vec3d modify(Vec3d velocity, LivingEntity entity);
	}
}

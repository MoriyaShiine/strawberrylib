/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@FunctionalInterface
public interface ModifyBlockBreakingSpeedEvent {
	Event<ModifyBlockBreakingSpeedEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyBlockBreakingSpeedEvent.class, events -> (breakSpeed, player, state, world, pos) -> {
		for (ModifyBlockBreakingSpeedEvent event : events) {
			breakSpeed *= event.modify(breakSpeed, player, state, world, pos);
		}
		return breakSpeed;
	});

	Event<ModifyBlockBreakingSpeedEvent> ADD = EventFactory.createArrayBacked(ModifyBlockBreakingSpeedEvent.class, events -> (breakSpeed, player, state, world, pos) -> {
		for (ModifyBlockBreakingSpeedEvent event : events) {
			breakSpeed += event.modify(breakSpeed, player, state, world, pos);
		}
		return breakSpeed;
	});

	Event<ModifyBlockBreakingSpeedEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyBlockBreakingSpeedEvent.class, events -> (breakSpeed, player, state, world, pos) -> {
		for (ModifyBlockBreakingSpeedEvent event : events) {
			breakSpeed *= event.modify(breakSpeed, player, state, world, pos);
		}
		return breakSpeed;
	});

	static float getModifiedBlockBreakingSpeed(float original, PlayerEntity player, BlockState state, BlockView world, BlockPos pos) {
		original = MULTIPLY_BASE.invoker().modify(original, player, state, world, pos);
		original = ADD.invoker().modify(original, player, state, world, pos);
		original = MULTIPLY_TOTAL.invoker().modify(original, player, state, world, pos);
		return original;
	}

	float modify(float breakSpeed, PlayerEntity player, BlockState state, BlockView world, BlockPos pos);
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface ModifyDestroyProgressEvent {
	Event<ModifyDestroyProgressEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyDestroyProgressEvent.class, events -> (player, state, level, pos) -> {
		float modifier = 1;
		for (ModifyDestroyProgressEvent event : events) {
			modifier *= event.modify(player, state, level, pos);
		}
		return modifier;
	});

	Event<ModifyDestroyProgressEvent> ADD = EventFactory.createArrayBacked(ModifyDestroyProgressEvent.class, events -> (player, state, level, pos) -> {
		float modifier = 0;
		for (ModifyDestroyProgressEvent event : events) {
			modifier += event.modify(player, state, level, pos);
		}
		return modifier;
	});

	Event<ModifyDestroyProgressEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyDestroyProgressEvent.class, events -> (player, state, level, pos) -> {
		float modifier = 1;
		for (ModifyDestroyProgressEvent event : events) {
			modifier *= event.modify(player, state, level, pos);
		}
		return modifier;
	});

	static float getModifiedDestroyProgress(float destroyProgress, Player player, BlockState state, BlockGetter level, BlockPos pos) {
		destroyProgress *= MULTIPLY_BASE.invoker().modify(player, state, level, pos);
		destroyProgress += ADD.invoker().modify(player, state, level, pos);
		destroyProgress *= MULTIPLY_TOTAL.invoker().modify(player, state, level, pos);
		return destroyProgress;
	}

	float modify(Player player, BlockState state, BlockGetter level, BlockPos pos);
}

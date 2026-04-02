/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface ModifyDestroySpeedEvent {
	Event<ModifyDestroySpeedEvent> MULTIPLY_BASE = EventFactory.createArrayBacked(ModifyDestroySpeedEvent.class, events -> (level, player, stack, state, pos) -> {
		float modifier = 1;
		for (ModifyDestroySpeedEvent event : events) {
			modifier *= event.modify(level, player, stack, state, pos);
		}
		return modifier;
	});

	Event<ModifyDestroySpeedEvent> ADD_EFFICIENCY = EventFactory.createArrayBacked(ModifyDestroySpeedEvent.class, events -> (level, player, stack, state, pos) -> {
		float modifier = 0;
		for (ModifyDestroySpeedEvent event : events) {
			modifier += event.modify(level, player, stack, state, pos);
		}
		return modifier;
	});

	Event<ModifyDestroySpeedEvent> MULTIPLY_TOTAL = EventFactory.createArrayBacked(ModifyDestroySpeedEvent.class, events -> (level, player, stack, state, pos) -> {
		float modifier = 1;
		for (ModifyDestroySpeedEvent event : events) {
			modifier *= event.modify(level, player, stack, state, pos);
		}
		return modifier;
	});

	float modify(Player player, ItemStack stack, Level level, BlockState state, @Nullable BlockPos pos);
}

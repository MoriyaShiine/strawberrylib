/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.event;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public class ModelReplacementEvent implements EntitySleepEvents.AllowSleeping {
	@Override
	public Player.@Nullable BedSleepingProblem allowSleep(Player player, BlockPos sleepingPos) {
		if (SLibUtils.getModelReplacement(player) != null) {
			player.sendOverlayMessage(Component.translatable("block.minecraft.bed.alternate_form"));
			return Player.BedSleepingProblem.OTHER_PROBLEM;
		}
		return null;
	}
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.event;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ModelReplacementEvent implements EntitySleepEvents.AllowSleeping {
	@Nullable
	@Override
	public PlayerEntity.SleepFailureReason allowSleep(PlayerEntity player, BlockPos sleepingPos) {
		if (SLibUtils.getModelReplacement(player) != null) {
			player.sendMessage(Text.translatable("block.minecraft.bed.alternate_form"), true);
			return PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
		}
		return null;
	}
}

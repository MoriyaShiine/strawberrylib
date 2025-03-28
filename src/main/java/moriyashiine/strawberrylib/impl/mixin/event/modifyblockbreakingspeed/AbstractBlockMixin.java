/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.modifyblockbreakingspeed;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.ModifyBlockBreakingSpeedEvent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
	@ModifyReturnValue(method = "calcBlockBreakingDelta", at = @At("RETURN"))
	private float slib$modifyBlockBreakingSpeed(float original, BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
		return ModifyBlockBreakingSpeedEvent.getModifiedBlockBreakingSpeed(original, player, state, world, pos);
	}
}

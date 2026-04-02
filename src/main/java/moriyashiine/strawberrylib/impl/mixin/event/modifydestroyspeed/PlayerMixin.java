/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.modifydestroyspeed;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.ModifyDestroySpeedEvent;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar {
	@Shadow
	@Final
	private Inventory inventory;

	protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
		super(type, level);
	}

	@ModifyExpressionValue(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"))
	private float slib$modifyDestroySpeed(float original, BlockState state) {
		return original * ModifyDestroySpeedEvent.MULTIPLY_BASE.invoker().modify((Player) (Object) this, inventory.getSelectedItem(), level(), state, StrawberryLib.currentEventDestroyPos);
	}

	@ModifyExpressionValue(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttributeValue(Lnet/minecraft/core/Holder;)D", ordinal = 0))
	private double slib$modifyDestroySpeed(double original, BlockState state) {
		return original + ModifyDestroySpeedEvent.ADD_EFFICIENCY.invoker().modify((Player) (Object) this, inventory.getSelectedItem(), level(), state, StrawberryLib.currentEventDestroyPos);
	}

	@ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
	private float slib$modifyDestroySpeedTotal(float original, BlockState state) {
		return original * ModifyDestroySpeedEvent.MULTIPLY_TOTAL.invoker().modify((Player) (Object) this, inventory.getSelectedItem(), level(), state, StrawberryLib.currentEventDestroyPos);
	}
}

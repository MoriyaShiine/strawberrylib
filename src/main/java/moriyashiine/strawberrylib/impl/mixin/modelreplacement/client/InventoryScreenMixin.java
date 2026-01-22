/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
	@ModifyVariable(method = "extractEntityInInventoryFollowsMouse", at = @At("HEAD"), argsOnly = true)
	private static LivingEntity slib$modelReplacement(LivingEntity entity) {
		if (entity instanceof Player player && SLibUtils.getModelReplacement(player) instanceof LivingEntity replacement) {
			return replacement;
		}
		return entity;
	}
}

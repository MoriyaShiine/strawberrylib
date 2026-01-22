/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Equippable.class)
public class EquippableMixin {
	@Inject(method = "swapWithEquipmentSlot", at = @At("HEAD"), cancellable = true)
	private void slib$preventEquipmentUsage(ItemStack inHand, Player player, CallbackInfoReturnable<InteractionResult> cir) {
		if (PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage(player, inHand).get()) {
			cir.setReturnValue(InteractionResult.PASS);
		}
	}
}

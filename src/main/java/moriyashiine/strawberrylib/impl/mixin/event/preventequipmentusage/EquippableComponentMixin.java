/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EquippableComponent.class)
public class EquippableComponentMixin {
	@Inject(method = "equip", at = @At("HEAD"), cancellable = true)
	private void slib$preventEquipmentUsage(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ActionResult> cir) {
		if (PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage(player, stack).get()) {
			cir.setReturnValue(ActionResult.PASS);
		}
	}
}

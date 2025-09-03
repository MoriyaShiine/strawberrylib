/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.ArmorSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin {
	@Shadow
	@Final
	private LivingEntity entity;

	@ModifyReturnValue(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
	private boolean slib$preventEquipmentUsage(boolean original, ItemStack stack) {
		return original && !PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage(entity, stack).get();
	}
}

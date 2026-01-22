/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.ArmorSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin {
	@Shadow
	@Final
	private LivingEntity owner;

	@ModifyReturnValue(method = "mayPlace(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("RETURN"))
	private boolean slib$preventEquipmentUsage(boolean original, ItemStack itemStack) {
		return original && !PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage(owner, itemStack).get();
	}
}

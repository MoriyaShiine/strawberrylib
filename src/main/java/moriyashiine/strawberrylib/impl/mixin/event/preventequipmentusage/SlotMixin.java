/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slot.class)
public class SlotMixin {
	@Shadow
	@Final
	public Container container;

	@Shadow
	@Final
	private int slot;

	@ModifyReturnValue(method = "mayPlace", at = @At("RETURN"))
	private boolean slib$preventEquipmentUsage(boolean original, ItemStack itemStack) {
		if (container instanceof Inventory inventory) {
			if (slot == ModEntityComponents.STORED_EQUIPMENT.get(inventory.player).getHotbarIndex()) {
				return false;
			}
			if (slot == inventory.getSelectedSlot() && PreventEquipmentUsageEvent.cannotEquip(inventory.player, itemStack, EquipmentSlot.MAINHAND)) {
				return false;
			}
			if (slot == Inventory.SLOT_OFFHAND && PreventEquipmentUsageEvent.cannotEquip(inventory.player, itemStack, EquipmentSlot.OFFHAND)) {
				return false;
			}
		}
		return original;
	}
}

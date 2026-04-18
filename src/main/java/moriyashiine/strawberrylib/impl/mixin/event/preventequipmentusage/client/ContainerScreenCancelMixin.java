/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage.client;

import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AbstractContainerScreen.class, CreativeModeInventoryScreen.class})
public class ContainerScreenCancelMixin {
	@Inject(method = "slotClicked", at = @At("HEAD"), cancellable = true)
	private void slib$preventEquipmentUsage(Slot slot, int slotId, int buttonNum, ContainerInput containerInput, CallbackInfo ci) {
		if (slot != null && slotId >= 0 && containerInput == ContainerInput.SWAP) {
			if (slot instanceof CreativeModeInventoryScreen.SlotWrapper wrapper) {
				slot = wrapper.target;
			}
			if (slot.container instanceof Inventory inventory) {
				if (buttonNum == inventory.getSelectedSlot() && PreventEquipmentUsageEvent.cannotEquip(inventory.player, slot.getItem(), EquipmentSlot.MAINHAND)) {
					ci.cancel();
				}
				if (buttonNum == Inventory.SLOT_OFFHAND && PreventEquipmentUsageEvent.cannotEquip(inventory.player, slot.getItem(), EquipmentSlot.OFFHAND)) {
					ci.cancel();
				}
			}
		}
	}
}

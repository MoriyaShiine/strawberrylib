/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.common.component.entity.StoredEquipmentComponent;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
	@Unique
	private static final Identifier BLOCKED_SPRITE = Identifier.withDefaultNamespace("textures/item/barrier.png");

	@Unique
	private boolean renderBlocked = false;

	@ModifyExpressionValue(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack slib$preventEquipmentUsage(ItemStack original, @Local(name = "player") Player player) {
		ItemStack storedOffHandStack = ModEntityComponents.STORED_EQUIPMENT.get(player).getStoredStack(EquipmentSlot.OFFHAND);
		return storedOffHandStack.isEmpty() ? original : storedOffHandStack;
	}

	@WrapOperation(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 0))
	private void slib$preventEquipmentUsageMainHand(Gui instance, GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int seed, Operation<Void> original, @Local(name = "i") int i) {
		StoredEquipmentComponent storedEquipmentComponent = ModEntityComponents.STORED_EQUIPMENT.get(player);
		if (storedEquipmentComponent.getHotbarIndex() == i) {
			itemStack = storedEquipmentComponent.getStoredStack(EquipmentSlot.MAINHAND);
			renderBlocked = true;
		}
		original.call(instance, graphics, x, y, deltaTracker, player, itemStack, seed);
	}

	@WrapOperation(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
	private void slib$preventEquipmentUsageOffHandLeft(Gui instance, GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int seed, Operation<Void> original) {
		ItemStack storedOffHandStack = ModEntityComponents.STORED_EQUIPMENT.get(player).getStoredStack(EquipmentSlot.OFFHAND);
		if (!storedOffHandStack.isEmpty()) {
			itemStack = storedOffHandStack;
			renderBlocked = true;
		}
		original.call(instance, graphics, x, y, deltaTracker, player, itemStack, seed);
	}

	@WrapOperation(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
	private void slib$preventEquipmentUsageOffHandRight(Gui instance, GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int seed, Operation<Void> original) {
		ItemStack storedOffHandStack = ModEntityComponents.STORED_EQUIPMENT.get(player).getStoredStack(EquipmentSlot.OFFHAND);
		if (!storedOffHandStack.isEmpty()) {
			itemStack = storedOffHandStack;
			renderBlocked = true;
		}
		original.call(instance, graphics, x, y, deltaTracker, player, itemStack, seed);
	}

	@Inject(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V"))
	private void slib$preventEquipmentUsage(GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int seed, CallbackInfo ci) {
		if (renderBlocked) {
			renderBlocked = false;
			graphics.blit(RenderPipelines.GUI_TEXTURED, BLOCKED_SPRITE, x, y, 0, 0, 16, 16, 16, 16, 16, 16, 0x7FFFFFFF);
		}
	}
}

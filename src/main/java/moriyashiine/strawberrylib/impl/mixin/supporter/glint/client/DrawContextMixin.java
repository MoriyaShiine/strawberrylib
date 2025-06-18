/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.ItemRenderStateAddition;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class DrawContextMixin {
	@Inject(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/item/ItemModelManager;clearAndUpdate(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V"))
	private void slib$supporterGlintPush(LivingEntity entity, World world, ItemStack stack, int x, int y, int seed, CallbackInfo ci, @Local ItemRenderState itemRenderState) {
		if (entity instanceof PlayerEntity player && SupporterInit.isSupporter(player)) {
			SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(player);
			((ItemRenderStateAddition) itemRenderState).slib$setGlintLayers(GlintLayers.getLayers(stack.contains(DataComponentTypes.EQUIPPABLE) ? supporterComponent.getEquippableGlintColor() : supporterComponent.getGlintColor()));
		}
	}
}

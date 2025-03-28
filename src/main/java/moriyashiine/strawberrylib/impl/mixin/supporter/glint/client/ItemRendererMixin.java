/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At("HEAD"))
	private void slib$supporterGlint(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {
		if (entity instanceof PlayerEntity player && SupporterInit.isSupporter(player)) {
			SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(player);
			GlintLayers.currentLayer = GlintLayers.getLayers(stack.contains(DataComponentTypes.EQUIPPABLE) ? supporterComponent.getEquippableGlintColor() : supporterComponent.getGlintColor());
		}
	}

	@Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At("TAIL"))
	private void slib$supporterGlintTail(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {
		GlintLayers.currentLayer = null;
	}

	@ModifyExpressionValue(method = "getArmorGlintConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorEntityGlint()Lnet/minecraft/client/render/RenderLayer;"))
	private static RenderLayer slib$supporterGlint$armorEntityGlint(RenderLayer original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.armorEntityGlint();
		}
		return original;
	}

	@ModifyExpressionValue(method = "getItemGlintConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getGlintTranslucent()Lnet/minecraft/client/render/RenderLayer;"))
	private static RenderLayer slib$supporterGlint$glintTranslucent(RenderLayer original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glintTranslucent();
		}
		return original;
	}

	@ModifyExpressionValue(method = "getItemGlintConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getGlint()Lnet/minecraft/client/render/RenderLayer;"))
	private static RenderLayer slib$supporterGlint$glint(RenderLayer original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glint();
		}
		return original;
	}

	@ModifyExpressionValue(method = "getItemGlintConsumer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityGlint()Lnet/minecraft/client/render/RenderLayer;"))
	private static RenderLayer slib$supporterGlint$entityGlint(RenderLayer original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.entityGlint();
		}
		return original;
	}
}

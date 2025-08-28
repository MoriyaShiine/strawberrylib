/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
	@Unique
	private static PlayerEntityRenderState playerRenderState = null;

	@Shadow
	public abstract <T extends Entity> EntityRenderer<? super T, ?> getRenderer(T entity);

	@Shadow
	public abstract <E extends Entity> void render(E entity, double x, double y, double z, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

	@Inject(method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V", at = @At("HEAD"), cancellable = true)
	private <E extends Entity, S extends EntityRenderState> void slib$modelReplacement(E entity, double x, double y, double z, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<? super E, S> renderer, CallbackInfo ci) {
		if (entity instanceof PlayerEntity player && player.isPartOfGame()) {
			LivingEntity replacement = SLibUtils.getModelReplacement(player);
			if (replacement != null) {
				playerRenderState = (PlayerEntityRenderState) getRenderer(player).getAndUpdateRenderState(player, tickProgress);
				render(replacement, x, y, z, tickProgress, matrices, vertexConsumers, light);
				playerRenderState = null;
				ci.cancel();
			}
		}
	}

	@WrapOperation(method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;getAndUpdateRenderState(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/entity/state/EntityRenderState;"))
	private <E extends Entity, S extends EntityRenderState> S slib$modelReplacement(EntityRenderer<E, S> instance, E entity, float tickProgress, Operation<S> original) {
		S state = original.call(instance, entity, tickProgress);
		if (playerRenderState != null) {
			// Entity
			state.x = playerRenderState.x;
			state.y = playerRenderState.y;
			state.z = playerRenderState.z;
			state.squaredDistanceToCamera = playerRenderState.squaredDistanceToCamera;
			// LivingEntity
			if (state instanceof LivingEntityRenderState livingState) {
				livingState.bodyYaw = playerRenderState.bodyYaw;
				livingState.relativeHeadYaw = playerRenderState.relativeHeadYaw;
				livingState.pitch = playerRenderState.pitch;
			}
		}
		return state;
	}
}

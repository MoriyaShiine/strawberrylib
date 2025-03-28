/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.client;

import moriyashiine.strawberrylib.api.registry.client.particle.AnchoredParticle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@ModifyVariable(method = "spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private boolean slib$anchoredParticle(boolean force, ParticleEffect parameters) {
		return force || AnchoredParticle.shouldForce;
	}
}

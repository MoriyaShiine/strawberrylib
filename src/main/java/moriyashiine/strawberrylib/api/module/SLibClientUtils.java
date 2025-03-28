/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.module;

import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import moriyashiine.strawberrylib.api.registry.client.particle.AnchoredParticle;
import moriyashiine.strawberrylib.impl.client.sound.AnchoredSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class SLibClientUtils {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	public static boolean isHost(Entity entity) {
		return entity == client.player;
	}

	public static boolean shouldAddParticles(Entity entity) {
		return client.gameRenderer.getCamera().isThirdPerson() || client.getCameraEntity() != entity;
	}

	public static void addAnchoredParticle(Entity entity, ParticleType<?> particleType, double yOffset, double speed, double intensity) {
		AnchoredParticle.shouldForce = true;
		entity.getWorld().addParticleClient((ParticleEffect) particleType, entity.getX(), entity.getZ(), entity.getId(), yOffset, speed, intensity);
		AnchoredParticle.shouldForce = false;
	}

	public static void addEmitterParticle(Entity entity, ParticleType<?> particleType) {
		client.particleManager.addEmitter(entity, (ParticleEffect) particleType);
	}

	public static void addParticleEffects(Entity entity, ParticleEffect particleEffect, int count, ParticleAnchor anchor, ParticleVelocity velocity) {
		if (shouldAddParticles(entity)) {
			for (int i = 0; i < count; i++) {
				double y = entity.getY();
				if (anchor == ParticleAnchor.BODY) {
					y = entity.getRandomBodyY();
				} else if (anchor == ParticleAnchor.EYES) {
					y = entity.getEyeY() + MathHelper.nextDouble(entity.getRandom(), -0.1, 0.1);
				} else if (anchor == ParticleAnchor.CHEST) {
					y += entity.getHeight() * MathHelper.nextDouble(entity.getRandom(), 0.4, 0.8);
				} else if (anchor == ParticleAnchor.FEET) {
					y += entity.getHeight() * 0.15;
				}
				double velocityX, velocityY, velocityZ;
				if (velocity.randomMultiplier() == 0) {
					velocityX = velocity.velocity().getX();
					velocityY = velocity.velocity().getY();
					velocityZ = velocity.velocity().getZ();
				} else {
					Vec3d randomized = velocity.velocity().addRandom(entity.getRandom(), (float) velocity.randomMultiplier());
					velocityX = randomized.getX();
					velocityY = randomized.getY();
					velocityZ = randomized.getZ();
				}
				entity.getWorld().addParticleClient(particleEffect, entity.getParticleX(1), y, entity.getParticleZ(1), velocityX, velocityY, velocityZ);
			}
		}
	}

	public static void addParticles(Entity entity, ParticleType<?> particleType, int count, ParticleAnchor anchor, ParticleVelocity velocity) {
		addParticleEffects(entity, (ParticleEffect) particleType, count, anchor, velocity);
	}

	public static void addParticles(Entity entity, ParticleType<?> particleType, int count, ParticleAnchor anchor) {
		addParticles(entity, particleType, count, anchor, ParticleVelocity.ZERO);
	}

	public static void playAnchoredSound(Entity entity, SoundEvent soundEvent) {
		client.getSoundManager().play(new AnchoredSoundInstance(entity, soundEvent));
	}
}

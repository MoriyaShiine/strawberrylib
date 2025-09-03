/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.registry.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class AnchoredParticle extends SpriteBillboardParticle {
	public static boolean shouldForce = false;

	protected final Entity entity;
	protected final double yOffset, speed, intensity;

	public AnchoredParticle(ClientWorld world, double x, double z, int entityId, double yOffset, double speed, double intensity) {
		super(world, x, Integer.MIN_VALUE, z, 0, 0, 0);
		velocityX = velocityY = velocityZ = 0;
		maxAge = 2;
		scale = 1 / 3F;
		entity = world.getEntityById(entityId);
		this.yOffset = yOffset;
		this.speed = speed;
		this.intensity = intensity;
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		if (entity == null) {
			markDead();
			return;
		}
		x = entity.getX();
		y = entity.getY() + yOffset + MathHelper.sin((float) ((entity.getId() + entity.age) * speed)) * intensity;
		z = entity.getZ();
	}

	public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double z, double entityId, double yOffset, double speed, double intensity) {
			AnchoredParticle particle = new AnchoredParticle(world, x, z, MathHelper.floor(entityId), yOffset, speed, intensity);
			particle.setSprite(spriteProvider);
			return particle;
		}
	}
}
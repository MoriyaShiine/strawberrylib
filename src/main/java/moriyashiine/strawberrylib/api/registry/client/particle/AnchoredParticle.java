/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.registry.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class AnchoredParticle extends SingleQuadParticle {
	protected final Entity entity;
	protected final double yOffset, speed, intensity;

	public AnchoredParticle(ClientLevel level, double x, double z, int entityId, double yOffset, double speed, double intensity, TextureAtlasSprite sprite) {
		super(level, x, Integer.MIN_VALUE, z, 0, 0, 0, sprite);
		xd = yd = zd = 0;
		lifetime = 2;
		quadSize = 1 / 3F;
		entity = level.getEntity(entityId);
		this.yOffset = yOffset;
		this.speed = speed;
		this.intensity = intensity;
	}

	@Override
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		if (entity == null) {
			remove();
			return;
		}
		x = entity.getX();
		y = entity.getY() + yOffset + Mth.sin((float) ((entity.getId() + entity.tickCount) * speed)) * intensity;
		z = entity.getZ();
	}

	public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double z, double entityId, double yOffset, double speed, double intensity, RandomSource random) {
			return new AnchoredParticle(level, x, z, Mth.floor(entityId), yOffset, speed, intensity, sprites().get(random));
		}
	}
}

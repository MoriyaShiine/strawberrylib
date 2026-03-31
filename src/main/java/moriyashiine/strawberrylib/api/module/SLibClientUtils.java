/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.module;

import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import moriyashiine.strawberrylib.impl.client.sound.AnchoredSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public final class SLibClientUtils {
	private static final Minecraft minecraft = Minecraft.getInstance();

	public static boolean isHost(Entity entity) {
		return entity == minecraft.player;
	}

	public static boolean shouldAddParticles(Entity entity) {
		return minecraft.gameRenderer.getMainCamera().isDetached() || minecraft.getCameraEntity() != entity;
	}

	public static void addAnchoredParticle(Entity entity, ParticleType<?> particle, double yOffset, double speed, double intensity) {
		entity.level().addAlwaysVisibleParticle((ParticleOptions) particle, true, entity.getX(), entity.getZ(), entity.getId(), yOffset, speed, intensity);
	}

	public static void addTrackingEmitter(Entity entity, ParticleType<?> particle) {
		minecraft.particleEngine.createTrackingEmitter(entity, (ParticleOptions) particle);
	}

	public static void addParticleOptions(Entity entity, ParticleOptions particle, int count, ParticleAnchor anchor, ParticleVelocity velocity) {
		if (shouldAddParticles(entity)) {
			for (int i = 0; i < count; i++) {
				double y = entity.getY();
				if (anchor == ParticleAnchor.BODY) {
					y = entity.getRandomY();
				} else if (anchor == ParticleAnchor.EYES) {
					y = entity.getEyeY() + Mth.nextDouble(entity.getRandom(), -0.1, 0.1);
				} else if (anchor == ParticleAnchor.CHEST) {
					y += entity.getBbHeight() * Mth.nextDouble(entity.getRandom(), 0.4, 0.8);
				} else if (anchor == ParticleAnchor.FEET) {
					y += entity.getBbHeight() * 0.15;
				}
				double velocityX, velocityY, velocityZ;
				if (velocity.randomMultiplier() == 0) {
					velocityX = velocity.velocity().x();
					velocityY = velocity.velocity().y();
					velocityZ = velocity.velocity().z();
				} else {
					Vec3 randomized = velocity.velocity().offsetRandom(entity.getRandom(), (float) velocity.randomMultiplier());
					velocityX = randomized.x();
					velocityY = randomized.y();
					velocityZ = randomized.z();
				}
				entity.level().addParticle(particle, entity.getRandomX(1), y, entity.getRandomZ(1), velocityX, velocityY, velocityZ);
			}
		}
	}

	public static void addParticles(Entity entity, ParticleType<?> particle, int count, ParticleAnchor anchor, ParticleVelocity velocity) {
		addParticleOptions(entity, (ParticleOptions) particle, count, anchor, velocity);
	}

	public static void addParticles(Entity entity, ParticleType<?> particle, int count, ParticleAnchor anchor) {
		addParticles(entity, particle, count, anchor, ParticleVelocity.ZERO);
	}

	public static void playAnchoredSound(Entity entity, SoundEvent sound) {
		minecraft.getSoundManager().play(new AnchoredSoundInstance(entity, sound));
	}

	public static List<Component> wrapText(Component text, int width) {
		List<Component> lines = new ArrayList<>();
		StringBuilder[] builder = new StringBuilder[1];
		Style[] lastStyle = new Style[1];
		for (FormattedCharSequence sequence : minecraft.font.split(text, width)) {
			builder[0] = new StringBuilder();
			lastStyle[0] = Style.EMPTY;
			MutableComponent component = Component.empty();
			sequence.accept((_, style, codePoint) -> {
				if (!style.equals(lastStyle[0])) {
					if (!builder[0].isEmpty()) {
						component.append(Component.literal(builder[0].toString()).setStyle(lastStyle[0]));
						builder[0] = new StringBuilder();
					}
					lastStyle[0] = style;
				}
				builder[0].appendCodePoint(codePoint);
				return true;
			});
			if (!builder[0].isEmpty()) {
				component.append(Component.literal(builder[0].toString()).setStyle(lastStyle[0]));
			}
			lines.add(component);
		}
		return lines;
	}

	public static List<Component> wrapText(Component text) {
		return wrapText(text, 192);
	}
}

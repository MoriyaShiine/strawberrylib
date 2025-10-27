/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.module;

import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import moriyashiine.strawberrylib.impl.client.sound.AnchoredSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public final class SLibClientUtils {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	public static boolean isHost(Entity entity) {
		return entity == client.player;
	}

	public static boolean shouldAddParticles(Entity entity) {
		return client.gameRenderer.getCamera().isThirdPerson() || client.getCameraEntity() != entity;
	}

	public static void addAnchoredParticle(Entity entity, ParticleType<?> particleType, double yOffset, double speed, double intensity) {
		entity.getEntityWorld().addImportantParticleClient((ParticleEffect) particleType, true, entity.getX(), entity.getZ(), entity.getId(), yOffset, speed, intensity);
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
				entity.getEntityWorld().addParticleClient(particleEffect, entity.getParticleX(1), y, entity.getParticleZ(1), velocityX, velocityY, velocityZ);
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

	public static List<Text> wrapText(Text text, int width) {
		List<Text> lines = new ArrayList<>();
		StringBuilder[] builder = new StringBuilder[1];
		Style[] lastStyle = new Style[1];
		for (OrderedText orderedText : client.textRenderer.wrapLines(text, width)) {
			builder[0] = new StringBuilder();
			lastStyle[0] = Style.EMPTY;
			MutableText mutableText = Text.empty();
			orderedText.accept((index, style, codePoint) -> {
				if (!style.equals(lastStyle[0])) {
					if (!builder[0].isEmpty()) {
						mutableText.append(Text.literal(builder[0].toString()).setStyle(lastStyle[0]));
						builder[0] = new StringBuilder();
					}
					lastStyle[0] = style;
				}
				builder[0].appendCodePoint(codePoint);
				return true;
			});
			if (!builder[0].isEmpty()) {
				mutableText.append(Text.literal(builder[0].toString()).setStyle(lastStyle[0]));
			}
			lines.add(mutableText);
		}
		return lines;
	}

	public static List<Text> wrapText(Text text) {
		return wrapText(text, 192);
	}
}

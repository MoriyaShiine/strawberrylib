/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.sound;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

import java.util.function.Predicate;

public class AnchoredSoundInstance extends MovingSoundInstance {
	private final Entity entity;
	private final Predicate<Entity> donePredicate;

	public AnchoredSoundInstance(Entity entity, SoundEvent soundEvent, Predicate<Entity> donePredicate) {
		super(soundEvent, entity.getSoundCategory(), entity.getRandom());
		this.entity = entity;
		this.donePredicate = donePredicate;
	}

	public AnchoredSoundInstance(Entity entity, SoundEvent soundEvent) {
		this(entity, soundEvent, currentEntity -> false);
	}

	@Override
	public void tick() {
		if (entity == null || !entity.isAlive() || donePredicate.test(entity)) {
			volume -= 0.1F;
			if (volume <= 0) {
				setDone();
			}
			return;
		}
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
	}
}

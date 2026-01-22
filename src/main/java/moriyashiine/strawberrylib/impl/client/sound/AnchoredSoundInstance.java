/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

public class AnchoredSoundInstance extends AbstractTickableSoundInstance {
	private final Entity entity;
	private final Predicate<Entity> finishPredicate;

	public AnchoredSoundInstance(Entity entity, SoundEvent event, Predicate<Entity> finishPredicate) {
		super(event, entity.getSoundSource(), entity.getRandom());
		this.entity = entity;
		this.finishPredicate = finishPredicate;
	}

	public AnchoredSoundInstance(Entity entity, SoundEvent soundEvent) {
		this(entity, soundEvent, _ -> false);
	}

	@Override
	public void tick() {
		if (entity == null || !entity.isAlive() || finishPredicate.test(entity)) {
			volume -= 0.1F;
			if (volume <= 0) {
				stop();
			}
			return;
		}
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
	}
}

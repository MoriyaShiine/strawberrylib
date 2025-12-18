/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.component.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModelReplacementComponent implements AutoSyncedComponent, CommonTickingComponent {
	public static final List<CopyFunction> COPY_FUNCTIONS = new ArrayList<>();

	public static boolean disableAttack = false, disableTick = false;

	private final PlayerEntity obj;
	private final List<ReplacementType> replacementTypes = new ArrayList<>();
	@Nullable
	private LivingEntity replacement = null;
	private int ambientSoundChance = 0;

	public ModelReplacementComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		replacementTypes.clear();
		replacementTypes.addAll(readView.read("ReplacementTypes", ReplacementType.CODEC.listOf()).orElse(List.of()));
		ambientSoundChance = readView.getInt("AmbientSoundChance", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.put("ReplacementTypes", ReplacementType.CODEC.listOf(), replacementTypes);
		writeView.putInt("AmbientSoundChance", ambientSoundChance);
	}

	public void sync() {
		ModEntityComponents.MODEL_REPLACEMENT.sync(obj);
	}

	@Override
	public void tick() {
		@Nullable EntityType<?> replacementType = replacementTypes.isEmpty() ? null : replacementTypes.getFirst().type();
		if (replacement == null && replacementType != null) {
			if (replacementType.create(obj.getEntityWorld(), SpawnReason.LOAD) instanceof LivingEntity living) {
				replacement = living;
				obj.calculateDimensions();
			} else {
				StrawberryLib.LOGGER.error("Entity Type '{}' is not a living entity, cannot replace player model.", replacementType);
				replacementType = null;
			}
		}
		if (replacementType == null || (replacement != null && (obj.getEntityWorld() != replacement.getEntityWorld() || replacement.getType() != replacementType))) {
			replacement = null;
			ambientSoundChance = 0;
			obj.calculateDimensions();
		}
		if (replacement != null) {
			copyData(replacement);
			if (obj.isPartOfGame() && replacement instanceof MobEntity mob && mob.getRandom().nextInt(1000) < ambientSoundChance++) {
				resetSoundDelay(mob);
				obj.playSound(mob.getAmbientSound());
			}
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (replacement != null && obj.isPartOfGame()) {
			disableTick = true;
			replacement.tick();
			disableTick = false;
		}
	}

	@Nullable
	public LivingEntity getReplacement() {
		return replacement;
	}

	public boolean hasReplacementType(EntityType<?> type) {
		return replacementTypes.stream().anyMatch(replacementType -> replacementType.type() == type);
	}

	public void addReplacementType(EntityType<?> type, int priority) {
		replacementTypes.add(new ReplacementType(type, priority));
		replacementTypes.sort(Comparator.comparingInt(ReplacementType::priority));
	}

	public void removeReplacementType(EntityType<?> type) {
		for (int i = replacementTypes.size() - 1; i >= 0; i--) {
			if (replacementTypes.get(i).type() == type) {
				replacementTypes.remove(i);
			}
		}
	}

	public void resetSoundDelay(MobEntity mob) {
		ambientSoundChance = -mob.getMinAmbientSoundDelay();
	}

	private void copyData(LivingEntity replacement) {
		// Entity
		replacement.refreshPositionAndAngles(obj.getEntityPos(), obj.getYaw(), obj.getPitch());
		replacement.setSneaking(obj.isSneaking());
		replacement.setSprinting(obj.isSprinting());
		replacement.setSwimming(obj.isSwimming());
		replacement.setInvisible(obj.isInvisible());
		replacement.setGlowing(obj.isGlowing());
		replacement.setAir(obj.getAir());
		replacement.setCustomName(obj.getCustomName());
		replacement.setCustomNameVisible(obj.isCustomNameVisible());
		replacement.setPose(obj.getPose());
		replacement.setFrozenTicks(obj.getFrozenTicks());
		if (obj.hasVehicle()) {
			replacement.startRiding(obj.getVehicle(), true, false);
		} else {
			replacement.dismountVehicle();
		}
		replacement.setOnGround(obj.isOnGround());
		replacement.horizontalCollision = obj.horizontalCollision;
		replacement.verticalCollision = obj.verticalCollision;
		replacement.groundCollision = obj.groundCollision;
		replacement.collidedSoftly = obj.collidedSoftly;
		replacement.age = obj.age;
		replacement.setOnFire(obj.isOnFire());
		replacement.timeUntilRegen = obj.timeUntilRegen;
		replacement.inPowderSnow = obj.inPowderSnow;
		replacement.wasInPowderSnow = obj.wasInPowderSnow;
		// LivingEntity
		replacement.setJumping(obj.jumping && !obj.getAbilities().flying);
		replacement.handSwinging = obj.handSwinging;
		replacement.preferredHand = obj.getMainArm() == Arm.RIGHT ? Hand.MAIN_HAND : Hand.OFF_HAND;
		replacement.handSwingTicks = obj.handSwingTicks;
		replacement.hurtTime = obj.hurtTime;
		replacement.maxHurtTime = obj.maxHurtTime;
		replacement.deathTime = obj.deathTime;
		replacement.lastHandSwingProgress = obj.lastHandSwingProgress;
		replacement.handSwingProgress = obj.handSwingProgress;
		replacement.limbAnimator = obj.limbAnimator;
		replacement.bodyYaw = obj.bodyYaw;
		replacement.lastBodyYaw = obj.lastBodyYaw;
		replacement.headYaw = obj.headYaw;
		replacement.lastHeadYaw = obj.lastHeadYaw;
		replacement.leaningPitch = obj.leaningPitch;
		replacement.lastLeaningPitch = obj.lastLeaningPitch;
		replacement.setCurrentHand(obj.getActiveHand() == null ? Hand.MAIN_HAND : obj.getActiveHand());
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			replacement.equipStack(slot, obj.getEquippedStack(slot));
		}
		COPY_FUNCTIONS.forEach(copyFunction -> copyFunction.copyData(obj, replacement));
	}

	public interface CopyFunction {
		void copyData(PlayerEntity player, LivingEntity replacement);
	}

	private record ReplacementType(EntityType<?> type, int priority) {
		private static final Codec<ReplacementType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						EntityType.CODEC.fieldOf("entity_type").forGetter(ReplacementType::type),
						Codec.INT.fieldOf("priority").forGetter(ReplacementType::priority))
				.apply(instance, ReplacementType::new));
	}
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.component.entity;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ModelReplacementComponent implements AutoSyncedComponent, CommonTickingComponent {
	public static boolean disableAttack = false, disableTick = false;

	private final PlayerEntity obj;
	@Nullable
	private EntityType<?> replacementType = null;
	@Nullable
	private LivingEntity replacement = null;
	private int ambientSoundChance = 0;

	public ModelReplacementComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		readView.getOptionalString("ReplacementType").ifPresentOrElse(type -> replacementType = Registries.ENTITY_TYPE.get(Identifier.of(type)), () -> replacementType = null);
		ambientSoundChance = readView.getInt("AmbientSoundChance", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		if (replacementType != null) {
			writeView.putString("ReplacementType", Registries.ENTITY_TYPE.getId(replacementType).toString());
			writeView.putInt("AmbientSoundChance", ambientSoundChance);
		}
	}

	public void sync() {
		ModEntityComponents.MODEL_REPLACEMENT.sync(obj);
	}

	@Override
	public void tick() {
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
			if (replacement instanceof MobEntity mob && mob.getRandom().nextInt(1000) < ambientSoundChance++) {
				resetSoundDelay(mob);
				obj.playSound(mob.getAmbientSound());
			}
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (replacement != null) {
			disableTick = true;
			replacement.tick();
			disableTick = false;
		}
	}

	@Nullable
	public LivingEntity getReplacement() {
		return replacement;
	}

	public void setReplacementType(@Nullable EntityType<?> replacementType) {
		if (replacementType == EntityType.PLAYER) {
			replacementType = null;
		}
		this.replacementType = replacementType;
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
	}
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.component.entity;

import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ModelReplacementComponent implements AutoSyncedComponent, CommonTickingComponent {
	public static boolean disableAttack = false, disableTick = false, enableRenderCheck = false;

	private final PlayerEntity obj;
	@Nullable
	private EntityType<?> replacementType = null;
	@Nullable
	private LivingEntity replacement = null;

	public ModelReplacementComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		tag.getString("ReplacementType").ifPresentOrElse(string -> replacementType = Registries.ENTITY_TYPE.get(Identifier.of(string)), () -> replacementType = null);
	}

	@Override
	public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		if (replacementType != null) {
			tag.putString("ReplacementType", Registries.ENTITY_TYPE.getId(replacementType).toString());
		}
	}

	public void sync() {
		ModEntityComponents.MODEL_REPLACEMENT.sync(obj);
	}

	@Override
	public void tick() {
		if (replacement == null && replacementType != null) {
			replacement = (LivingEntity) replacementType.create(obj.getWorld(), SpawnReason.LOAD);
			obj.calculateDimensions();
		}
		if (replacementType == null || (replacement != null && (obj.getWorld() != replacement.getWorld() || replacement.getType() != replacementType))) {
			replacement = null;
			obj.calculateDimensions();
		}
		if (replacement != null) {
			copyData(replacement);
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
		this.replacementType = replacementType;
	}

	private void copyData(LivingEntity replacement) {
		// Entity
		replacement.refreshPositionAndAngles(obj.getPos(), obj.getYaw(), obj.getPitch());
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
			replacement.startRiding(obj.getVehicle(), true);
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
		replacement.setJumping(obj.jumping);
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

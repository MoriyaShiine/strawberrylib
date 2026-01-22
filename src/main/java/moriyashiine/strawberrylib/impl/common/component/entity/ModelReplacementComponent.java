/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.component.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModelReplacementComponent implements AutoSyncedComponent, CommonTickingComponent {
	public static final List<CopyFunction> COPY_FUNCTIONS = new ArrayList<>();

	public static boolean disableAttack = false, disableTick = false;

	private final Player obj;
	private final List<ReplacementType> replacementTypes = new ArrayList<>();
	@Nullable
	private LivingEntity replacement = null;
	private int ambientSoundTime = 0;

	public ModelReplacementComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		replacementTypes.clear();
		replacementTypes.addAll(input.read("ReplacementTypes", ReplacementType.CODEC.listOf()).orElse(List.of()));
		ambientSoundTime = input.getIntOr("AmbientSoundTime", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.store("ReplacementTypes", ReplacementType.CODEC.listOf(), replacementTypes);
		output.putInt("AmbientSoundTime", ambientSoundTime);
	}

	public void sync() {
		ModEntityComponents.MODEL_REPLACEMENT.sync(obj);
	}

	@Override
	public void tick() {
		@Nullable EntityType<?> replacementType = replacementTypes.isEmpty() ? null : replacementTypes.getFirst().type();
		if (replacement == null && replacementType != null) {
			if (replacementType.create(obj.level(), EntitySpawnReason.LOAD) instanceof LivingEntity living) {
				replacement = living;
				obj.refreshDimensions();
			} else {
				StrawberryLib.LOGGER.error("Entity Type '{}' is not a living entity, cannot replace player model.", replacementType);
				replacementType = null;
			}
		}
		if (replacementType == null || (replacement != null && (obj.level() != replacement.level() || replacement.getType() != replacementType))) {
			replacement = null;
			ambientSoundTime = 0;
			obj.refreshDimensions();
		}
		if (replacement != null) {
			copyData(replacement);
			if (obj.slib$exists() && replacement instanceof Mob mob && mob.getRandom().nextInt(1000) < ambientSoundTime++) {
				resetAmbientSoundTime(mob);
				obj.makeSound(mob.getAmbientSound());
			}
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (replacement != null && obj.slib$exists()) {
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

	public void resetAmbientSoundTime(Mob mob) {
		ambientSoundTime = -mob.getAmbientSoundInterval();
	}

	private void copyData(LivingEntity replacement) {
		// Entity
		replacement.snapTo(obj.position(), obj.getYRot(), obj.getXRot());
		replacement.setShiftKeyDown(obj.isShiftKeyDown());
		replacement.setSprinting(obj.isSprinting());
		replacement.setSwimming(obj.isSwimming());
		replacement.setInvisible(obj.isInvisible());
		replacement.setGlowingTag(obj.hasGlowingTag());
		replacement.setAirSupply(obj.getAirSupply());
		replacement.setCustomName(obj.getCustomName());
		replacement.setCustomNameVisible(obj.isCustomNameVisible());
		replacement.setPose(obj.getPose());
		replacement.setTicksFrozen(obj.getTicksFrozen());
		if (obj.isPassenger()) {
			replacement.startRiding(obj.getVehicle(), true, false);
		} else {
			replacement.removeVehicle();
		}
		replacement.setOnGround(obj.onGround());
		replacement.horizontalCollision = obj.horizontalCollision;
		replacement.verticalCollision = obj.verticalCollision;
		replacement.verticalCollisionBelow = obj.verticalCollisionBelow;
		replacement.minorHorizontalCollision = obj.minorHorizontalCollision;
		replacement.tickCount = obj.tickCount;
		replacement.setSharedFlagOnFire(obj.isOnFire());
		replacement.invulnerableTime = obj.invulnerableTime;
		replacement.isInPowderSnow = obj.isInPowderSnow;
		replacement.wasInPowderSnow = obj.wasInPowderSnow;
		// LivingEntity
		replacement.setJumping(obj.jumping && !obj.getAbilities().flying);
		replacement.swinging = obj.swinging;
		replacement.swingingArm = obj.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
		replacement.swingTime = obj.swingTime;
		replacement.hurtTime = obj.hurtTime;
		replacement.hurtDuration = obj.hurtDuration;
		replacement.deathTime = obj.deathTime;
		replacement.oAttackAnim = obj.oAttackAnim;
		replacement.attackAnim = obj.attackAnim;
		replacement.walkAnimation = obj.walkAnimation;
		replacement.yBodyRot = obj.yBodyRot;
		replacement.yBodyRotO = obj.yBodyRotO;
		replacement.yHeadRot = obj.yHeadRot;
		replacement.yHeadRotO = obj.yHeadRotO;
		replacement.swimAmount = obj.swimAmount;
		replacement.swimAmountO = obj.swimAmountO;
		replacement.startUsingItem(obj.getUsedItemHand());
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			replacement.setItemSlot(slot, obj.getItemBySlot(slot));
		}
		COPY_FUNCTIONS.forEach(copyFunction -> copyFunction.copyData(obj, replacement));
	}

	public interface CopyFunction {
		void copyData(Player player, LivingEntity replacement);
	}

	private record ReplacementType(EntityType<?> type, int priority) {
		private static final Codec<ReplacementType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						EntityType.CODEC.fieldOf("entity_type").forGetter(ReplacementType::type),
						Codec.INT.fieldOf("priority").forGetter(ReplacementType::priority))
				.apply(instance, ReplacementType::new));
	}
}

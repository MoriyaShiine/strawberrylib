/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.supporter.component.entity;

import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.impl.client.supporter.SupporterOptions;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.payload.SyncGlintColorPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class SupporterComponent implements AutoSyncedComponent, ClientTickingComponent {
	private final PlayerEntity obj;
	private GlintColor equippableGlintColor = GlintColor.PURPLE, glintColor = GlintColor.PURPLE;

	public SupporterComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
		equippableGlintColor = GlintColor.valueOf(tag.getString("EquippableGlintColor", GlintColor.PURPLE.name()));
		glintColor = GlintColor.valueOf(tag.getString("GlintColor", GlintColor.PURPLE.name()));
	}

	@Override
	public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
		tag.putString("EquippableGlintColor", equippableGlintColor.name());
		tag.putString("GlintColor", glintColor.name());
	}

	@Override
	public void clientTick() {
		if (SLibClientUtils.isHost(obj) && SupporterInit.isSupporter(obj)) {
			if (getEquippableGlintColor() != SupporterOptions.EQUIPPABLE_GLINT_COLOR.getValue()) {
				equippableGlintColor = SupporterOptions.EQUIPPABLE_GLINT_COLOR.getValue();
				SyncGlintColorPayload.send(true, equippableGlintColor);
			}
			if (getGlintColor() != SupporterOptions.GLINT_COLOR.getValue()) {
				glintColor = SupporterOptions.GLINT_COLOR.getValue();
				SyncGlintColorPayload.send(false, glintColor);
			}
		}
	}

	public void sync() {
		ModEntityComponents.SUPPORTER.sync(obj);
	}

	public GlintColor getEquippableGlintColor() {
		return equippableGlintColor;
	}

	public void setEquippableGlintColor(GlintColor equippableGlintColor) {
		this.equippableGlintColor = equippableGlintColor;
	}

	public GlintColor getGlintColor() {
		return glintColor;
	}

	public void setGlintColor(GlintColor glintColor) {
		this.glintColor = glintColor;
	}
}

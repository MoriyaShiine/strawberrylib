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
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class SupporterComponent implements AutoSyncedComponent, ClientTickingComponent {
	private final PlayerEntity obj;
	private GlintColor equippableGlintColor = GlintColor.PURPLE, glintColor = GlintColor.PURPLE;

	public SupporterComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		equippableGlintColor = GlintColor.valueOf(readView.getString("EquippableGlintColor", GlintColor.PURPLE.name()));
		glintColor = GlintColor.valueOf(readView.getString("GlintColor", GlintColor.PURPLE.name()));
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putString("EquippableGlintColor", equippableGlintColor.name());
		writeView.putString("GlintColor", glintColor.name());
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

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.supporter.component.entity;

import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterData;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataKey;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

import java.util.HashMap;
import java.util.Map;

public class SupporterComponent implements AutoSyncedComponent, ClientTickingComponent {
	private final PlayerEntity obj;
	private final Map<SupporterDataKey<?>, SupporterData<?>> dataMap = new HashMap<>();

	public SupporterComponent(PlayerEntity obj) {
		this.obj = obj;
		SupporterInit.DATA.forEach((key, data) -> dataMap.put(key, data.copy()));
	}

	@Override
	public void readData(ReadView readView) {
		dataMap.values().forEach(data -> data.readData(readView));
	}

	@Override
	public void writeData(WriteView writeView) {
		dataMap.values().forEach(data -> data.writeData(writeView));
	}

	@Override
	public void clientTick() {
		if (SLibClientUtils.isHost(obj) && SLibSupporterUtils.isSupporter(obj)) {
			ClientSupporterInit.OPTIONS.forEach((key, clientData) -> {
				SupporterData<?> data = getData(key);
				if (data.getValue() != clientData.option().getValue()) {
					data.setValueFromOption(clientData);
				}
			});
		}
	}

	public void sync() {
		ModEntityComponents.SUPPORTER.sync(obj);
	}

	@SuppressWarnings("unchecked")
	public <T> SupporterData<T> getData(SupporterDataKey<T> key) {
		return (SupporterData<T>) dataMap.get(key);
	}
}

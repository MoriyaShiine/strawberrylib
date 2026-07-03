package moriyashiine.strawberrylib.impl.common.supporter.component.entity;

import com.mojang.serialization.Codec;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataType;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import moriyashiine.strawberrylib.impl.common.init.StrawberryLibEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.payload.SyncSupporterValuePayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

import java.util.HashMap;
import java.util.Map;

public class SupporterComponent implements AutoSyncedComponent, ClientTickingComponent {
	private static final Codec<Map<SupporterDataType<?>, Object>> DATA_MAP_CODEC = Codec.dispatchedMap(SupporterDataType.CODEC, SupporterDataType::codec);

	private final Player obj;
	private final Map<SupporterDataType<?>, Object> dataMap = new HashMap<>();

	public SupporterComponent(Player obj) {
		this.obj = obj;
		initDataMap();
	}

	@Override
	public void readData(ValueInput input) {
		initDataMap();
		input.read("DataMap", DATA_MAP_CODEC).ifPresent(dataMap::putAll);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.store("DataMap", DATA_MAP_CODEC, dataMap);
	}

	@Override
	public void clientTick() {
		if (SLibClientUtils.isHost(obj) && SLibSupporterUtils.isSupporter(obj)) {
			ClientSupporterInit.OPTIONS.forEach((type, clientData) -> {
				Object value = getValue(type);
				Object clientValue = clientData.option().get();
				if (value != clientValue) {
					setValueAndSync(type, clientValue);
				}
			});
		}
	}

	public void sync() {
		StrawberryLibEntityComponents.SUPPORTER.sync(obj);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(SupporterDataType<T> type) {
		return (T) dataMap.get(type);
	}

	public <T> void setValue(SupporterDataType<T> type, T value) {
		dataMap.put(type, value);
	}

	@SuppressWarnings("unchecked")
	private <T> void setValueAndSync(SupporterDataType<T> type, Object clientValue) {
		setValue(type, (T) clientValue);
		SyncSupporterValuePayload.send(type, (T) clientValue);
	}

	private void initDataMap() {
		SupporterInit.SUPPORTER_DATA_TYPE.forEach(type -> dataMap.put(type, type.initialValue()));
	}
}

/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.supporter.objects;

import com.mojang.serialization.Codec;
import moriyashiine.strawberrylib.api.supporter.objects.client.ClientSupporterData;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

public class SupporterData<T> {
	private final String key;
	private final Codec<T> codec;
	private T value;
	private final T defaultValue;

	public SupporterData(SupporterDataKey<T> key, Codec<T> codec, T defaultValue) {
		this(key.id().toString().replace(":", "."), codec, defaultValue, defaultValue);
	}

	private SupporterData(String key, Codec<T> codec, T value, T defaultValue) {
		this.key = key;
		this.codec = codec;
		this.value = value;
		this.defaultValue = defaultValue;
	}

	public SupporterData<T> copy() {
		return new SupporterData<>(key, codec, value, defaultValue);
	}

	public void readData(ReadView view) {
		value = view.read(key, codec).orElse(defaultValue);
	}

	public void writeData(WriteView view) {
		view.put(key, codec, value);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public <O> void setValueFromOption(ClientSupporterData<O> data) {
		this.value = (T) data.option().getValue();
		data.payloadSender().send((O) value);
	}
}

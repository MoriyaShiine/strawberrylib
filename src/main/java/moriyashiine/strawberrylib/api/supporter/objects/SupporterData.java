/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.supporter.objects;

import com.mojang.serialization.Codec;
import moriyashiine.strawberrylib.api.supporter.objects.client.ClientSupporterData;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SupporterData<T> {
	private final String key;
	private final Codec<T> codec;
	private T value;
	private final T initialValue;

	public SupporterData(SupporterDataKey<T> key, Codec<T> codec, T initialValue) {
		this(key.id().toString().replace(":", "."), codec, initialValue, initialValue);
	}

	private SupporterData(String key, Codec<T> codec, T value, T initialValue) {
		this.key = key;
		this.codec = codec;
		this.value = value;
		this.initialValue = initialValue;
	}

	public SupporterData<T> copy() {
		return new SupporterData<>(key, codec, value, initialValue);
	}

	public void readData(ValueInput view) {
		value = view.read(key, codec).orElse(initialValue);
	}

	public void writeData(ValueOutput view) {
		view.store(key, codec, value);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public <O> void setValueFromOption(ClientSupporterData<O> data) {
		this.value = (T) data.option().get();
		data.payloadSender().send((O) value);
	}
}

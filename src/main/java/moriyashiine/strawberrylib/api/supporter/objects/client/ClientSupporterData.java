/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.supporter.objects.client;

import net.minecraft.client.OptionInstance;

public record ClientSupporterData<T>(String optionKey, OptionInstance<T> option, T initialValue, Reader<T> reader, Writer<T> writer, PayloadSender<T> payloadSender) {
	public void read(String key) {
		option().set(reader().read(key));
	}

	public String write() {
		return writer().write(option().get());
	}

	public String initialName() {
		return writer().write(initialValue());
	}

	public interface Reader<T> {
		T read(String key);
	}

	public interface Writer<T> {
		String write(T value);
	}

	public interface PayloadSender<T> {
		void send(T value);
	}
}

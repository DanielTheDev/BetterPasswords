package io.github.danielthedev.passwords;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class SecurityInputStream extends DataInputStream {
	
	public SecurityInputStream(byte[] data) {
		super(new ByteArrayInputStream(data));
	}
	
	public <T> T readDecrypt(Unsafe.Function<SecurityInputStream, T> callback, String key) throws Exception {
		int size = this.readInt();
		byte[] payload = new byte[size];
		this.read(payload);
		byte[] decrypted = Security.decrypt(payload, Security.enforcePassword(key));
		try(SecurityInputStream in = new SecurityInputStream(decrypted)) {
			return callback.accept(in);
		}
	}

}

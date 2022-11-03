package io.github.danielthedev.passwords;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SecurityOutputStream extends DataOutputStream {
	
	public SecurityOutputStream() {
		super(new ByteArrayOutputStream());
	}
	
	public void writeEncrypt(Unsafe.Consumer<SecurityOutputStream> callback, String key) throws Exception {
		try(SecurityOutputStream out = new SecurityOutputStream()) {
			callback.accept(out);
			byte[] content = out.toByteArray();
			byte[] encrypted = Security.encrypt(content, Security.enforcePassword(key));
			this.writeInt(encrypted.length);
			this.write(encrypted);
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	public byte[] toByteArray() {
		return ((ByteArrayOutputStream)this.out).toByteArray();
	}

}

package io.github.danielthedev.passwords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
public class Security {
	
	private static final String ENCRYPTION_SALT = "CGG8rtDG8zh4Fp5mN6WAe7kY";
	private static final String[] SALT_TABLE;
	private static final char[] HEX = new char[]{'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	private static final char[] ENCODING = new char[] { '*', '/', '\\', '+', '@', '#', '$', '!', '^', '%', '&', '(',
			')', '[', ']', ':', ';', '.', ',', '=', '-', '?', '{', '}','0','1','2','3','4','5','6','7','8','9'};
	
	static {
		String[] table = new String[1000];
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(Security.class.getResourceAsStream("/salt.table")))) {
			for(int t = 0; t < table.length; t++) {
				table[t] = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		SALT_TABLE = table;
	}
	
	public static String enforcePassword(String password) {
		return password + Security.encode(password);
	}
	
	public static byte[] decrypt(byte[] payload, String secret) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");  
	    KeySpec spec = new PBEKeySpec(secret.toCharArray(), ENCRYPTION_SALT.getBytes(), 65536, 256);  
	    SecretKey key = factory.generateSecret(spec);
	    SecretKeySpec secretKey = new SecretKeySpec(key.getEncoded(), "AES");  
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);  
		return cipher.doFinal(payload);
	}
	
	public static byte[] encrypt(byte[] payload, String secret) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");  
	    KeySpec spec = new PBEKeySpec(secret.toCharArray(), ENCRYPTION_SALT.getBytes(), 65536, 256);  
	    SecretKey key = factory.generateSecret(spec);
	    SecretKeySpec secretKey = new SecretKeySpec(key.getEncoded(), "AES");  
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);  
		return cipher.doFinal(payload);
	}
	
	public static String hash(String string) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < SALT_TABLE.length; i++) {
			StringBuilder builder = new StringBuilder();
			md.reset();
			md.update(SALT_TABLE[i].getBytes(StandardCharsets.UTF_8));
			md.update(string.getBytes(StandardCharsets.UTF_8));
			for(byte b : md.digest()) {
				builder.append(HEX[(b & 0xFF) >> 4]).append(HEX[b & 0x0F]);
			}
			string = builder.toString();
		}

		return string;
	}
	
	public static String encode(String text) {
		char[] chars = Base64.getEncoder().encodeToString(text.getBytes()).toCharArray();
		StringBuilder builder = new StringBuilder();
		for (int t = 0; t < chars.length; t++) {
			int intVal = (int) (chars[chars.length - t - 1]);
			String val = "";
			do {
				val+=ENCODING[intVal % ENCODING.length];
				intVal /= ENCODING.length;
			} while (intVal != 0);
			builder.append(val);
			builder.append("|");
		}
		return builder.substring(0, builder.length()-1);
	}

	public static String decode(String text) {
		String[] parts = text.split("\\|");
		char[] decode = new char[parts.length];
		for(int p = 0; p < parts.length; p++) {
			int sum = 0;
			char[] chars = parts[p].toCharArray();
			for (int t = 0; t < chars.length; t++) {
				char c = chars[t];
				for (int i = 0; i < ENCODING.length; i++) {
					if (ENCODING[i] == c) {
						
						sum += i * (Math.pow(ENCODING.length, t));
					}
				}
			}
			decode[decode.length-1-p] = (char) sum;
		}
		return new String(Base64.getDecoder().decode(new String(decode)));
	}
	
}

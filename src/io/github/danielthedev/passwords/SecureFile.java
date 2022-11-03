package io.github.danielthedev.passwords;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.nfd.NativeFileDialog;

public class SecureFile {

	private final static String FILE_KEY = Security.hash(Security.encode("1x#phbQ32s6d&Bwt5*qvKco4YMNV9*YjBO&OgC8mFYeoUIBy6BM$881*6V9ZWeAT"));
	private final static String EXTENSION = "epf";
	
	private final JSONObject content;
	
	public SecureFile(JSONObject content) {
		this.content = content;
	}
	
	public JSONObject getContent() {
		return content;
	}

	public static List<String> readPasswordHints(File file) throws IOException {
		List<String> passwordHints = new ArrayList<>();
		byte[] fileContent = Files.readAllBytes(file.toPath());
		try(SecurityInputStream in = new SecurityInputStream(fileContent)) {
			in.readDecrypt((i)->{				
				int hints = i.readInt();
				for(int t = 0; t < hints; t++) {
					passwordHints.add(i.readUTF());
				}
				return hints;
			}, FILE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passwordHints;
	}
	
	public static SecureFile readFile(String password) throws IOException {
		File file = new File("C:\\Users\\danie\\Desktop\\hey.epf");//selectFile(false);
		byte[] fileContent = Files.readAllBytes(file.toPath());
		try(SecurityInputStream in = new SecurityInputStream(fileContent)) {
			return in.readDecrypt((i)->{
				int hints = i.readInt();
				for(int t = 0; t < hints; t++) {i.readUTF();}
				String key = null;
				for(int t = 0; t < hints; t++) {
					try {
						key = i.readDecrypt((ii)->ii.readUTF(), password);
					} catch (Exception e) {}
				}
				if(key == null) throw new NullPointerException();
				JSONObject obj = i.readDecrypt((ii)->new JSONObject(ii.readUTF()), key);
				return new SecureFile(obj);
			}, FILE_KEY); //skip hints
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void createFile(File file, List<Password> passwords) throws NoSuchAlgorithmException {
		if(passwords.size() == 0) throw new UnsupportedOperationException();
		byte[] key = new byte[256];
		SecureRandom.getInstanceStrong().nextBytes(key);
		String secureKey = new String(key);
		
		try (SecurityOutputStream out = new SecurityOutputStream()) {
			out.writeEncrypt((o) -> {
				o.writeInt(passwords.size());
				passwords.forEach(p->{
					try {
						o.writeUTF(p.getHint());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				passwords.forEach(p->{
					try {
						o.writeEncrypt((oo)->oo.writeUTF(Security.encode(secureKey)), p.getPassword());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				o.writeEncrypt((oo)->oo.writeUTF(content.toString()), Security.encode(secureKey));
			}, FILE_KEY);
			Files.write(file.toPath(), out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}

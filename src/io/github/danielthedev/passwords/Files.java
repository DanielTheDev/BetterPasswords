package io.github.danielthedev.passwords;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.lwjgl.PointerBuffer;
import org.lwjgl.util.nfd.NativeFileDialog;

public class Files {
	
	private final static String EXTENSION = "epf";

	public static File createFile() {
		return getFile(true);
	}
	
	public static File selectFile() {
		return getFile(false);
	}
	
	
	private static File getFile(boolean create) {
		if(System.getProperty("os.name").startsWith("Windows")) {
			PointerBuffer pb = PointerBuffer.allocateDirect(260);
			try {
				String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath().toString();
				int result;
				if(create) {
					result = NativeFileDialog.NFD_SaveDialog(EXTENSION, desktop, pb);
				} else {
					result = NativeFileDialog.NFD_OpenDialog(EXTENSION, desktop, pb);
				}
				if(result == NativeFileDialog.NFD_OKAY) {
					return new File(pb.getStringUTF8());
				} return null;
			} finally {
				pb.free();
			}
		} else {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select a file");
			jfc.setAcceptAllFileFilterUsed(false);
			
			FileFilter filter = new FileFilter() {
			    public String getDescription() {
			        return "Encrypted Password File (*.epf)";
			    }
			 
			    public boolean accept(File f) {
			        if (f.isDirectory()) {
			            return true;
			        } else {
			            return f.getName().toLowerCase().endsWith(".epf");
			        }
			    }
			};
			
						jfc.addChoosableFileFilter(filter);
			int returnValue = create ? jfc.showSaveDialog(null) : jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				return jfc.getSelectedFile();
			} else return null;
		}
	}
	
}

package laskowski.rafal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeHelper {
	private File file;

	public SerializeHelper(File file) {
		this.file = file;
	}

	public SerializeHelper(String directory, String fileName) {
		if (!fileName.endsWith(".ser")) {
			fileName += ".ser";
		}
		this.file = new File(directory, fileName);
	}

	public void out(Object object) {
		try {
			if (file.exists()) {
				file.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Object in() {
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object object = in.readObject();
			in.close();
			fileIn.close();

			return object;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void delete() {
		file.delete();
	}
}

package laskowski.rafal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import laskowski.rafal.constants.Directories;

public class ImageLoader {

	public static Image load(String fileName) {
		return loadImage(fileName);
	}

	private static Image loadImage(String fileName) {
		try {
			return new Image(
					new FileInputStream(
							new File(Directories.IMAGES, fileName)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}

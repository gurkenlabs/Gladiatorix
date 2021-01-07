package de.litigame;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Images {

	private static Map<String, BufferedImage> images = new HashMap<>();

	public static BufferedImage get(String key) {
		return images.get(key);
	}

	public static void loadImages(String imageFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(imageFile));

			String line = "";

			while (true) {
				line = br.readLine();

				if (line == null) break;

				String[] tokens = line.split(":");

				if (tokens.length != 2) continue;

				String key = tokens[0].trim();
				BufferedImage value = ImageIO.read(new File(tokens[1].trim()));

				images.put(key, value);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

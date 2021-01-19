package de.litigame;

import java.awt.Graphics2D;
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

	public static BufferedImage getCopy(String key) {
		BufferedImage copy = new BufferedImage(get(key).getWidth(), get(key).getHeight(), get(key).getType());
		Graphics2D g = copy.createGraphics();
		g.drawImage(get(key), 0, 0, null);
		g.dispose();
		return copy;
	}

	public static BufferedImage getRescaledCopy(BufferedImage image, double scalar) {
		BufferedImage scaled = new BufferedImage((int) (image.getWidth() * scalar), (int) (image.getHeight() * scalar),
				image.getType());
		Graphics2D g = scaled.createGraphics();
		g.scale(scalar, scalar);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return scaled;
	}

	public static BufferedImage getRescaledCopy(String key, double scalar) {
		return getRescaledCopy(get(key), scalar);
	}

	public static void init(File imageFile) {
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

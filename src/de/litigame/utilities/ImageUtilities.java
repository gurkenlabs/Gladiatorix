package de.litigame.utilities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import de.gurkenlabs.litiengine.resources.Resources;

public class ImageUtilities {

	private static final Map<String, String> paths = new HashMap<>();

	public static BufferedImage get(String key) {
		return Resources.images().get(key);
	}

	public static BufferedImage getCopy(String key) {
		BufferedImage copy = new BufferedImage(get(key).getWidth(), get(key).getHeight(), get(key).getType());
		Graphics2D g = copy.createGraphics();
		g.drawImage(get(key), 0, 0, null);
		g.dispose();
		return copy;
	}

	public static String getPath(String name) {
		return paths.get(name);
	}

	public static BufferedImage getRescaledCopy(BufferedImage image, double scalar) {
		BufferedImage scaled = new BufferedImage((int) (image.getWidth() * scalar), (int) (image.getHeight() * scalar), image.getType());
		Graphics2D g = scaled.createGraphics();
		g.scale(scalar, scalar);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return scaled;
	}

	public static BufferedImage getRescaledCopy(String key, double scalar) {
		return getRescaledCopy(get(key), scalar);
	}

	public static void init(InputStream imageFile) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(imageFile));
			String line = "";

			while (true) {
				line = br.readLine();
				if (line == null) break;
				String[] tokens = line.split(":");
				if (tokens.length != 2) continue;

				String name = tokens[0].trim();
				String path = tokens[1].trim();
				BufferedImage image = ImageIO.read(ClassLoader.getSystemResourceAsStream(path));

				paths.put(name, path);
				Resources.images().add(name, image);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ImageUtilities() {
	}
}

package de.litigame.items;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import de.litigame.weapons.Weapon;

public class Items {

	private static JSONArray items;

	public static Map<String, String> getInfo(String itemName) {
		for (Object obj : items) {
			JSONObject item = (JSONObject) obj;
			if (item.getString("item_name").equals(itemName)) {
				Map<String, String> map = new HashMap<>();
				for (String key : item.keySet()) {
					map.put(key, item.getString(key));
				}
				return map;
			}
		}
		return null;
	}

	public static Item getItem(String itemName) {
		Map<String, String> info = getInfo(itemName);
		switch (info.get("item_class")) {
		case "weapon":
			return new Weapon(info);
		}
		return null;
	}

	public static void init(File itemFile) {
		try {
			JSONObject obj = new JSONObject(new JSONTokener(new FileInputStream(itemFile)));
			items = obj.getJSONArray("items");
		} catch (JSONException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

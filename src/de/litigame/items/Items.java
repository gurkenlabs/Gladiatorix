package de.litigame.items;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Items {

	public static final Map<String, Map<String, String>> itemInfos = new HashMap<>();

	public static Item getItem(String itemName) {
		Map<String, String> info = itemInfos.get(itemName);
		switch (info.get("item_class")) {
		case "armor":
			return new Armor(info);
		case "weapon":
			return new Weapon(info);
		case "potion":
			return new Potion(info);
		case "shield":
			return new Shield(info);
		}
		return null;
	}

	public static void init(InputStream itemFile) {
		try {
			JSONObject data = new JSONObject(new JSONTokener(itemFile));
			itemInfos.clear();
			for (Object obj : data.getJSONArray("items")) {
				JSONObject item = (JSONObject) obj;
				String name = item.getString("item_name");
				Map<String, String> info = new HashMap<>();
				for (String key : item.keySet()) {
					info.put(key, item.getString(key));
				}
				itemInfos.put(name, info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

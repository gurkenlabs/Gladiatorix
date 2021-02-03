package de.litigame.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import de.litigame.items.Item;
import de.litigame.items.Items;

public class Shops {

	private static final Map<String, Shop> shops = new HashMap<>();

	public static Shop getShop(String shopName) {
		return shops.get(shopName);
	}

	public static void init(File shopFile) {
		try {
			JSONArray JSONShops = new JSONObject(new JSONTokener(new FileInputStream(shopFile))).getJSONArray("shops");
			for (Object shop : JSONShops) {
				JSONObject JSONShop = ((JSONObject) shop);
				List<ShopEntry> offers = new ArrayList<>();

				for (Object entry : JSONShop.getJSONArray("offers")) {
					JSONObject JSONEntry = (JSONObject) entry;
					Item item = Items.getItem(JSONEntry.getString("item_name"));

					int price = Integer.valueOf(JSONEntry.getString("price"));
					int required_level = Integer.valueOf(JSONEntry.getString("required_level"));
					boolean equippable = Boolean.valueOf(JSONEntry.getString("equippable"));

					ShopEntry shopEntry = new ShopEntry(item, price, required_level, equippable);

					offers.add(shopEntry);
				}

				shops.put(JSONShop.getString("shop_name"), new Shop(offers, new ArrayList<>()));
			}
		} catch (JSONException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Shops() {
	}
}

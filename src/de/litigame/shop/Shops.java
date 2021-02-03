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

import de.litigame.items.Items;

public class Shops {
	private final Map<String, Shop> map = new HashMap<>();

	public void init(File shopFile) {
		try {
			final JSONArray shops = new JSONObject(new JSONTokener(new FileInputStream(shopFile)))
					.getJSONArray("shops");
			for (final Object objBig : shops) {
				final JSONObject shop = (JSONObject) objBig;
				final List<ShopEntry> offers = new ArrayList<>();
				for (final Object objMedium : shop.getJSONArray("offers")) {
					final JSONObject entry = (JSONObject) objMedium;
					final ShopEntry entrySmall = new ShopEntry(Items.getItem(entry.getString("item_name")),
							Integer.valueOf(entry.getString("price")), Integer.valueOf(entry.getString("reqLvl")),
							Boolean.valueOf(entry.getString("equippable")));
					offers.add(entrySmall);
				}
				map.put(shop.getString("shop_name"), new Shop(offers, new ArrayList<>()));
			}
		} catch (JSONException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Shop getShop(String shopName) {
		return map.get(shopName);
	}
}

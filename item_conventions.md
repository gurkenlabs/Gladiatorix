## Items
- items are saved in a json-file, which has one json-object with the key "items" and an array of item-data value as value
- every value within the item-data is stored as a String

## Item
+ `item_class`: "weapon"
+ `item_image`: a key value of [images](image_conventions.md)
+ `item_name`: name of the item

## Weapon
+ `weapon_cooldown`: how long before re-use
+ `weapon_duration`: only range weapon, velocity projectile
+ `weapon_impact`: radius of attacked area
+ `weapon_impactAngle`: only melee weapons, how far to side
+ `weapon_multiTarget`: "true" or "false"
+ `weapon_range`: only for range weapons, how far
+ `weapon_type`: "melee" or "range"
+ `weapon_value`: strength of the weapon

## Armor
+ `armor_buff`: increase in player HP
+ `armor_skin`: which player sprite
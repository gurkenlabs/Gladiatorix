## Shops
- stored in a json-file, containing one json-object with key "shops" and array as value
- every key is stored as a String
- values may be array (`"offers"`), else String
- following structure:

  * "shops"
    * "shop_name"
    * "offers"
      * "item_name"
      * "price"
      * "required_level"
      * "equippable"

## Variables

| variable         | value-type | description                                               |
| ---------------- | ---------- | --------------------------------------------------------- |
| `shops`          | JSONArray  | contains all shop-JSONObjects                             |
| `shop_name`      | String     | identifier for map object property                        |
| `offers`         | JSONArray  | contains all offer-JSONObjects                            |
| `item_name`      | String     | item-identifier from [items](item_conventions.md)     |
| `price`          | Integer    | amount of coins needed to buy this item                   |
| `required_level` | Integer    | level needed to buy this item                             |
| `equippable`     | Boolean    | determines whether item cannot be obtained multiple times |
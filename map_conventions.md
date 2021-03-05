## Layers
1. props
2. collision boxes
3. map areas
4. triggers
5. spawn points

## Triggers
| tag          | description                            | required properties                         | optional properties            |
| ------------ | -------------------------------------- | ------------------------------------------- | ------------------------------ |
| `deadly`     | kills touching entity                  |                                             |                                |
| `portal`     | teleports player to different map      | `toMap`: String; `toPos`: Double, Double    |                                |
| `portalback` | the portal in arena to come back       | `toMap`: String; `toPos`: Double, Double    |                                |
| `shop`       | shop position to buy items             | `shopName`: String                          |                                |
| `zoom`       | zooms camera when touching             | `zoomValue`: Float                          | `zoomDuration`: Integer        |
| `dialogue`   | displays a text box inside the world   | `time`: Integer                             | `message_<INDEX>`: String, ... |

## Collisionboxes
| tag              | description                                           | required properties                         | required attributes       |
| ---------------- | ----------------------------------------------------- | ------------------------------------------- | ------------------------- |
| `barrier`        | allows enemies to pass, blocks player                 |                                             | `collision type`: DYNAMIC |
| `enemyspawndata` | holds the amount of max waves and delay between waves | `waveCount`: Integer; `waveDelay`: Integer  |                           |

## Spawnpoints
| tag          | description                                   | required properties                         | optional properties          |
| ------------ | --------------------------------------------- | ------------------------------------------- | ---------------------------- |
| `enemyspawn` | spawns enemy waves, requires `enemyspawndata` |                                             | `wave_<INDEX>`: Integer, ... |

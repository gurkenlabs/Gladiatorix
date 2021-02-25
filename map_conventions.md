## Layers
1. props
2. collision boxes
3. map areas
4. triggers
5. spawn points

## Triggers
| tag        | description                            | required properties                                  | optional properties        |
| ---------- | -------------------------------------- | ---------------------------------------------------- | -------------------------- |
| `deadly`   | kills touching entity                  |                                                      |                            |
| `portal`   | teleports player to different map      | `toMap`: String; `toPos`: Double, Double             |                            |
| `zoom`     | zooms camera when touching             | `zoomValue`: Float                                   | `zoomDuration`: Integer    |
| `shop`     | opens shop interface on trigger        | `shopName`: String from [shops](shop_conventions.md) |                            |
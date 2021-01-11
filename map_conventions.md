## Layers
1. prop | collision box
2. trigger

## Triggers
| tag        | description                            | required properties                         | optional properties        |
| ---------- | -------------------------------------- | ------------------------------------------- | -------------------------- |
| `deadly`   | kills touching Entity                  |                                             |                            |
| `portal`   | teleports player to different map      | `toMap`: String; `toPos`: Double, Double    |                            |
| `zoom`     | zooms camera when touching             | `zoomValue`: Float                          | `zoomDuration`: Integer    |

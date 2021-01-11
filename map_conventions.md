## Layers
1. prop | collision box
2. trigger

## Triggers
| tag        | description                            | required properties                         | optional properties        |
| ---------- | -------------------------------------- | ------------------------------------------- | -------------------------- |
| `deadly`   | kills touching Entity                  |                                             |                            |
| `portal`   | teleports player to different map      | `toMap`:<map_name>, `toPos`:<x,y>           |                            |
| `zoom`     | zooms camera when touching             | `zoomValue`:<value>                         | `zoomDuration`:<duration>  |

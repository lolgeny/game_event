# Game event
Simple mod for interfacing with game events in datapacks.
# Usage
Game events go into the `game_events` folder in datapacks. They have a structure like so:
```json
{
  "event": "<event_id>",
  "function": "<function_id>",
  "predicate": [
    {
      "condition": "<predicate>",
      "...": ""
    },
    "..."
  ]
}
```
The predicate tag is optional and specifies a list of predicates to check before running the function. Note `this` entity is set (if there is one for this event) and the block position is set.
The event ids can be found on [the wiki](https://minecraft.gamepedia.com/Sculk_Sensor#Vibration_frequencies). This will run your function when a game event happens, `at` the location of the event.

# Game event
Simple mod for interfacing with game events in datapacks.
# Usage
Game events go into the `game_events` folder in datapacks. They have a structure like so:
```json
{
  "event": "<event_id>",
  "function": "<function_id>"
}
```
The event ids can be found on [the wiki](https://minecraft.gamepedia.com/Sculk_Sensor#Vibration_frequencies). This will run your function when a game event happens, `at` the location of the event.

# Future
- Will try to add more syntax, probably a block predicate but this is not immediately necessary

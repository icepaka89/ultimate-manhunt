# Ultimate Manhunt Plugin
**This project is still a work in progress**
### Overview

A new take on the popular manhunt/assassin minecraft game mode.

### Command Usage

* `/manhunt:assassin <player>` - Add a player to the assassins group
* `/manhunt:speedrunner <>player` - Add a player to the speedrunner group
* `/manhunt:groups` - Display a list of players in each group
* `/manhunt:randomize-spawn` - Randomizes the spawn point
* `/manhunt:starting-distance` - Sets the starting distance between the assassin and speedrunner
* `/manhunt:countdown-time` - Sets the length of the countdown timer when starting
* `/manhunt:start` - Starts game, begins the countdown timer

### Project Directory Structure

```
src /
test-server/
```

The `src/` folder contains java source files for the plugin.

The `test-server/` folder contains a test bukkit server that IntelliJ has been configured to deploy to and start in the default run configuration.
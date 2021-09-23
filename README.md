# Ultimate Manhunt Plugin
*Version 1.0*
### Overview

The popular manhunt/assassin minecraft game mode, with some extra features!

<!-- 
TODO:
* Maybe use action bar for distance reporting?
* Make it so weather is always clear (toggle)
* Command to save current time so you can pick up later
* Improve randomize-spawn command

** Reset Health and food every time manhunt starts
** Ability to stop manhunt
** When assassin dies during manhunt, give them the compass again
** BUG: If assassin leaves the game then comes back, the compass is broken until they're re-added to assassins group
** Add 1/2 time, 1/4 time, and 10sec warnings to countdown
** Add optional game timer?
-->

### Commands

* `/assassin <player>` - Add a player to the assassins group
* `/speedrunner <player>` - Add a player to the speedrunner group
* `/manhunt-groups` - Display a list of players in each group
* `/reset-manhunt-groups` - Clears all groups 
* `/randomize-spawn` - Randomizes the spawn point
* `/toggle-freeze-assassin` - Enable/Disable freezing the assassin if a speedrunner sees them
* `/toggle-distance-reporting` - Enable/Disable printing the distance from each assassin to the nearest speedrunner periodically  
* `/starting-distance` - Sets the starting distance between the assassin and speedrunner
* `/debuff-assassin <percentage>` - Debuffs assassins by reducing their damage by the given percentage
* `/countdown-time` - Sets the length of the countdown timer when starting
* `/start-manhunt` - Starts game, begins the countdown timer

### Project Directory Structure

```
src /
test-server/
```

The `src/` folder contains java source files for the plugin.

The `test-server/` folder contains a test bukkit server that IntelliJ can be configured to deploy to and start in the default run configuration.

### Setting Up A Development Environment

This plugin was developed using JetBrains IntelliJ, and targets JDK 14. You can follow these instructions to set up a run configuration with IntelliJ to build the plugin, deploy it to the test server, and run the test server. 

The first thing you'll need to do is create an artifact that compiles the plugin JAR to the `~/test-server/plugins` directory:

* Go to "File > Project Structure > Artifacts"
* Add a new Artifact from the template "Other"
* Set "Output Directory" to the plugins directory of the testing server
* Under "Available Elements", right click the entry "'ProjectName' compile output" and then click "Pack into /ProjectName.jar"
* **If your project has no MANIFEST.MF file:**
  * Select the new jar file entry under "output root" and click "Create Manifest..."
  * Navigate to the directory the project's java source files are kept (e.g. src/main/java/)

Next, you need to create a Run Configuration that executes the Spigot server JAR:

* Go to "Run > Edit Configurations..."
* Add a new "JAR Application" configuration
* Set "Path to JAR" to the testing server's CraftBukkit JAR (`craftbukkit-1.16.5.jar`)
* Set "Working directory" to the directory of the testing server
* Under "Before launch", add a new "Build Artifacts" task
* Checkmark the artifact created above on the list


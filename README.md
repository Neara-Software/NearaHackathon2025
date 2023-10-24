# Example Robocode Tank Royale Bot
This repository has a skeleton of an example bot written in Java, with some basic logic and heuristics provided.
It also has everything necessary to run as a host for a Robocode Tank Royale competition. So other bots can be packaged and dropped into the `bots` folder and it'll be picked up.

Ensure that you are familiar with the rules and anatomy of a tank. They're important to understanding the limitations of your tank.
https://robocode-dev.github.io/tank-royale/articles/anatomy.html

## Default competition rules
- Game type: Classic
- Arena size: 800x600
- Gun cooling rate: 0.1
- Number of rounds: 10
- Ready timeout (μs): 1,000,000
- Turn timeout (μs): 30,000

## Running as host
Unzip the included compiled sample bots
```bash
unzip sample-bots-java-0.19.2.zip -d $YourFolder
```

Run the host application
```bash
java -jar robocode-tankroyale-gui-0.19.2.jar
```

1. Go to `Config->Bot root directories` and add the $YourFolder
2. Start the server
3. Start game and set the mode to "classic", load all the bots you want to run and click start


## Building a bot and submitting to host
- Ensure that you set compatibility mode to JDK 18. You'll need to produce a jar file in the final deliverable
- Ensure that your bot includes a .json file with the same name as the jar. Example [here](./src/main/kotlin/org/example/BTreeBot.json). 
- Ensure that you have a .sh file with the command that would execute the jar file. See example [here](./BTreeBot/BTreeBot.sh)

Full instructions for the JVM is available in the official docs: https://robocode-dev.github.io/tank-royale/tutorial/jvm/my-first-bot-for-jvm.html

The easiest way to develop a bot would be to use the skeleton we have provided in this repo.
1. In folder 'src/main/java/org/example', add logic to 'BTreeBot.java'.
2. Compile:
```
./gradlew packageBot
```
The compiled BTreeBot.jar will be inside the BTreeBot folder. 

3. Send that BTreeBot folder to the host.
4. To test your bot, following the steps in Running as a host. And copy BTreeBot folder in $YourFolder.

## Competition agenda 
We'll run a series of rounds and use the scoring system built into Tank Royale to determine the winner. The general agenda would be:

### Introduction - ~20 mins
- Who Neara is and what we do
- What the competition is about and prizes that will be won
- The people who are helping from Neara's side

### Competition details - ~20 mins
- Going over this repository and how a game is run
- What is expected of your teams
- How scoring is done

### Breakout into teams - ~60 mins
- Team assignment and intro to each other 
- Formulate a strategy
- Read suggested documentation
- Build build build

### Test run - ~15 mins
- 1 hour into the team breakout, we'll get everyone's bot shipped and make sure everything's working

### Build build build - ~60 mins

### Round 1 - Feeling out the other bots
- Score not counted, just to get a feel for how people are building their bots

### Last Day
- Final preparations and we run the actual competition
- Judging, scoring and winner announcements

## Scoring details

Scoring will be done based on placement during the final competition, based on number of rounds won. In the case of a
tie, tiebreaker rounds will be run. Assume FFA style combat (multiple bots pitted against each other in a single round),
instead of 1 vs 1 rounds.

## Requirements and references
- JDK 18 for this repo. The bot should be compiled to JDK 18 for the final jar.
- https://robocode-dev.github.io/tank-royale/articles/anatomy.html
- https://github.com/robocode-dev/tank-royale
- https://robowiki.net/wiki/Robocode/Game_Physics

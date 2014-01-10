PlugBot [![Build Status](https://travis-ci.org/LordAkkarin/PlugBot.png?branch=master)](https://travis-ci.org/LordAkkarin/PlugBot)
===============
PlugBot is a bot framework based on Selenium which is able to interact with the
service plug.dj. It collects events issued by the service and forwards them to
it's Java based plugin API.

The library features multiple interfaces for easy access to the service with plugins
or external services and can be included into other applications to add additional
features to it.

Currently already implemented (or planned) plugins are:
* Length Limiter - Limits the maximum length of a video in the room
* History Check - Automatically checks videos against the history of the room to make
sure songs won't be played over and over again.
* Spam Prevention - Prevents users from massively spamming a room or overusing emojis
or /me s.
* Track Blacklist - Allows to blacklist certain songs.
* AFK Check - Automatically removes users from the DJ booth when they're either trying to play
a song which has been played already in the past X minutes or when they are completely inactive
for a specific amount of time (not interacting with the service at all)
* Report - Allows users to report certain problems in the room (e.g. wrong track genres etc.) and
prove them with a screenshot and exact details of the track currently played (including the user which
has been playing it)
* Badword Protection - Allows to ban certain words
* Web Interface - Allows easy administration of the bot and includes a copy of the chat log (unlimited)

Directory Structure
-------------------
The bot has been split into two directories: `Plugins` and `Bot`.
* `Bot` contains the main application (and will be compiled first when executing maven in the main
directory).
* `Plugins` contains all 1st- (and selected 3rd-)party plugins.

Compiling
---------
You need to have Maven installed (http://maven.apache.org). Once installed,
simply run:

	mvn clean install

Maven will automatically download dependencies for you. Note: For that to work,
be sure to add Maven to your "PATH".

Contributing
------------

We happily accept contributions. The best way to do this is to fork PlugBot
on GitHub, add your changes, and then submit a pull request. We'll look at it,
make comments, and merge it into the repository if everything works out.

By submitting code, you agree to place to license your code under the 
GNU Lesser General Public License v3.
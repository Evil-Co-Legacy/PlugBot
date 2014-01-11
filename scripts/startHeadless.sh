################################
# PlugBot Headless Script
# for *NIX
################################
# Requirements:
#	- Xvfb
#	- Screen
#	- Firefox or chrome
#
# Additionally these two
# files have to be copied over
# to the binary directory and
# the PlugBot-${version}.jar
# has to be renamed to
# "PlugBot.jar" (without quotes).
###############################

# Start screen with commands pre-setup
screen -c headless.screenrc
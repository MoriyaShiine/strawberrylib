------------------------------------------------------
Version 1.21.10-r2
------------------------------------------------------
- fix after damage including death event not taking final modify damage taken event changes into account

------------------------------------------------------
Version 1.21.10-r1
------------------------------------------------------
- update to 1.21.10

------------------------------------------------------
Version 1.21.9-r1
------------------------------------------------------
- update to 1.21.9

------------------------------------------------------
Version 1.21.8-r2
------------------------------------------------------
- fix another model replacement dupe bug

------------------------------------------------------
Version 1.21.8-r1
------------------------------------------------------
- update to 1.21.8
- add after damage including death event
- refactor a bunch of events
- add extra addParticles overload in SLibUtils
- model replacement jumping state is no longer set to true when the player is flying
- model replacements now replace your sounds
- fix model replacement entity duping
- merge https://github.com/MoriyaShiine/strawberrylib/pull/1
  - adds Ukrainian translation. Thanks StarmanMine142!

------------------------------------------------------
Version 1.21.7-r9
------------------------------------------------------
- fix outline event returning when the state is true or false instead of if the data isn't empty

------------------------------------------------------
Version 1.21.7-r8
------------------------------------------------------
- allow outlines to change state without changing color in outline entity event. oops lol

------------------------------------------------------
Version 1.21.7-r7
------------------------------------------------------
- optimize entity outline event. this is a breaking change, so update your mods if you use it
- add riptide check to isGroundedOrAirborne

------------------------------------------------------
Version 1.21.7-r6
------------------------------------------------------
- cleaner current attack cooldown api
- modify critical status event now has a priority
- fix prevent equipment usage checks not updating held item attributes properly

------------------------------------------------------
Version 1.21.7-r5
------------------------------------------------------
- add currentAttackCooldown to utils class
- condense setModelReplacement overloads
- fix rotation jitteryness with model replacements

------------------------------------------------------
Version 1.21.7-r4
------------------------------------------------------
- add untrimmable_armor item tag
- add more item registry helpers

------------------------------------------------------
Version 1.21.7-r3
------------------------------------------------------
- potion registry helper now returns a registry entry instead of the value

------------------------------------------------------
Version 1.21.7-r2
------------------------------------------------------
- add disable hud bar event
- build with loom 1.11

------------------------------------------------------
Version 1.21.7-r1
------------------------------------------------------
- update to 1.21.7

------------------------------------------------------
Version 1.21.6-r1
------------------------------------------------------
- update to 1.21.6
- optimize prevent equipment usage event (if it needs to be activated immediately, there's a trigger method now)
- replace heart textures event now has a priority

------------------------------------------------------
Version 1.21.5-r5
------------------------------------------------------
- add consume effect type registry helper

------------------------------------------------------
Version 1.21.5-r4
------------------------------------------------------
- update dependencies
- anchored particles can now be added on servers
- expose fields in anchored particle class
- model replacement now has null fallback if player entity type is supplied
- model replacement no longer crashes when trying to replace player model with a nonliving entity

------------------------------------------------------
Version 1.21.5-r3
------------------------------------------------------
- add loot function type registry helper

------------------------------------------------------
Version 1.21.5-r2
------------------------------------------------------
- add extra modifiers helper

------------------------------------------------------
Version 1.21.5-r1
------------------------------------------------------
- initial release
![vanilla](https://img.shields.io/badge/DESIGNED%20FOR-VANILLA+%20SERVERS-green?style=for-the-badge)

![RetrosLodestones](https://i.imgur.com/3g6y0vM.png)
# Retro's Lodestones Reloaded
**Supported Versions:**

![1.18](https://img.shields.io/badge/%201.18-SUPPORTED-green?style=for-the-badge)

Retro's Lodestones adds teleport functionality to lodestones in Minecraft.
Players can bind a compass to a lodestone then right click the compass on a respawn
anchor, consuming a experience, to teleport to the bound lodestone.

![gif](https://i.imgur.com/bUpsHXA.gif)

## Vanilla Focused

The plugin is built to give a vanilla+ feel without too many in-your-face features.

Cost can be configured to also burn XP and refuse teleportation if the player does
not have enough experience. Notifications are shown via the action bar with the
addition of a sound to simulate a vanilla experience.

If you prefer a less vanilla experience you can disable the need to activate the
teleport at a respawn anchor and instead shift right-click the bound compass to
teleport instead.

## In-Game Usage
#### Binding the Compass
To create a new Lodestone compass right click the compass on the lodestone (or whatever block
you have configured as the lodestone in <1.16).

#### Teleporting
Once bound you can right click the compass on another lodestone to teleport to the original lodestone.
This costs experience that multiplies the further you wish to travel. Note that
teleporting cross-dimension has a fixed cost.

#### Tracking
You can also track the lodestone by shift right-clicking with the compass in your hand.
 This will point the compass to the lodestone it's bound to.

#### Free Space & Obstructions
If the lodestone does not have free space above it, or is destroyed you will not
be able to teleport to that location anymore.

## Permissions
Players will need the `use` node to use the plugin's core functionality.
```
retroslodestones.use
```

## Configuration

### Require Respawn Anchor
You can toggle the need for a respawn anchor and instead allow the user to just
shift right-click the bound compass to teleport.
```yml
require-respawn-anchor: true
```

### Expereince Cost
Experience cost is calculated by experience-cost * (blocks-traveled / 1,000).
```yml
experience-cost: 100
```

### Free-space Scan Range
The plugin will search for a free block to spawn in within this radius from the
target lodestone.
```yml
freespace-scan-range: 15
```

### Allow cross-dimensional teleport
If you'd like to allow the players to teleport between dimensions enable this.
```yml
cross-dimension: true
```

### Cross-dimensional teleport cost
The amount in glowstone charges to deduct from the respawn anchor for
cross-dimension teleportation.
```yml
cross-dimension-cost: 4
```

### Owner-only teleport
If disabled players will be allowed to trade lodestone compasses and teleport to
lodestones, you should disable this if you only want the original owners
of the compass to use the teleport feature.
```yml
owner-only-teleport: false
```

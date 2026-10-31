# MCDuels Rank Tagger

A Fabric client-side mod that displays players' [MCDuels](https://mcduels.com) ranks above their nametags in-game.

[Original mod](https://github.com/baldzikaa/MCDuelsRankTaggerFabric) by [baldzikaa](https://github.com/baldzikaa).

The prefix shows each player's kit icon and their highest tier so you can tell who you're up against at a glance.

## Features

- Floating nametag prefix with the MCDuels rank badge and tier-coloured kit icon, the same art the MCDuels resource pack draws on its own nametags (including the Spear Mace icons)
- Picks the kit with the highest rank automatically across all 9 kits (Axe, Crystal, Mace, NethOp, Pot, SMP, Spear Mace, Sword, UHC)
- Steps aside on MCDuels when you accepted the server resource pack, since the server already shows ranks there; decline the pack and the tagger stays on
- Two-tier cache (in-memory + on-disk) — survives client restarts
- Async API lookups — rendering is never blocked on I/O
- Configurable via [ModMenu](https://modrinth.com/mod/modmenu) + toggle keybind

## Requirements

- Minecraft **1.21.2 – 26.3** (pick the jar for your version, see below)
- **Java 21** (1.21.x) or **Java 25** (26.x)
- [Fabric Loader](https://fabricmc.net/) 0.16.0 or newer
- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Cloth Config API](https://modrinth.com/mod/cloth-config)
- [ModMenu](https://modrinth.com/mod/modmenu) (optional — for the in-game settings screen)

## Installation

1. Install Fabric Loader for your Minecraft version.
2. Drop Fabric API, Cloth Config, and the matching `mcduels-tagger-<version>+<minecraft>.jar` into your `mods/` folder.
3. Launch Minecraft.

| Minecraft          | Jar                                 |
|--------------------|-------------------------------------|
| 1.21.2 – 1.21.3    | `mcduels-tagger-1.1+1.21.2.jar`     |
| 1.21.4             | `mcduels-tagger-1.1+1.21.4.jar`     |
| 1.21.5             | `mcduels-tagger-1.1+1.21.5.jar`     |
| 1.21.6 – 1.21.8    | `mcduels-tagger-1.1+1.21.6.jar`     |
| 1.21.9 – 1.21.10   | `mcduels-tagger-1.1+1.21.9.jar`     |
| 1.21.11            | `mcduels-tagger-1.1+1.21.11.jar`    |
| 26.1 – 26.1.2      | `mcduels-tagger-1.1+26.1.jar`       |
| 26.2               | `mcduels-tagger-1.1+26.2.jar`       |
| 26.3               | `mcduels-tagger-1.1+26.3.jar`       |

## Usage

- Join any server. Player nametags will show their MCDuels rank prefix automatically.
- On MCDuels (`*.mcduels.com`, or any server that sends the MCDuels pack) the tagger turns itself off while the MCDuels pack is accepted, and back on if you decline it or it fails to load.
- Press the toggle keybind (configurable in **Options → Controls**) to show/hide tags.
- Open **Mods → MCDuels Rank Tagger → Config** (via ModMenu) to:
  - Choose which kits count toward the "highest rank" calculation
  - Toggle showing tags on yourself / on sneaking players

### Debug command

`/mcduelstagger lookup <name>` — fetch a player's rank manually (cache-first, falls back to a live API call).

## Building from source

Gradle must run on **Java 25** (Fabric Loom 1.19 needs it); the 1.21.x targets still compile for Java 21.

```sh
./gradlew build                       # every version
./gradlew :versions:1.21.11:build     # one version
```

Jars land in `versions/<minecraft>/build/libs/`.

### Layout

```
common/                  pure-Java logic + tests (API client, cache, ranks, MCDuels pack detection)
fabric/src/main/         Minecraft code shared by every version (Mojang mappings)
fabric/src/variants/     small per-version shims where Minecraft or Fabric API changed
versions/<minecraft>/    one Gradle project per target: dependency pins + which variants it uses
gradle/fabric-target.gradle  build logic applied to every target
```

Every target is compiled against official Mojang names (1.21.x via Loom's Mojang mappings,
26.x is unobfuscated), so almost all code is shared. A new Minecraft version usually only needs a
`versions/<minecraft>/gradle.properties` file; when an API moves, add a variant directory with the
new shim and list it in that file's `variants`.

## License

[MIT](LICENSE)

## Credits
- Original source by [baldzika](https://github.com/baldzikaa)
- Forked and made official by MCDuels

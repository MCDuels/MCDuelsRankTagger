# Changelog

## 1.1 — 2026-10-30
- **Minecraft 1.21.2 – 26.3 support.** One build produces a jar per version range (1.21.2, 1.21.4, 1.21.5, 1.21.6, 1.21.9, 1.21.11, 26.1, 26.2, 26.3); see the README table.
- Moved to official Mojang mappings and split the code into `common/` (pure Java, tested), shared Minecraft code, and small per-version variants. Fabric Loom 1.19.3, Gradle 9.8.2.
- Nametag prefix now uses the MCDuels resource pack art: rank badge plus tier-coloured kit icon, including the new Spear Mace icons. Crystal uses the pack's "vanilla" set and Mace the SMP set, matching the server.
- The tagger switches off on MCDuels while the MCDuels resource pack is accepted (the server already draws ranks), and stays on if the pack is declined or fails to load.
- Mixins now fail loudly (`defaultRequire: 1`) since every jar is built for its exact version range; the keybind no longer needs the `LinkageError` fallback.

## 0.2.0 — 2026-05-07
- **Minecraft 1.21.11 support.** Ported the mixin to the new generic `PlayerEntityRenderer` (target signature `updateRenderState(PlayerLikeEntity, PlayerEntityRenderState, F)V`); added an `instanceof PlayerEntity` guard so mannequins / display avatars are skipped cleanly.
- Updated for 1.21.11 yarn API renames: `GameProfile.name()` / `id()` (now a record), `KeyBinding.Category.MISC` (categories became records), `Style.withFont(StyleSpriteSource.Font)` (fonts wrapped in a sprite source).
- Build toolchain bumped: Gradle wrapper 8.10 → 8.14, Fabric Loom 1.8 → 1.13, fabric-api → 0.141.3+1.21.11, cloth-config → 21.11.153.
- Adopted MCDuels rank labels (`HD`, `D`, `HDi`, `Di`, `HG`, `G`, `HS`, `S`, `HI`, `I`) with refreshed palette: dueler reds, diamond cyan, gold, silver greys, warm-iron browns. Player nametag color is preserved — only the rank label is tinted.
- Code-review fixes: `McDuelsClient` is now `AutoCloseable`, distinguishes 4xx (permanent) from 5xx (transient); `PlayerRankCache.putFailed` uses atomic `compute`; cache snapshot is immutable; shutdown save runs off the render thread; keybind toggle no longer blocks on disk I/O; logging uses SLF4J consistently.
- Renamed `Rank` enum identifiers (`HT1`–`LT5` → `HD`/`D`/`HDI`/`DI`/`HG`/`G`/`HS`/`S`/`HI`/`I`) for clarity.

## 0.1.0 — 2026-05-07
- Initial release: floating-nametag rank prefix for MCDuels players, with ModMenu config and toggle keybind.
- Supports 9 kits: Axe, Crystal PvP, Mace, NethOp, Pot, SMP, Spear Mace, Sword, UHC.
- Two-tier cache (in-memory + on-disk) keyed by UUID — survives restarts, uses ≤1 API call per player per hour.
- HT/LT scheme (HT1 = `high_dueler` … LT5 = `iron`) with the rank-color palette from the design spec.
- Async, non-blocking lookup against `https://mcduels.com/public/player/{id}` — render path never blocks on I/O.

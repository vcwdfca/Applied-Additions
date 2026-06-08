# Applied Additions Wireless Connector Port Plan

## Summary
- Initialize the root project as a 1.12.2/CleanroomMC addon for `Applied-Energistics-2-Supergiant`, not as a NeoForge 1.21.1 mod.
- Port ExtendedAE’s full Wireless Connector feature set: `wireless_connect`, `wireless_hub`, `wireless_tool`, and `wireless_connector_upgrade`.
- Preserve ExtendedAE behavior and UI as closely as the Supergiant/1.12.2 GUI and networking APIs allow.

## Key Changes
- Project metadata:
  - Set `mod_id = ae2additions`, `root_package = com.formlesslab`, `mod_name = Applied Additions`.
  - Move template classes from `com.example.modid` to `com.formlesslab.ae2additions`.
  - Replace `ExampleMod` with an addon entrypoint that registers blocks, items, tile entities, GUI handler, recipes, config, and client models.
  - Disable or empty the template access transformer unless compile errors prove one is required.

- Wireless content:
  - Add blocks `ae2additions:wireless_connect` and `ae2additions:wireless_hub`.
  - Add items `ae2additions:wireless_tool` and `ae2additions:wireless_connector_upgrade`.
  - Port Connector/Hub network logic from ExtendedAE using Supergiant’s 1.12.2 APIs:
    - `AENetworkedTile`
    - `GridHelper.createConnection`
    - `ConnectionWrapper`
    - `Locatables`-style frequency lookup
    - `UpgradeInventories.forMachine`
    - `Upgrades.add`
  - Preserve defaults: max range `1000`, power multiplier `1.0`, Hub max ports `8`, Connector/Hub upgrade slots `4`, energy-card discount behavior.

- UI and resources:
  - Add containers and GUIs using Supergiant’s `AEBaseContainer`, `UpgradeableContainer`, `AEBaseGui`, and `GuiStyleManager`.
  - Port style JSON and textures for:
    - `/screens/wireless_connector.json`
    - `/screens/wireless_hub.json`
    - `guis/wireless_connector.png`
    - `guis/wireless_hub.png`
  - Recreate the 1.21 `WorldDisplay`, highlight button, status icon, and disconnect controls in 1.12.2 GUI terms.
  - Add English and Chinese lang entries under `assets/ae2additions/lang/*.lang`, preserving user-facing text from ExtendedAE.

- Compatibility adaptation:
  - Replace NeoForge data components with item NBT for `wireless_tool` locator data.
  - Replace `GlobalPos` with dimension id + `BlockPos` stored in NBT.
  - Replace `BlockEntity`, `Level`, `Direction`, `CompoundTag`, and Component APIs with 1.12.2 `TileEntity`, `World`, `EnumFacing`, `NBTTagCompound`, and text components.
  - Omit Jade/GuideME integration in the first pass unless a 1.12.2 equivalent is already present.

## Public Interfaces
- Registry names:
  - `ae2additions:wireless_connect`
  - `ae2additions:wireless_hub`
  - `ae2additions:wireless_tool`
  - `ae2additions:wireless_connector_upgrade`
- Config keys:
  - `device.wireless_connector_max_range`, default `1000.0`
  - `device.wireless_connector_power_multiplier`, default `1.0`
- GUI ids:
  - Add addon GUI entries for Wireless Connector and Wireless Hub, routed through the addon’s own `IGuiHandler`.

## Test Plan
- Run `.\gradlew.bat compileJava -x test`.
- In a dev client:
  - Place two Wireless Connectors on separate AE networks, link with `wireless_tool`, confirm the networks connect.
  - Confirm same-block, cross-dimension, missing target, and out-of-range links fail with messages.
  - Confirm breaking/unloading either side destroys the wireless grid connection.
  - Confirm Connector GUI shows status, power usage, channel usage, and remote position.
  - Confirm Hub can allocate up to 8 ports, disconnect individual ports, and reject a 9th active link.
  - Confirm energy cards reduce power usage and are dropped when the block is broken.
  - Confirm resources, lang names, item models, blockstates, and recipes load.

## Assumptions
- The addon targets the root project’s current 1.12.2/CleanroomMC toolchain.
- The local `lib/AppliedEnergistics2-Supergiant-0.0.0-dev.jar` remains the compile dependency.
- ExtendedAE registry names are preserved, only the namespace changes to `ae2additions`.
- Full Wireless Hub support is included.

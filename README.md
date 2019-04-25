# ⛏️QuakeCraft⛏️
## Description
QuakeCraft is a minecraft plugin for a separate server. It is a simple and fun shooter with mechanics of armor, repairing health, ammunition and different types of weapons.
The main difference of this plugin from the other ones is that it has more functionality than analogues.
## Commands
After execution of command, blocks will appear below the user.
### Spawn block
This block is used as a place for respawn after death.
```
/newspawnblock [name of block]
```
#### Example
``
/newspawnblock testBlock1
``

### Jump block
This block is designed to throw the player in a defined direction and with a defined power.
```
/newjumpblock [type] [power] [name of block] 
```
| Type        | Description           |
| ------------- |:-------------:|
| **_directed_**      | The direction of the flight is determined by direction the player is looking at while using the command |
| **_free_**      | The direction of the flight is determined by direction the player is looking at while stepping on the block      |
**power** - is the power of the player's throw.The range is from  **_1.1_** to **_5_**.
#### Example
``
/newjumpblock directed 2.3 testBlock2 
``
OR
``
/newjumpblock free 4.1 testBlock22
``
### Item block
This block is assigned to spawn items such as armor, weapons, healing items and ammunition.
```
/newitemblock [item type] [item name or other] [extra options] [number of tick] [name of block]
```
| Item type  | Item name or other | Extra options |
|------------|:---------------:|---------------:|
| **_weapon_**      | **_Blaster,Bazooka,ShoutGun,Excalibur_** | None |
| **_health_**      |   **_huge,small,middle_**       |   None |
| **_ammo_**   | The name of the weapon from the first row     |    Number of ammo |
| **_armor_**	      |  **_Bashmachki,NaGrudinin,Nabaldazhnik,40griven_**        |    None |

Arguments of the second row for armor and weapons can be changed in the config.yml file.
#### Example
``
/newitemblock weapon Blaster 30 testBlock3
``
OR
``
/newitemblock health huge 27 testBlock4
``

OR

``
/newitemblock armor Bashmachki 50 testBlock5
``
OR
``
/newitemblock ammo Blaster 10 47 testBlock5
``

### List of blocks
List of created blocks.
```
/show [type] [page]
```
| Type  | Description |
| :------------|------------:|
| **_itemblocks_**     |Show a list of item's blocks |
| **_spawnblocks_**      |Show a list of spawn's blocks|
| **_jumpblocks_** |Show a list of jump's blocks|

**page** - number or just leave it blank. The argument determines which page to show.
#### Example
``
/show itemblocks
``
OR
``
/show itemblocks 0
``
### Remove block
Delete a block of a particular type by name.
```
/remove [type] [name]
```
**types** - **_jumpblock_**, **_spawnblock_**, **_itemblock_**.
#### Example
``
/remove jumpblock testBlock22
``

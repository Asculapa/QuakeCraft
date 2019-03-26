package com.quake;

import com.quake.block.*;
import com.quake.item.Armor;
import com.quake.item.Health;
import com.quake.item.Item;
import com.quake.listener.GameListener;
import com.quake.listener.ItemListener;
import com.quake.item.Weapon;
import com.quake.сonfig.ReadConfig;
import com.quake.сonfig.WriteConfig;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;


public class Main extends JavaPlugin implements Listener, CommandExecutor {

    public static final Logger log = Logger.getLogger("Minecraft");
    public static World world;
    public static Main main;
    private ArrayList<PlayerSpawnBlock> playerSpawnBlocks;
    private ArrayList<JumpBlock> jumpBlocks;
    private ArrayList<ItemSpawnBlock> itemSpawnBlocks;


    @Override
    public void onEnable() {

        this.getLogger().info("Quake!");
        world = this.getServer().getWorld("world");
        main = this;
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GameListener(), this);
        ReadConfig readConfig = new ReadConfig(this);
        playerSpawnBlocks = readConfig.getPlayerSpawnBlocks();
        jumpBlocks = new ArrayList<>();
        itemSpawnBlocks = new ArrayList<>();
        for (BehaviorBlock s : readConfig.getBehaviorBlocks(JumpBlock.class)) {
            s.buildBehavior(this);
            jumpBlocks.add((JumpBlock) s);
        }
        for (BehaviorBlock s : readConfig.getBehaviorBlocks(ItemSpawnBlock.class)) {
            s.buildBehavior(this);
            itemSpawnBlocks.add((ItemSpawnBlock) s);
        }

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        CommandController controller = new CommandController(p);
        WriteConfig writeConfig = new WriteConfig(this);

        if (label.equals("newjumpblock") && p.hasPermission("creator")) {
            if (args.length == 3) {
                boolean free = false;
                switch (args[0]) {
                    case "free":
                        free = true;
                        break;
                    case "directed":
                        break;
                    default:
                        controller.errorMessage("Incorrect type!");
                        return true;
                }

                if (!isDouble(args[1])) {
                    controller.errorMessage("Incorrect power!");
                    return true;
                }

                if (checkBlockId(jumpBlocks, args[2])) {
                    controller.errorMessage("This name already exists!");
                    return true;
                }
                JumpBlock jumpBlock = controller.createJumpBlock(free, Double.valueOf(args[1]), args[2]);
                jumpBlock.buildBehavior(this);
                jumpBlocks.add(jumpBlock);
                writeConfig.addBehaviorBlock(jumpBlock);
                controller.successCreatedBlock(jumpBlock.getName());
            }
        }

        if (label.equals("newitemblock") && p.hasPermission("creator")) {
            if (args.length >= 3) {

                ItemStack itemStack = null;

                int i = 1;
                switch (args[0]) {
                    case "armor":
                        itemStack = getItemStuck(Armor.Type.values(), new Armor(), args[i]);
                        break;
                    case "health":
                        itemStack = new Health().getItem(Item.getEnumByName(Health.Type.values(), args[i]));
                        break;
                    case "weapon":
                        itemStack = getItemStuck(Weapon.Type.values(), new Weapon(this), args[i]);
                        break;
                    case "ammo":
                        if (isInt(args[i + 1])) {
                            itemStack = Weapon.getAmmo((Weapon.Type) Item.getEnumByToString(Weapon.Type.values(), args[i]), Integer.valueOf(args[i + 1]));
                        }
                        ++i;
                        break;
                    default:
                        controller.errorMessage("Incorrect type!");
                        return true;
                }

                if (itemStack == null) {
                    controller.errorMessage("Incorrect item!");
                    return true;
                }

                if (!isInt(args[i + 1])) {
                    controller.errorMessage("Incorrect delay!");
                    return true;
                }

                if (checkBlockId(itemSpawnBlocks, args[i + 2])) {
                    controller.errorMessage("This name already exists!");
                    return true;
                }

                ItemSpawnBlock spawnBlock = controller.createItemBlock(Integer.valueOf(args[i + 1]), itemStack, args[i + 2]);
                spawnBlock.buildBehavior(this);
                itemSpawnBlocks.add(spawnBlock);
                writeConfig.addBehaviorBlock(spawnBlock);
                controller.successCreatedBlock(spawnBlock.getName());
            }
        }

        if (label.equals("newspawnblock") && p.hasPermission("creator")) {
            if (args.length == 1 && !checkBlockId(playerSpawnBlocks, args[0])) {
                PlayerSpawnBlock playerSpawnBlock = controller.createSpawnBlock(args[0]);
                writeConfig.addPlayerSpawnBlock(playerSpawnBlock);
                playerSpawnBlocks.add(playerSpawnBlock);
                controller.successCreatedBlock(playerSpawnBlock.getName());
            } else {
                controller.errorMessage("Incorrect name!");
                return true;
            }
        }

        if (label.equals("show") && p.hasPermission("creator")) {
            if (args.length >= 1) {
                int page = 0;

                if (args.length == 2) {
                    if (isInt(args[1])) {
                        page = Integer.valueOf(args[1]);
                    } else {
                        controller.errorMessage("Incorrect index!");
                        return true;
                    }
                }

                switch (args[0]) {
                    case "itemblocks":
                        controller.chatList(itemSpawnBlocks, page);
                        break;
                    case "jumpblocks":
                        controller.chatList(jumpBlocks, page);
                        break;
                    case "spawnblocks":
                        controller.chatList(playerSpawnBlocks, page);
                        break;
                    default:
                        controller.errorMessage("Incorrect type!");
                        return true;
                }

            } else {
                controller.errorMessage("Incorrect data!");
                return true;
            }
        }

        if (label.equals("remove") && p.hasPermission("creator")) {
            if (args.length == 2) {
                ArrayList<? extends Block> blocks;
                switch (args[0]) {
                    case "itemblock":
                        blocks = itemSpawnBlocks;
                        break;
                    case "jumpblock":
                        blocks = jumpBlocks;
                        break;
                    case "spawnblock":
                        blocks = playerSpawnBlocks;
                        break;
                    default:
                        controller.errorMessage("Incorrect type!");
                        return true;
                }
                if (controller.removeBlock(blocks, writeConfig, args[1])) {
                    controller.successRemovedBlock(args[1]);
                } else {
                    controller.errorMessage("You can't hahahah! Why? Idk :(");
                    return true;
                }
            } else {
                controller.errorMessage("Incorrect data!");
                return true;
            }
        }
        return false;
    }


    private boolean isDouble(String string) {
        try {
            Double.valueOf(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isInt(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ItemStack getItemStuck(Enum[] values, Item item, String type) {
        if (Item.getEnumByToString(values, type) != null) {
            return item.getItem(Item.getEnumByToString(values, type));
        } else {
            return null;
        }
    }


    private boolean checkBlockId(ArrayList<? extends Block> blocks, String list) {
        for (Block block : blocks) {
            if (list.equals(block.getName())) {
                return true;
            }
        }
        return false;
    }

}

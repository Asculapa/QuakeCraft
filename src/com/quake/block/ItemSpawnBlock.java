package com.quake.block;

import com.quake.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Location;
import org.bukkit.util.Vector;


public class ItemSpawnBlock implements BehaviorBlock {
    private String name;
    private ItemStack itemStack;
    private Item item;
    private int delay;
    private Block block;
    private BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    private int taskID;
    private Location spawnLocation;
    private World world;
    private boolean wait = true;

    public ItemSpawnBlock(){}

    public ItemSpawnBlock(Block block, int delay, ItemStack itemStack, String name) {
        this.block = block;
        this.name = name;
        this.world = block.getWorld();
        this.spawnLocation = block.getLocation();
        this.spawnLocation.add(0.5d, 1, 0.5d);
        this.delay = delay;
        this.itemStack = itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public String getBlockClass() {
        return this.getClass().getSimpleName();
    }

    @Override
    public ConfigurationSection setSpecificArgs(ConfigurationSection section) {
        section.set("delay", delay);
        section.set("itemStack", itemStack);
        return section;
    }

    @Override
    public BehaviorBlock getInstance(ConfigurationSection section, Block block, String name) {
        try {
            int delay = section.getInt("delay");
            ItemStack itemStack = section.getItemStack("itemStack");
            return new ItemSpawnBlock(block, delay, itemStack, name);
        } catch (Exception e) {
            Main.log.info("I can't create " + getBlockClass());
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean buildBehavior(Plugin plugin) {
        try {
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
                if (!itemAbsent() && wait) {
                    scheduler.scheduleSyncDelayedTask(plugin, () -> {
                        if (item != null){
                            item.remove();
                        }
                        item = world.dropItem(spawnLocation, itemStack);
                        item.setVelocity(new Vector(0, 0, 0));
                        Main.log.info(item.getLocation().toString());
                        Main.log.info("New item");
                        wait = true;
                    }, delay);
                    wait = false;
                }
                Main.log.info("New cicle");
            }, 50L, 50L);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeBehavior() {
            try {
                scheduler.cancelTask(taskID);
                return true;
            } catch (Exception e) {
                Main.log.info("ItemSpawnBlock " + name + " not removed");
                e.printStackTrace();
                return false;
            }
    }

    private boolean itemAbsent() {
        if (item != null){
            Main.log.info("valid - " + item.isValid());
        }
        //TODO fix it
        return item != null && item.isValid() && !item.isDead() && item.getLocation().getBlock().getRelative(BlockFace.DOWN).equals(block);
    }
}
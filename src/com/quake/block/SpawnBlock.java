package com.quake.block;

import com.quake.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SpawnBlock implements BehaviorBlock {
    private int id;
    private ItemStack itemStack;
    private Item item;
    private long delay = 200;
    private Block block;
    private BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    private int taskID;
    private Location blockLocation;
    private Location spawnLocation;
    private World world;
    private Plugin plugin;
    private boolean wait = true;

    public SpawnBlock(Block block, Plugin plugin) {
        this.block = block;
        this.plugin = plugin;
        this.world = block.getWorld();
        this.blockLocation = block.getLocation();
        spawnLocation = blockLocation;
        spawnLocation.add(0.5d, 1, 0.5d);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public Block getBlock() {
        return block;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean buildBehavior() {
        try {
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
                if (!itemAbsent() && wait) {
                    scheduler.scheduleSyncDelayedTask(plugin, () -> {
                        item = world.dropItem(spawnLocation, itemStack);
                        item.setVelocity(new Vector(0, 0, 0));
                        Main.log.info(item.getLocation().toString());
                        Main.log.info("New item");
                        wait = true;
                    }, delay);
                    wait = false;
                }
            }, 50L, 50L);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeBehavior() {
        if (scheduler.isCurrentlyRunning(taskID)) {
            try {
                scheduler.cancelTask(taskID);
                return true;
            } catch (Exception e) {
                Main.log.info("BlockSpawn " + id + "not removed");
                return false;
            }
        }
        return false;
    }

    private boolean itemAbsent() {
        return item != null && !item.isDead() && item.getLocation().getBlock().getRelative(BlockFace.DOWN).equals(block);
    }

}

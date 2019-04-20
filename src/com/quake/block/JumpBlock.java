package com.quake.block;

import com.quake.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class JumpBlock implements BehaviorBlock, Listener {

    private Block block;
    private String name;
    private Vector direction;
    private double power;
    private int taskID;
    private BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    public JumpBlock() {
    }

    public JumpBlock(Block block, Vector direction, double power, String name) {
        this.block = block;
        this.direction = direction;
        this.power = power;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public String getBlockClass() {
        return this.getClass().getSimpleName();
    }

    @Override
    public ConfigurationSection setSpecificArgs(ConfigurationSection section) {
        section.set("power", power);
        section.set("direction", direction);
        return section;
    }

    @Override
    public BehaviorBlock getInstance(ConfigurationSection section, Block block, String name) {
        try {
            Vector direction = section.getVector("direction");
            int power = section.getInt("power");
            return new JumpBlock(block, direction, power, name);
        } catch (Exception e) {
            Main.log.info("I can't create " + getBlockClass());
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean buildBehavior(Plugin plugin) {
        taskID = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).equals(block)) {
                    Vector v = direction;
                    if (direction == null) {
                        player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(power));
                    } else {
                        player.setVelocity(v.normalize().multiply(power)
                        );
                    }
                }
            });
        }, 5L, 5L);
        return true;
    }

    @Override
    public boolean removeBehavior() {
        try {
            scheduler.cancelTask(taskID);
            return true;
        } catch (Exception e) {
            Main.log.info("Jumpblock " + name + " not removed");
            e.printStackTrace();
            return false;
        }
    }

}


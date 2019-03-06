package com.quake.block;

import com.quake.Main;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class JumpBlock implements BehaviorBlock, Listener {

    private Block block;
    private String name;
    private Vector direction;
    private double power;
    private Plugin plugin;

    public JumpBlock(){}

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
            return new JumpBlock(block,direction,power,name);
        } catch (Exception e) {
            Main.log.info("I can't create " + getBlockClass());
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean buildBehavior(Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        return true;
    }

    @Override
    public boolean removeBehavior() {
        if (plugin != null) {
            PlayerPortalEvent.getHandlerList().unregister(this);
            PlayerMoveEvent.getHandlerList().unregister(this);
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    private void onPlyerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).equals(block)) {
            Player player = event.getPlayer();
            if (direction == null) {
                player.setVelocity(player.getEyeLocation().getDirection().multiply(power));
            } else {
                player.setVelocity(direction.multiply(power));
            }
        }
    }
}


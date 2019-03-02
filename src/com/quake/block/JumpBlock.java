package com.quake.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

//TODO modify later
public class JumpBlock implements BehaviorBlock, Listener {

    private Block block;
    private String name;
    private Vector direction;
    private double power;
    private Plugin plugin;

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
    private void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
            event.getPlayer().leaveVehicle();
        }
    }

    @EventHandler
    private void onPlyerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).equals(block)) {
            Player player = event.getPlayer();
            EnderPearl ep = player.launchProjectile(EnderPearl.class, direction);
            ep.addPassenger(player);
        }

        if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
            event.getPlayer().getVelocity().multiply(power);
        }
    }
}


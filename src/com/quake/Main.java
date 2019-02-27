package com.quake;

import com.quake.block.SpawnBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener, CommandExecutor {

    public static final Logger log = Logger.getLogger("Minecraft");
    public static final String PLUGIN_NAME = "QuakeCraft";
    public static World world;

    @Override
    public void onEnable() {
        this.getLogger().info("Quake!");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Main.log.info("Fire!");
        Action action = event.getAction();

        Player player = event.getPlayer();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand().getType().equals(Material.IRON_SWORD)) {
              //  Fireball f = player.launchProjectile(Fireball.class);
               // f.setIsIncendiary(false);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpawnBlock spawnBlock = new SpawnBlock(((Player)sender).getLocation().getBlock().getRelative(BlockFace.DOWN),this);
        spawnBlock.setItemStack(new ItemStack(Material.WRITTEN_BOOK));
        spawnBlock.buildBehavior();
        return false;
    }
}
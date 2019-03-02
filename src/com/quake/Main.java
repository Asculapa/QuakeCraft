package com.quake;

import com.quake.block.JumpBlock;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener, CommandExecutor {

    public static final Logger log = Logger.getLogger("Minecraft");
    public static final String PLUGIN_NAME = "QuakeCraft";
    public static World world;
    public static ArrayList<Listener> listeners = new ArrayList<>();

    @Override
    public void onEnable() {
        this.getLogger().info("Quake!");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      //  SpawnBlock spawnBlock = new SpawnBlock(this,((Player)sender).getLocation().getBlock().getRelative(BlockFace.DOWN),"First");
      //  spawnBlock.setItemStack(new ItemStack(Material.WRITTEN_BOOK));
      //  spawnBlock.setDelay(200);
      //  spawnBlock.buildBehavior();

      //  JumpBlock jumpBlock = new JumpBlock(((Player)sender),3,"1");
      //  jumpBlock.buildBehavior(this);
        return false;
    }
}
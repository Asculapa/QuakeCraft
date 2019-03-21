package com.quake;

import com.quake.block.BehaviorBlock;
import com.quake.block.ItemSpawnBlock;
import com.quake.block.JumpBlock;
import com.quake.block.PlayerSpawnBlock;
import com.quake.item.Armor;
import com.quake.item.Health;
import com.quake.listener.GameListener;
import com.quake.listener.ItemListener;
import com.quake.item.Weapon;
import com.quake.сonfig.ReadConfig;
import net.minecraft.server.v1_13_R2.ChatComponentText;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;


public class Main extends JavaPlugin implements Listener, CommandExecutor {

    public static final Logger log = Logger.getLogger("Minecraft");
    public static World world;
    public static Main main;
    private ArrayList<PlayerSpawnBlock> playerSpawnBlocks;
    private ArrayList<JumpBlock> jumpBlocks;

    @Override
    public void onEnable() {
        this.getLogger().info("Quake!");
        world = this.getServer().getWorld("world");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GameListener(), this);
        ReadConfig readConfig = new ReadConfig(this);
        playerSpawnBlocks = readConfig.getPlayerSpawnBlocks();
        readConfig.getBehaviorBlocks(JumpBlock.class);
        main = this;
        //==============================================================================
        for (BehaviorBlock s : readConfig.getBehaviorBlocks(JumpBlock.class)) {
            s.buildBehavior(this);
            log.info(s.getName() + "/n" + s.getBlock().toString());
        }
        for (BehaviorBlock s : readConfig.getBehaviorBlocks(ItemSpawnBlock.class)) {
            s.buildBehavior(this);
            log.info(s.getName() + "/n" + s.getBlock().toString());
        }
       /* for (ItemSpawnBlock block : config.getSpawnBlocks()){

            log.info("Name - " + block.getName());

            log.info("X - " + block.getBlock().getX());
            log.info("Y - " + block.getBlock().getY());
            log.info("Z - " + block.getBlock().getZ());
            block.buildBehavior(this);
        }*/
    }

    @Override
    public void onDisable() {

    }

    public void sendMultilineMessage(Player player, String message) {
        if (player != null && message != null && player.isOnline()) {
            String[] s = message.split("\n");
            for (String m : s) {
                player.sendMessage(m);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equals("new")) {
            CommandController controller = new CommandController((Player) sender);
            if (args.length >= 4) {
                switch (args[0]) {
                    case "jumpblock":
                        boolean free = false;
                        switch (args[1]) {
                            case "free":
                                free = true;
                                break;
                            case "directed":
                                break;
                            default:
                                controller.errorMessage("Incorrect type!");
                                return false;
                        }
                        if (isDouble(args[2])) {
                            jumpBlocks.add(controller.createJumpBlock(free, Double.valueOf(args[2]), args[3]));
                        } else {
                            controller.errorMessage("Incorrect power!");
                            return false;
                        }
                }
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

}

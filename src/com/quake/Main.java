package com.quake;

import com.quake.block.BehaviorBlock;
import com.quake.block.ItemSpawnBlock;
import com.quake.block.JumpBlock;
import com.quake.block.PlayerSpawnBlock;
import com.quake.item.Armor;
import com.quake.item.Health;
import com.quake.item.ItemListener;
import com.quake.item.Weapon;
import com.quake.сonfig.ReadConfig;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;


public class Main extends JavaPlugin implements Listener, CommandExecutor {

    public static final Logger log = Logger.getLogger("Minecraft");
    public static final String PLUGIN_NAME = "QuakeCraft";
    public static World world;
    private ArrayList<PlayerSpawnBlock> playerSpawnBlocks;

    @Override
    public void onEnable() {
        this.getLogger().info("Quake!");
        world = this.getServer().getWorld("world");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        ReadConfig readConfig = new ReadConfig(this);
        playerSpawnBlocks = readConfig.getPlayerSpawnBlocks();
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //  ItemSpawnBlock spawnBlock = new ItemSpawnBlock(this,((Player)sender).getLocation().getBlock().getRelative(BlockFace.DOWN),"First");
        //  spawnBlock.setItemStack(new ItemStack(Material.WRITTEN_BOOK));
        //  spawnBlock.setDelay(200);
        //  spawnBlock.buildBehavior();
/*        Player player = (Player) sender;
        JumpBlock jumpBlock = new JumpBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),null,10,"234d" + ++test);
        jumpBlock.buildBehavior(this);
        ItemSpawnBlock spawnBlock = new ItemSpawnBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),20,new ItemStack(Material.IRON_SWORD),"Hohoh"+ ++test);
        spawnBlock.buildBehavior(this);
        WriteConfig w = new WriteConfig(this);
        w.addBehaviorBlock(jumpBlock);
        w.addBehaviorBlock(spawnBlock);*/
        Player player = (Player) sender;
        Armor armor = new Armor();
        Health health = new Health();
        Weapon weapon = new Weapon();
        Arrow item = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection().multiply(20));
        item.setDamage(999d);
        world.dropItem(player.getLocation(), weapon.getItem(Weapon.Type.DIAMOND_HOE));
        world.dropItem(player.getLocation(), weapon.getItem(Weapon.Type.DIAMOND_PICKAXE));
        world.dropItem(player.getLocation(), weapon.getItem(Weapon.Type.DIAMOND_SWORD));
        world.dropItem(player.getLocation(), weapon.getItem(Weapon.Type.DIAMOND_SHOVEL));
        world.dropItem(player.getLocation(), Weapon.getAmmo(Weapon.Type.DIAMOND_SHOVEL, 20));
/*        PlayerSpawnBlock spawnBlock = new PlayerSpawnBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),"SpawnBlock" + ++test);
        WriteConfig writeConfig = new WriteConfig(this);
        writeConfig.addPlayerSpawnBlock(spawnBlock);*/

        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!UserInterface.createScoreBoard(event.getPlayer())) {
            event.getPlayer().kickPlayer("Developer is fool =/");
            event.getPlayer().setMaximumNoDamageTicks(200); // TODO add config and delete this (https://bukkit.org/threads/getting-a-players-attack-speed.329494/)
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();

        if (killer != null && !killer.equals(killed)) {
            UserInterface.addKills(killer, 1);
        }else{
            UserInterface.addKills(killed, -1);
        }

        UserInterface.resetScoreBoard(killed);

    }
}

/*
    }*/

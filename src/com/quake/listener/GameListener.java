package com.quake.listener;

import com.quake.Main;
import com.quake.PlayerEffect;
import com.quake.UserInterface;
import com.quake.block.PlayerSpawnBlock;
import com.quake.item.Weapon;
import com.quake.сonfig.ReadConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class GameListener implements Listener {

    private Plugin plugin;
    private static float explosionDamage = 2F;

    public static void build(ReadConfig readConfig){
        double f = readConfig.getDoubleValue("explosionRadius");
        if (f != 0){
            explosionDamage = (float) f;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!UserInterface.createScoreBoard(player)) {
            event.getPlayer().kickPlayer("Developer is fool =/");
        }
        setDefaultEffects(event.getPlayer());
        clearInventory(player);
        clearArmor(player);
        player.setHealth(20d);
        new Weapon().pickUp(player, Weapon.getSword());
        PlayerSpawnBlock b = getRandomSpawnBlock();

        if (b != null){
            player.teleport(b.getBlock().getLocation());
        }
    }

    public GameListener(Plugin plugin) {
        this.plugin = plugin;
    }

    private void setDefaultEffects(Player player) {
        PlayerEffect.setEffect(player, PlayerEffect.Type.JUMP, 2);
        PlayerEffect.setEffect(player, PlayerEffect.Type.REGENERATION, 2);
        PlayerEffect.setEffect(player, PlayerEffect.Type.SPEED, 2);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();
        e.setKeepInventory(true);
        clearInventory(killed);
        clearArmor(killed);

        if (killer != null && !killer.equals(killed)) {
            UserInterface.addKills(killer, 1, plugin);
        } else {
            UserInterface.addKills(killed, -1, plugin);
        }
        UserInterface.resetScoreBoard(killed);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> setDefaultEffects(player), 20);
        if (Main.getPlayerSpawnBlocks().size() != 0) {
            PlayerSpawnBlock block = getRandomSpawnBlock();
            event.setRespawnLocation(block.getBlock().getLocation().add(0.5d, 0, 0.5d));
        }
        new Weapon().pickUp(player, Weapon.getSword());
        setDefaultEffects(event.getPlayer());
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        Entity ent = event.getEntity();
        if (ent instanceof Fireball) {
            event.setRadius(explosionDamage);
        }
    }

    @EventHandler
    public void PickUpArrow(PlayerPickupArrowEvent event) {
        event.getArrow().remove();
        event.setCancelled(true);
    }

    private static void clearInventory(Player player) {
        player.getInventory().clear();
        player.updateInventory();
    }

    private static void clearArmor(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
    private PlayerSpawnBlock getRandomSpawnBlock(){
        if (Main.getPlayerSpawnBlocks().size() == 0){
            return null;
        }
        return Main.getPlayerSpawnBlocks().get((int) (Math.random() * Main.getPlayerSpawnBlocks().size()));
    }
}

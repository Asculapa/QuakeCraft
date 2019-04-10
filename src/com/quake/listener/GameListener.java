package com.quake.listener;

import com.quake.Main;
import com.quake.PlayerEffect;
import com.quake.UserInterface;
import com.quake.block.PlayerSpawnBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class GameListener implements Listener {

    private Plugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!UserInterface.createScoreBoard(event.getPlayer())) {
            event.getPlayer().kickPlayer("Developer is fool =/");
        }
        setDefaultEffects(event.getPlayer());
    }

    public GameListener(Plugin plugin) {
        this.plugin = plugin;
    }

    private void setDefaultEffects(Player player){
        PlayerEffect.setEffect(player, PlayerEffect.Type.JUMP,2);
        PlayerEffect.setEffect(player, PlayerEffect.Type.REGENERATION,2);
        PlayerEffect.setEffect(player, PlayerEffect.Type.SPEED,2);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();

        if (killer != null && !killer.equals(killed)) {
            UserInterface.addKills(killer, 1,plugin);
        } else {
            UserInterface.addKills(killed, -1,plugin);
        }
        UserInterface.resetScoreBoard(killed);
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> setDefaultEffects(player), 20);
        if (Main.getPlayerSpawnBlocks().size() != 0) {
            PlayerSpawnBlock block = Main.getPlayerSpawnBlocks().get((int) (Math.random() * Main.getPlayerSpawnBlocks().size()));
            event.setRespawnLocation(block.getBlock().getLocation().add(0.5d,0,0.5d));
        }
        setDefaultEffects(event.getPlayer());
    }

    @EventHandler
    public void PickUpArrow(PlayerPickupArrowEvent event){
        event.getArrow().remove();
        event.setCancelled(true);
    }

}

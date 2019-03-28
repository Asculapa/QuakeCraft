package com.quake.listener;

import com.quake.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!UserInterface.createScoreBoard(event.getPlayer())) {
            event.getPlayer().kickPlayer("Developer is fool =/");
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();

        if (killer != null && !killer.equals(killed)) {
            UserInterface.addKills(killer, 1);
        } else {
            UserInterface.addKills(killed, -1);
        }

        UserInterface.resetScoreBoard(killed);

    }
}

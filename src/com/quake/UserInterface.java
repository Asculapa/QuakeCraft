package com.quake;

import com.quake.item.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

public final class UserInterface {
    private static String resources = "Resources";
    private static String kills = ChatColor.RED + "Kills";
    private static final int MAX_KILLS = 20;
    private static boolean winner;

    public static boolean createScoreBoard(Player player) {
        try {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            Objective objective = board.registerNewObjective(resources, "dummy", resources);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            for (Weapon.Type s : Weapon.Type.values()) {
                if (s == Weapon.Type.DIAMOND_SWORD) {
                    continue;
                }
                Score score = objective.getScore(s.toString());
                score.setScore(0);
            }
            Score score = objective.getScore(kills);
            score.setScore(0);
            player.setScoreboard(board);
            return true;
        } catch (Exception e) {
            Main.log.info("I can't create scoreboard");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeScoreBoard(Player player) {
        try {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            return true;
        } catch (Exception e) {
            Main.log.info("I can't remove scoreboard");
            e.printStackTrace();
            return false;
        }
    }

    public static void resetScoreBoard(Player player) {
        for (Weapon.Type t : Weapon.Type.values()) {
            if (t == Weapon.Type.DIAMOND_SWORD) {
                continue;
            }
            player.getScoreboard().getObjective(resources).getScore(t.toString()).setScore(0);
        }
    }

    public static int getKills(Player player) {
        return player.getScoreboard().getObjective(resources).getScore(kills).getScore();
    }

    public static void addKills(Player player, int killCount, Plugin plugin) {
        player.getScoreboard().getObjective(resources).getScore(kills).setScore(getKills(player) + killCount);
        if (player.getScoreboard().getObjective(resources).getScore(kills).getScore() >= MAX_KILLS && !winner) {
            winner = true;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                    p.setHealth(0);
                    resetScoreBoard(p);
                    p.getScoreboard().getObjective(resources).getScore(kills).setScore(0);
                });
                Main.removeItems(player.getWorld());
                winner = false;
            }, 50);
            announceTheWinner(player);
        }
    }

    public static int getAmmo(Player player, Weapon.Type weapon) {
        try {
            return player.getScoreboard().getObjective(resources).getScore(weapon.toString()).getScore();
        } catch (Exception e) {
            Main.log.info("I can't get ammo");
            e.printStackTrace();
            return 0;
        }
    }

    public static void addAmmo(Player player, Weapon.Type weapon, int count) {
        try {
            player.getScoreboard().getObjective(resources).getScore(weapon.toString()).setScore(getAmmo(player, weapon) + count);
        } catch (Exception e) {
            Main.log.info("I can't add ammo");
            e.printStackTrace();
        }
    }

    private static void announceTheWinner(Player p) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            if (p.equals(player)) {
                p.sendMessage(ChatColor.AQUA + "OMG you did it! Great game!");
            } else {
                player.sendMessage(ChatColor.DARK_AQUA + "The winner of this game becomes " + ChatColor.BLUE + p.getName());
            }
        });

    }
}

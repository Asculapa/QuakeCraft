package com.quake;

import com.quake.item.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public final class UserInterface {
    private static String resources = "Resources";
    private static String kills = ChatColor.RED + "Kills";

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

    public static void resetScoreBoard(Player player){
        for (Weapon.Type t: Weapon.Type.values()) {
            player.getScoreboard().getObjective(resources).getScore(t.toString()).setScore(0);
        }
    }

    public static int getKills(Player player) {
        return player.getScoreboard().getObjective(resources).getScore(kills).getScore();
    }

    public static void addKills(Player player, int killCount) {
        player.getScoreboard().getObjective(resources).getScore(kills).setScore(getKills(player) + killCount);
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
}
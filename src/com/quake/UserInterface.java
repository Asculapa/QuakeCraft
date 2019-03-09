package com.quake;

import com.quake.item.Weapon;
import org.bukkit.entity.Player;

public final class UserInterface {
    public static boolean createScoreBoard(Player player) {
        try {

            return true;
        } catch (Exception e) {
            Main.log.info("I can't create scoreboard");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeScoreBoard(Player player) {
        return false;
    }

    public static int getKills(Player player) {
        return 0;
    }

    public static void setKills(Player player) {

    }

    public static int getAmmo(Player player, Weapon.Type weapon) {
        return 0;
    }

    public static void setAmmo(Player player, Weapon.Type weapon) {

    }
}

package com.quake;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerEffect {
    public enum Type {
        SPEED, JUMP, REGENERATION
    }

    public static void setEffect(Player player, Type type, int amplifier) {

        if (amplifier <= 0 || amplifier >= 10) {
            return;
        }

        PotionEffect effect = null;
        switch (type) {
            case JUMP:
                effect = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, amplifier);
                break;
            case SPEED:
                effect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, amplifier);
                break;
            case REGENERATION:
                effect = new PotionEffect(PotionEffectType.REGENERATION,60,amplifier);
                break;
        }
        player.addPotionEffect(effect);
    }
}

package com.quake.item;

import com.quake.сonfig.ReadConfig;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class Health implements Item {

    private static double smallHealth;
    private static double middleHealth;
    private static double hugeHealth;

    public static void biuld(ReadConfig readConfig) {
        smallHealth = readConfig.getDoubleValue("smallHealth");
        middleHealth = readConfig.getDoubleValue("middleHealth");
        hugeHealth = readConfig.getDoubleValue("hugeHealth");
    }

    public enum Type {
        SMALL {
            @Override
            public String toString() {
                return "JUMP";
            }
        }, MIDDLE {
            @Override
            public String toString() {
                return "FIRE_RESISTANCE";
            }
        }, HUGE {
            @Override
            public String toString() {
                return "REGEN";
            }
        }
    }

    @Override
    public void pickUp(Player player, ItemStack itemStack) {
        switch (Type.valueOf(itemStack.getItemMeta().getDisplayName())) {
            case SMALL:
                player.setHealth(getHealth(player, smallHealth));
                break;
            case MIDDLE:
                player.setHealth(getHealth(player, middleHealth));
                break;
            case HUGE:
                player.setHealth(getHealth(player, hugeHealth));
                break;
        }
    }

    private double getHealth(Player player, double heath) {
        double maxHeath = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double currentHealth = player.getHealth();
        if (currentHealth + heath > maxHeath) {
            return maxHeath;
        } else {
            return currentHealth + heath;
        }
    }

    @Override
    public ItemStack getItem(Enum e) {
        if (Item.valueIsExist(Type.values(), e.name())) {
            ItemStack potion = new ItemStack(Material.POTION, 1);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName(e.name());
            meta.setBasePotionData(new PotionData(PotionType.valueOf(e.toString())));
            potion.setItemMeta(meta);
            return potion;
        }
        return null;
    }
}

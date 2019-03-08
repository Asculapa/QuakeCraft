package com.quake.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Item {
    void pickUp(Player player, ItemStack itemStack);
    ItemStack getItem(Enum e);
    static boolean valueIsExist(Enum[] values, String string){
        for (Enum c : values) {
            if (c.name().equals(string)) {
                return true;
            }
        }
        return false;
    }
}

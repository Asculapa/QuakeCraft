package com.quake.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public interface Item {
    void pickUp(Player player, ItemStack itemStack);

    ItemStack getItem(Enum e);

    static boolean valueIsExist(Enum[] values, String string) {
        for (Enum c : values) {
            if (c.name().equals(string)) {
                return true;
            }
        }
        return false;
    }
    static boolean itemIsExist(PlayerInventory playerInventory,ItemStack itemStack){
        for (ItemStack stack : playerInventory.getContents()){
            if (stack != null && stack.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())){
                return true;
            }
        }
        return false;
    }
}

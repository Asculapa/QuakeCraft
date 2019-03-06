package com.quake.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Item {
    void pickUp(Player player, ItemStack itemStack);
}

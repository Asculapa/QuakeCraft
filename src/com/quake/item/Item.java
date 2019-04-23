package com.quake.item;

import com.quake.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

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

    static Enum getEnumByToString(Enum[] values, String string) {
        for (Enum e : values) {
            if (e.toString().equals(string)) {
                return e;
            }
        }
        return null;
    }

    static Enum getEnumByName(Enum[] values, String string) {
        for (Enum e : values) {
            if (e.name().compareToIgnoreCase(string) == 0) {
                return e;
            }
        }
        return null;
    }

    static boolean itemIsExist(PlayerInventory playerInventory, ItemStack itemStack) {
        for (ItemStack stack : playerInventory.getContents()) {
            if (stack != null && stack.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())) {
                return true;
            }
        }
        return false;
    }

    static ItemStack getItemByMaterial(Enum type) {
        try {
            ItemStack itemStack = new ItemStack(Material.getMaterial(type.name()));
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(type.toString());
            itemStack.setItemMeta(meta);
            return itemStack;
        } catch (Exception e) {
            Main.log.info("I can't get item by material");
            e.printStackTrace();
            return null;
        }
    }
}

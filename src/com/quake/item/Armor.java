package com.quake.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Armor implements Item {

    public enum Type {
        DIAMOND_BOOTS {
            @Override
            public String toString() {
                return "Bashmachki";
            }
        }, DIAMOND_HELMET {
            @Override
            public String toString() {
                return "Nabaldazhnik";
            }
        }, DIAMOND_CHESTPLATE {
            @Override
            public String toString() {
                return "NaGrudinin";
            }
        }, DIAMOND_LEGGINGS {
            @Override
            public String toString() {
                return "40 griven";
            }
        }
    }

    @Override
    public void pickUp(Player player, ItemStack itemStack) {
        switch (Type.valueOf(itemStack.getType().name())) {
            case DIAMOND_BOOTS:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_BOOTS.toString());
                player.getInventory().setBoots(itemStack);
                break;
            case DIAMOND_HELMET:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_HELMET.toString());
                player.getInventory().setHelmet(itemStack);
                break;
            case DIAMOND_LEGGINGS:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_LEGGINGS.toString());
                player.getInventory().setLeggings(itemStack);
                break;
            case DIAMOND_CHESTPLATE:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_CHESTPLATE.toString());
                player.getInventory().setChestplate(itemStack);
                break;
        }
    }
}


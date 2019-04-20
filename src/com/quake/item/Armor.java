package com.quake.item;

import com.quake.сonfig.ReadConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Armor implements Item {

    private static String boots;
    private static String helmet;
    private static String chestplate;
    private static String leggins;

    public static void build(ReadConfig config){
        boots = config.getStringValue("boots");
        helmet = config.getStringValue("helmet");
        chestplate = config.getStringValue("chestplate");
        leggins = config.getStringValue("leggins");

        if (boots == null || boots.equals("")) {
            boots = "Bashmachki";
        }

        if (helmet == null ||helmet.equals("")) {
            helmet = "Nabaldazhnik";
        }

        if (chestplate == null ||chestplate.equals("")) {
            chestplate = "NaGrudinin";
        }

        if (leggins == null ||leggins.equals("")) {
            leggins = "40 griven";
        }
    }

    public enum Type {
        DIAMOND_BOOTS {
            @Override
            public String toString() {
                return boots;
            }
        }, DIAMOND_HELMET {
            @Override
            public String toString() {
                return helmet;
            }
        }, DIAMOND_CHESTPLATE {
            @Override
            public String toString() {
                return chestplate;
            }
        }, DIAMOND_LEGGINGS {
            @Override
            public String toString() {
                return leggins;
            }
        }
    }

    @Override
    public void pickUp(Player player, ItemStack itemStack) {
        switch (Type.valueOf(itemStack.getType().name())) {
            case DIAMOND_BOOTS:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_BOOTS.toString());
                destroyItem(player.getInventory(), player.getInventory().getBoots());
                player.getInventory().setBoots(itemStack);
                break;
            case DIAMOND_HELMET:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_HELMET.toString());
                destroyItem(player.getInventory(), player.getInventory().getHelmet());
                player.getInventory().setHelmet(itemStack);
                break;
            case DIAMOND_LEGGINGS:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_LEGGINGS.toString());
                destroyItem(player.getInventory(), player.getInventory().getLeggings());
                player.getInventory().setLeggings(itemStack);
                break;
            case DIAMOND_CHESTPLATE:
                itemStack.getItemMeta().setDisplayName(Type.DIAMOND_CHESTPLATE.toString());
                destroyItem(player.getInventory(), player.getInventory().getChestplate());
                player.getInventory().setChestplate(itemStack);
                break;
        }
    }

    @Override
    public ItemStack getItem(Enum type) {
        if (Item.valueIsExist(Armor.Type.values(), type.name())) {
            return Item.getItemByMaterial(type);
        }
        return null;
    }

    private void destroyItem(PlayerInventory inventory, ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }
        inventory.remove(itemStack);
    }
}


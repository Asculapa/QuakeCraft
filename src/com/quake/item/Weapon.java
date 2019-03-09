package com.quake.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Weapon implements Item {
    public enum Type {
        DIAMOND_SWORD {
            @Override
            public String toString() {
                return "Excalibur";
            }
        }, DIAMOND_SHOVEL {
            @Override
            public String toString() {
                return "Ray";
            }
        }, DIAMOND_HOE {
            @Override
            public String toString() {
                return "Bazooka";
            }
        }, DIAMOND_PICKAXE {
            @Override
            public String toString() {
                return "Blaster";
            }
        }
    }

    @Override
    public void pickUp(Player player, ItemStack itemStack) {

        if (!Item.itemIsExist(player.getInventory(), itemStack)) {
            player.getInventory().addItem(itemStack);
        }

    /*    switch (Type.valueOf(itemStack.getType().name())){
            case DIAMOND_SWORD:
                break;
        }*/
    }

    @Override
    public ItemStack getItem(Enum e) {
        return null;
    }

    public ItemStack getSword() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        meta.setLocalizedName(Type.DIAMOND_SWORD.toString());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

/*private final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    private void fgFire(Player player){
        if (player == null){
            return;
        }

        Block targetBlock = getTargetBlock(player,10);
        Location playerLocation = player.getLocation();
        Vector vector = new Vector(targetBlock.getX() - playerLocation.getX(),
                targetBlock.getY() - playerLocation.getY(),
                targetBlock.getZ() - playerLocation.getZ()
                );
        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setDirection(vector);
    }*/

}
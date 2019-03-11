package com.quake.item;

import com.quake.UserInterface;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;


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
                return "ShoutGun";
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
            return;
        }
        if (itemStack.getItemMeta().getDisplayName().equals(Type.DIAMOND_SWORD.toString())) {
            return;
        }

        for (Type type : Type.values()) {
            if (itemStack.getItemMeta().getDisplayName().equals(type.toString())) {
                UserInterface.addAmmo(player, type, 2);
            }
        }
    }

    @Override
    public ItemStack getItem(Enum e) {
        if (!Item.valueIsExist(Type.values(), e.name())) {
            return null;
        }
        switch (Type.valueOf(e.name())) {
            case DIAMOND_SWORD:
                return getSword();
            case DIAMOND_HOE:
                return getGun(Type.DIAMOND_HOE);
            case DIAMOND_SHOVEL:
                return getGun(Type.DIAMOND_SHOVEL);
            case DIAMOND_PICKAXE:
                return getGun(Type.DIAMOND_PICKAXE);
        }
        return null;
    }

    private ItemStack getSword() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        meta.setDisplayName(Type.DIAMOND_SWORD.toString());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private ItemStack getGun(Type type) {
        if (Type.DIAMOND_SWORD == type) {
            return null;
        }
        ItemStack itemStack = Item.getItemByMaterial(type);
        if (itemStack == null) {
            return null;
        }
        itemStack.setAmount(4);
        return itemStack;
    }

    public static void fire(Type type, Player player) {
        switch (type) {
            case DIAMOND_SHOVEL:
                player.launchProjectile(Snowball.class,player.getEyeLocation().getDirection().multiply(7));
                break;
            case DIAMOND_HOE:
                player.launchProjectile(Arrow.class,player.getEyeLocation().getDirection().multiply(20));
                break;
            case DIAMOND_PICKAXE:
                player.launchProjectile(Fireball.class,player.getEyeLocation().getDirection());
                break;
        }
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
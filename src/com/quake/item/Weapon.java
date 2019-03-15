package com.quake.item;

import com.quake.UserInterface;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;


public class Weapon implements Item {

    private int dropCount = 2; // TODO add config
    private int knockbackLevel = 3;//TODO add config

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
                UserInterface.addAmmo(player, type, dropCount);
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
        meta.addEnchant(Enchantment.KNOCKBACK, knockbackLevel, true);
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
        return itemStack;
    }

    public static void fire(Type type, Player player) {
        switch (type) {
            case DIAMOND_SHOVEL:
                fractionShot(Snowball.class, player.getEyeLocation().getDirection(),0.1d, player);
                break;
            case DIAMOND_HOE:
                player.launchProjectile(Fireball.class, player.getEyeLocation().getDirection());
                break;
            case DIAMOND_PICKAXE:
                player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection().multiply(20));
                break;
        }
    }

    private static void fractionShot(Class<? extends Projectile> projectile, Vector vector, double anlge, Player player) {
        for (double a = -anlge; a <= anlge; a += anlge) {
            double sin = Math.sin(a);
            double cos = Math.cos(a);
            double[][] matrix = {{cos, 0, sin}, {0, 1, 0}, {-sin, 0, cos}};
            player.launchProjectile(projectile, vec3Multiply(matrix, player.getEyeLocation().getDirection()).multiply(3));
        }
    }

    private static Vector vec3Multiply(double[][] matrix, Vector vector4) {
        double x = matrix[0][0] * vector4.getX() + matrix[0][1] * vector4.getY() + matrix[0][2] * vector4.getZ();
        double y = matrix[1][0] * vector4.getX() + matrix[1][1] * vector4.getY() + matrix[1][2] * vector4.getZ();
        double z = matrix[2][0] * vector4.getX() + matrix[2][1] * vector4.getY() + matrix[2][2] * vector4.getZ();
        return new Vector(x, y, z);
    }

    public static ItemStack getAmmo(Weapon.Type type, int count) {
        if (count <= 0 || count > 60) {
            count = 1;
        }

        Material material;
        switch (type) {
            case DIAMOND_HOE:
                material = Material.FIRE_CHARGE;
                break;
            case DIAMOND_PICKAXE:
                material = Material.ARROW;
                break;
            case DIAMOND_SHOVEL:
                material = Material.SNOWBALL;
                break;
            default:
                return null;
        }
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(count);
        return itemStack;
    }

    public static boolean setAmmo(ItemStack itemStack, Player player) {
        Type type;
        switch (itemStack.getType()) {
            case FIRE_CHARGE:
                type = Type.DIAMOND_HOE;
                break;
            case SNOWBALL:
                type = Type.DIAMOND_SHOVEL;
                break;
            case ARROW:
                type = Type.DIAMOND_PICKAXE;
                break;
            default:
                return false;
        }
        UserInterface.addAmmo(player, type, itemStack.getAmount());
        return true;
    }
}
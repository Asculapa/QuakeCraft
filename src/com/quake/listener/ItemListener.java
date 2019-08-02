package com.quake.listener;

import com.quake.Main;
import com.quake.UserInterface;
import com.quake.item.Armor;
import com.quake.item.Health;
import com.quake.item.Weapon;
import com.quake.сonfig.ReadConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

import static com.quake.item.Item.valueIsExist;

public class ItemListener implements Listener {
    private ArrayList<Player> players = new ArrayList<>();
    private static int delay;
    private static Plugin plugin;
    private static double shovelDamage = 4.5d;
    private static double hoeDamage = 12d;
    private static double pickaxeDamage = 8d;

    public ItemListener(ReadConfig readConfig, Plugin p) {
        delay = readConfig.getIntValue("attackDelay");
        if (delay < 0 || delay > 500) {
            delay = 500;
        }
        plugin = p;
    }

    public static void build(ReadConfig readConfig){
        double sd = readConfig.getDoubleValue("shovelDamage");
        double hd = readConfig.getDoubleValue("hoeDamage");
        double pd = readConfig.getDoubleValue("pickaxeDamage");

        if (sd != 0){
            shovelDamage = sd;
        }

        if (hd != 0){
            hoeDamage = hd;
        }

        if (pd != 0){
            pickaxeDamage = pd;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if (event.getDamager() instanceof Snowball) {
                event.setDamage(shovelDamage);
            } else if (event.getDamager() instanceof Arrow) {
                event.setDamage(pickaxeDamage);
            } else if (event.getDamager() instanceof Fireball) {
                event.setDamage(hoeDamage);
            }
        }
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setDamage(0.5d);
        }
    }

    private synchronized void delPlayer(Player player) {
        players.remove(player);
    }

    private synchronized void addPlayer(Player player) {
        players.add(player);
    }

    private synchronized boolean cntPlayer(Player player) {
        return players.contains(player);
    }


    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();


        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        String s = item.getType().name();
        if ((a == Action.LEFT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR) &&
                com.quake.item.Item.valueIsExist(Weapon.Type.values(), s)
                && event.getItem().getItemMeta().getDisplayName().equals(Weapon.Type.valueOf(s).toString())) {
            if (cntPlayer(p)) {
                event.setCancelled(true);
                return;
            }
            if (delay != 0) {
                addPlayer(p);
                Bukkit.getScheduler().runTaskLater(plugin, () -> delPlayer(p), delay);
            }
            if (UserInterface.getAmmo(p, Weapon.Type.valueOf(s)) > 0) {
                Weapon w = new Weapon();
                w.fire(Weapon.Type.valueOf(s), p);
                UserInterface.addAmmo(p, Weapon.Type.valueOf(s), -1);
            }
        }
    }


    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        org.bukkit.entity.Item item = event.getItem();
        Player player = (Player) event.getEntity();

        if (valueIsExist(Armor.Type.values(), item.getItemStack().getType().name())) {
            Armor armor = new Armor();
            armor.pickUp(player, event.getItem().getItemStack());

        } else if (valueIsExist(Health.Type.values(), item.getItemStack().getItemMeta().getDisplayName())) {
            Health health = new Health();
            health.pickUp(player, item.getItemStack());

        } else if (valueIsExist(Weapon.Type.values(), item.getItemStack().getType().name())) {
            Weapon weapon = new Weapon();
            weapon.pickUp(player, item.getItemStack());
        }

        Weapon.setAmmo(item.getItemStack(), player);
        event.setCancelled(true);
        item.getItemStack().setAmount(0);
        item.remove();
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}

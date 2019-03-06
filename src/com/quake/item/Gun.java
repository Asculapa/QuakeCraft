package com.quake.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Gun  {

    private final Block getTargetBlock(Player player, int range) {
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
    }

}

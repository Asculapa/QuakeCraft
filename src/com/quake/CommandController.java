package com.quake;

import com.quake.block.ItemSpawnBlock;
import com.quake.block.JumpBlock;
import com.quake.block.PlayerSpawnBlock;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.BlockFace;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CommandController {
    private Player player;

    public CommandController(Player player) {
        this.player = player;
    }

    public JumpBlock createJumpBlock(boolean free, double power, String name) {
        JumpBlock jumpBlock;
        Vector direction = null;

        if (!free) {
            direction = player.getEyeLocation().getDirection();
        }

        if (power <= 0d) {
            power = 1d;
        } else if (power > 50) {
            power = 50;
        }

        jumpBlock = new JumpBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN), direction, power, name);
        return jumpBlock;
    }

    public ItemSpawnBlock createItemBlock(int delay, ItemStack itemStack, String name){
        if (itemStack == null){
            return null;
        }
        return new ItemSpawnBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),delay,itemStack,name);

    }

    public PlayerSpawnBlock createSpawnBlock(String name){
        return new PlayerSpawnBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),name);
    }

    public void errorMessage(String message){
        player.sendMessage(ChatColor.RED + message);
    }

    public void successMessage(String name){
        player.sendMessage(ChatColor.BLUE + name + ChatColor.GREEN + " was created successfully.");
    }
}

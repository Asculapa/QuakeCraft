package com.quake;

import com.quake.block.JumpBlock;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
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

    public void errorMessage(String message){
        player.sendMessage(ChatColor.RED + message);
    }
}

package com.quake.block;


import org.bukkit.block.Block;

public class PlayerSpawnBlock {
    private Block block;
    private String name;

    public Block getBlock() {
        return block;
    }

    public PlayerSpawnBlock(Block block, String name) {
        this.block = block;
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

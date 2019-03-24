package com.quake.block;


import org.bukkit.block.Block;

public class PlayerSpawnBlock implements com.quake.block.Block {
    private Block block;
    private String name;

    public Block getBlock() {
        return block;
    }

    @Override
    public String getBlockClass() {
        return PlayerSpawnBlock.class.getSimpleName();
    }

    public PlayerSpawnBlock(Block block, String name) {
        this.block = block;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}

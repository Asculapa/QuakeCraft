package com.quake.block;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public interface BehaviorBlock {

    boolean buildBehavior(Plugin plugin);
    boolean removeBehavior();
    String getName();
    Block getBlock();
    String getBlockClass();
    ConfigurationSection setSpecificArgs(ConfigurationSection section);
    BehaviorBlock getInstance(ConfigurationSection section,Block block,String name);
}

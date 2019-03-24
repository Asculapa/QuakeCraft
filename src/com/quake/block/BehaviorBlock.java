package com.quake.block;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public interface BehaviorBlock extends com.quake.block.Block {

    boolean buildBehavior(Plugin plugin);
    boolean removeBehavior();
    ConfigurationSection setSpecificArgs(ConfigurationSection section);
    BehaviorBlock getInstance(ConfigurationSection section,Block block,String name);
}

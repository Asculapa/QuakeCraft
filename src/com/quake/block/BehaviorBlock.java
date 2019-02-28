package com.quake.block;

import org.bukkit.plugin.Plugin;

public interface BehaviorBlock {

    boolean buildBehavior(Plugin plugin);
    boolean removeBehavior();
}

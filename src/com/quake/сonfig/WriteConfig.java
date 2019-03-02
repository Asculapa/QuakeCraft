package com.quake.сonfig;

import com.quake.Main;
import com.quake.block.SpawnBlock;
import org.bukkit.configuration.ConfigurationSection;

public class WriteConfig extends AbstractConfig {

    public WriteConfig(Main main) {
        super(main);
    }

    //TODO replace SpawnBlock -> BehaviorBlock
    public boolean addSpawnBlock(SpawnBlock block) {
        try {

            ConfigurationSection blockSection = configuration.getConfigurationSection("SpawnBlocks");

            if (blockSection == null) {
                configuration.createSection("SpawnBlocks");
                blockSection = configuration.getConfigurationSection("SpawnBlocks");
            }

            if (blockSection.getConfigurationSection(block.getName()) != null) {
                Main.log.info(block.getName() + " exists!");
                return false;
            }

            ConfigurationSection newBlock = blockSection.createSection(block.getName());
            newBlock.set("x", block.getBlock().getX());
            newBlock.set("y", block.getBlock().getY());
            newBlock.set("z", block.getBlock().getZ());
            newBlock.set("delay", block.getDelay());
            newBlock.set("itemStack", block.getItemStack());

            saveConfig();
            return true;

        } catch (Exception e) {
            Main.log.info("I can't write block " + block.getName());
            e.printStackTrace();
            return false;
        }
    }
}

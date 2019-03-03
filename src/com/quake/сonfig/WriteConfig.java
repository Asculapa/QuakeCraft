package com.quake.сonfig;

import com.quake.Main;
import com.quake.block.BehaviorBlock;
import org.bukkit.configuration.ConfigurationSection;

public class WriteConfig extends AbstractConfig {

    public WriteConfig(Main main) {
        super(main);
    }

    public boolean addBehaviorBlock(BehaviorBlock block) {
        try {
            ConfigurationSection blockSection = configurationSection(block.getBlockClass() + "s", block.getName());

            if (blockSection == null) {
                return false;
            }

            ConfigurationSection newBlock = blockSection.createSection(block.getName());
            newBlock.set("x", block.getBlock().getX());
            newBlock.set("y", block.getBlock().getY());
            newBlock.set("z", block.getBlock().getZ());

            block.setSpecificArgs(newBlock);

            saveConfig();
            return true;

        } catch (Exception e) {
            Main.log.info("I can't write block " + block.getName());
            e.printStackTrace();
            return false;
        }
    }

    private ConfigurationSection configurationSection(String section, String block) {
        try {

            ConfigurationSection blockSection = configuration.getConfigurationSection(section);

            if (blockSection == null) {
                configuration.createSection(section);
                blockSection = configuration.getConfigurationSection(section);
            }

            if (blockSection.getConfigurationSection(block) != null) {
                Main.log.info(block + " exists!");
                return null;
            }

            return blockSection;

        } catch (Exception e) {
            Main.log.info("Some problems with section " + section);
            e.printStackTrace();
            return null;
        }
    }
}

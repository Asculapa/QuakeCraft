package com.quake.сonfig;

import com.quake.Main;
import com.quake.block.BehaviorBlock;
import com.quake.block.PlayerSpawnBlock;
import org.bukkit.block.Block;
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

            ConfigurationSection newBlock = blockSection(block.getBlock(), block.getName(), blockSection);
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

    private ConfigurationSection blockSection(Block block, String name, ConfigurationSection section) {
        ConfigurationSection newBlock = section.createSection(name);
        newBlock.set("x", block.getX());
        newBlock.set("y", block.getY());
        newBlock.set("z", block.getZ());
        return newBlock;
    }

    public boolean addPlayerSpawnBlock(PlayerSpawnBlock block) {
        try {
            ConfigurationSection section = configurationSection(PlayerSpawnBlock.class.getSimpleName() + "s", block.getName());

            if (section == null) {
                return false;
            }

            blockSection(block.getBlock(), block.getName(), section);

            saveConfig();

            return true;
        } catch (Exception e) {
            Main.log.info("I can't write " + PlayerSpawnBlock.class.getSimpleName() + " - " + block.getName());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeBlock(com.quake.block.Block block) {
        try {
            ConfigurationSection section = configuration.getConfigurationSection(block.getBlockClass() + "s");
            if (section == null || !section.isSet(block.getName())) {
                return false;
            }
            section.set(block.getName(), null);
            return true;
        } catch (Exception e) {
            Main.log.info("I can't remove " + block.getName());
            e.printStackTrace();
            return false;
        }
    }
}

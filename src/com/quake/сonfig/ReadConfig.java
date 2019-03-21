package com.quake.сonfig;

import com.quake.Main;
import com.quake.block.BehaviorBlock;
import com.quake.block.PlayerSpawnBlock;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Set;


public class ReadConfig extends AbstractConfig {

    public ReadConfig(Main main) {
        super(main);
    }

    public ArrayList<BehaviorBlock> getBehaviorBlocks(Class t) {
        try {
            ArrayList<BehaviorBlock> blocks = new ArrayList<>();
            if (!t.getPackage().getName().equals(BehaviorBlock.class.getPackage().getName())) {
                return null;
            }
            ConfigurationSection section = configuration.getConfigurationSection(t.getSimpleName() + "s");
            if (section == null) {
                return blocks;
            }
            Set<String> stringSet = section.getKeys(false);

            for (String name : stringSet) {
                Object obj = t.newInstance();
                BehaviorBlock block = ((BehaviorBlock) obj);
                ConfigurationSection blockSection = section.getConfigurationSection(name);
                Block blockW = getBlock(blockSection);
                block = block.getInstance(blockSection, blockW, name);
                blocks.add(block);
            }

            return blocks;
        } catch (Exception e) {
            Main.log.info("I can't load " + t.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }

    private Block getBlock(ConfigurationSection section) {
        try {
            int x = section.getInt("x");
            int y = section.getInt("y");
            int z = section.getInt("z");
            return Main.world.getBlockAt(x, y, z);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PlayerSpawnBlock> getPlayerSpawnBlocks() {
        try {
            ArrayList<PlayerSpawnBlock> playerSpawnBlocks = new ArrayList<>();
            ConfigurationSection section = configuration.getConfigurationSection(PlayerSpawnBlock.class.getSimpleName() + "s");
            if (section == null) {
                return playerSpawnBlocks;
            }
            Set<String> stringSet = section.getKeys(false);
            for (String name : stringSet) {
                Block block = getBlock(section.getConfigurationSection(name));
                PlayerSpawnBlock playerSpawnBlock = new PlayerSpawnBlock(block, name);
                playerSpawnBlocks.add(playerSpawnBlock);
            }
            return playerSpawnBlocks;
        } catch (Exception e) {
            Main.log.info("I can't load " + PlayerSpawnBlock.class.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }
    public String getHelp(){
       return configuration.getString("help");
    }
}

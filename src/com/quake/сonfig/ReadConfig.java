package com.quake.сonfig;

import com.quake.Main;
import com.quake.block.BehaviorBlock;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
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
            Set<String> stringSet = section.getKeys(false);

            for (String name : stringSet){
                Object obj = t.newInstance();
                BehaviorBlock block = ((BehaviorBlock)obj);
                ConfigurationSection blockSection = section.getConfigurationSection(name);
                int x = blockSection.getInt("x");
                int y = blockSection.getInt("y");
                int z = blockSection.getInt("z");
                Block blockW = Main.world.getBlockAt(x,y,z);
                block = block.getInstance(blockSection,blockW,name);
                blocks.add(block);
            }

            return blocks;
        }catch (Exception e){
            Main.log.info("I can't load " + t.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }
}

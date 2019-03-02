package com.quake.сonfig;

import com.quake.Main;
import com.quake.block.SpawnBlock;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class ReadConfig extends AbstractConfig {

    public ReadConfig(Main main) {
        super(main);
    }

    public ArrayList<SpawnBlock> getSpawnBlocks() {
        ArrayList<SpawnBlock> spawnBlocks = new ArrayList<>();
        ConfigurationSection mainSection = configuration.getConfigurationSection("SpawnBlocks");
        Set<String> nameOfBlocks = mainSection.getKeys(false);

        if (nameOfBlocks == null){
            return spawnBlocks;
        }

        for (String s : nameOfBlocks) {

            try {

                ConfigurationSection blockSection = mainSection.getConfigurationSection(s);
                double x = blockSection.getDouble("x");
                double y = blockSection.getDouble("y");
                double z = blockSection.getDouble("z");
                int delay = blockSection.getInt("delay");
                ItemStack itemStack = blockSection.getItemStack("itemStack");

                Location loc = new Location(Main.world, x, y, z);
                SpawnBlock spawnBlock = new SpawnBlock(Main.world.getBlockAt(loc), delay, itemStack, s);
                spawnBlocks.add(spawnBlock);

            } catch (Exception e) {
                Main.log.info("I can't load " + s);
                e.printStackTrace();
            }
        }
        return spawnBlocks;
    }
}

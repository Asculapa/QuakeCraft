package com.quake.сonfig;

import com.quake.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

 abstract class AbstractConfig {
    private File file;
    static FileConfiguration configuration;
    private Main main;
    private static final String FILE_NAME = "config.yml";

     AbstractConfig(Main main) {
        this.main = main;
        this.file = new File(main.getDataFolder(), FILE_NAME);
        if (!this.file.exists()) {
            try {
                this.main.saveResource(this.file.getName(), false);
            } catch (Exception e) {
                e.printStackTrace();
                Main.log.info("Something wrong with " + FILE_NAME);
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

     void saveConfig() {
        try {
            configuration.save(file);
        } catch (Exception e) {
            Main.log.info("I can't save config");
        }
    }
}

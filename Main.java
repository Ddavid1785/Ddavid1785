package org.david.minecraftv2;


import org.bukkit.plugin.java.JavaPlugin;
import org.david.minecraftv2.FileSaver.FileManager;

import java.io.IOException;

public final class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;

    }

    private static FileManager manager;

    @Override
    public void onEnable() {

        instance = this;

        manager = new FileManager();

        if(!FileManager.file.exists()) {
            try {
                FileManager.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        getServer().getPluginCommand("beacon").setExecutor(new Beacon_get());
        getServer().getPluginManager().registerEvents(new Beacon_placed(), this);


    }

    @Override
    public void onDisable() {

    }

    public static FileManager getFileManager() {
        return manager;
    }
}

package de.souperman.main;

import de.souperman.commands.CMDjoin;
import de.souperman.listeners.EVENTjoin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Plugin plugin;
    private static World spawn;

    @Override
    public void onEnable() {
        plugin = this;
        spawn = plugin.getServer().getWorld("world");

        System.out.println("[GameServer] started.");

        // register events
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new EVENTjoin(), this);

        // register commands
        this.getCommand("join").setExecutor(new CMDjoin());

    }

    @Override
    public void onDisable() {
        System.out.println("[GameServer] stopped.");
    }


    public static Plugin getPlugin() {
        return plugin;
    }

    public static World getSpawnWorld() {
        return spawn;
    }
}

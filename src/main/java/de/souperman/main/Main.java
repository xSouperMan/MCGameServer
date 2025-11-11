package de.souperman.main;

import de.souperman.commands.CMDjoin;
import de.souperman.commands.CMDsetGameLocation;
import de.souperman.commands.CMDsetspawn;
import de.souperman.games.countershot.CSVar;
import de.souperman.games.countershot.CounterShot;
import de.souperman.listeners.EVENTjoin;
import de.souperman.listeners.EVENTleave;
import de.souperman.vars.Vars;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Plugin plugin;
    private static World spawn;
    private static PluginManager pm;

    @Override
    public void onEnable() {
        plugin = this;
        spawn = plugin.getServer().getWorld("world");

        getLogger().info("[GameServer] started.");

        saveDefaultConfig();

        CSVar.initCSVars();

        spawn.setStorm(false);
        spawn.setThundering(false);
        spawn.setWeatherDuration(999999);
        spawn.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        // register events
        pm = Bukkit.getPluginManager();
        pm.registerEvents(new EVENTjoin(), this);
        pm.registerEvents(new EVENTleave(), this);
        pm.registerEvents(new Lobby(), this);

        // register commands
        this.getCommand("join").setExecutor(new CMDjoin());
        this.getCommand("setspawn").setExecutor(new CMDsetspawn());
        this.getCommand("setgamelocation").setExecutor(new CMDsetGameLocation());

        // initializer
        Vars.gamesInit();
        Lobby.lobbyInit();
        Vars.varsInit();
    }

    @Override
    public void onDisable() {
        getLogger().info("[GameServer] stopped.");
    }


    public static Plugin getPlugin() {
        return plugin;
    }

    public static World getSpawnWorld() {
        return spawn;
    }

    public static PluginManager getPluginManager() { return pm; }

}



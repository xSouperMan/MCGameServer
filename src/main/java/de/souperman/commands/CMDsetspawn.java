package de.souperman.commands;

import de.souperman.main.Main;
import de.souperman.vars.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CMDsetspawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) { return false; }
        if(args.length < 1 || args.length > 2) {
            Player p = (Player) sender;
            p.sendMessage(Vars.UNKNOWN_USAGE+ " /setspawn <where> <'nyp' if you want to ignore your facing angle>");
            return false;
        }
        String path = "spawn."+args[0];
        FileConfiguration config = Main.getPlugin().getConfig();

        if(config.getString(path) == null) {
            Player p = (Player) sender;
            p.sendMessage(Vars.UNKNOWN_USAGE+ " /setspawn <where> <'nyp' if you want to ignore your facing angle>");
            return false;
        }

        Player p = (Player) sender;

        Location loc = p.getLocation();

        config.set(path+".world", Objects.requireNonNull(loc.getWorld()).getName());
        config.set(path+".x", loc.getX());
        config.set(path+".y", loc.getY());
        config.set(path+".z", loc.getZ());
        if(args.length == 2 && args[0].equalsIgnoreCase("nyp")) {
            config.set(path+".yaw", 0.0);
            config.set(path+".pitch", 0.0);
            Vars.LOBBY_SPAWN = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), 0f, 0f);
        } else if(args.length == 1){
            config.set(path+".yaw", loc.getYaw());
            config.set(path+".pitch", loc.getPitch());
            Vars.LOBBY_SPAWN = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        } else {
            p.sendMessage(Vars.UNKNOWN_USAGE+ " /setspawn <where> <'nyp' if you want to ignore your facing angle>");
            return false;
        }

        Main.getPlugin().saveConfig();

        p.sendMessage(Vars.PRFX_SCS+"Spawn set for "+ args[0] +".");
        return false;
    }
}

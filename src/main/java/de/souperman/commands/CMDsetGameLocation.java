package de.souperman.commands;

import de.souperman.main.Main;
import de.souperman.vars.Vars;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CMDsetGameLocation implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only a Player can execute this command.");
            return false;
        }
        Player p = (Player) sender;

        if(!p.hasPermission("cmd.setgamelocation")) {
            p.sendMessage(Vars.PRFX_ERR+"Insufficient permission.");
            return false;
        }

        if(args.length == 0) {
            p.sendMessage(Vars.PRFX_ERR+ "Usage: /setgamelocation <Location Name>");
            return false;
        }

        StringBuilder locName = new StringBuilder("");
        StringBuilder locPathName = new StringBuilder("");
        for(int i = 1; i < args.length; i++) {
            locName.append(" ").append(args[i]);
            locPathName.append(args[i].toLowerCase());
        }

        Location loc = p.getLocation();

        FileConfiguration config = Main.getPlugin().getConfig();
        String basePath = "gamelocations." + locPathName;
        config.set(basePath + ".world", loc.getWorld().getName());
        config.set(basePath + "name", locName);
        config.set(basePath + ".x", loc.getX());
        config.set(basePath + ".y", loc.getY());
        config.set(basePath + ".z", loc.getZ());

        Main.getPlugin().saveConfig();

        p.sendMessage(Vars.PRFX_SCS + "Game location '" + locName + "' saved!");

        return true;
    }
}

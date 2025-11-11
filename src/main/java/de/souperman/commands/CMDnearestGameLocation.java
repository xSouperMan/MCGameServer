package de.souperman.commands;

import de.souperman.vars.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDnearestGameLocation implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Only Players can execute this command.");
            return false;
        }

        Player p = (Player) sender;

        p.sendMessage(Vars.calculateNearestGameLocation(p.getLocation()));
        return false;
    }
}

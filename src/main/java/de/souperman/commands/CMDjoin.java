package de.souperman.commands;

import de.souperman.games.Game;
import de.souperman.main.Lobby;
import de.souperman.vars.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CMDjoin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {return false;}

        Player p = (Player) sender;


        if(args.length == 1) {

            if(!Lobby.getPlayers().contains(p)) {
                p.sendMessage(Vars.PRFX_ERR+" You must be in the lobby to join a game!");
                return false;
            }

            if((args[0].equalsIgnoreCase("survivalgames") || args[0].equalsIgnoreCase("sg")) && Lobby.getPlayers().contains(p)) {

            }


            return false;
        }

        return false;
    }
}

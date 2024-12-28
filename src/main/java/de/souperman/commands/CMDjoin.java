package de.souperman.commands;

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

        // create a gamemode inventory
        Inventory inv = Bukkit.createInventory(p, 36, "§5Select a GameMode");




        if(args.length == 0) {
            p.openInventory(inv);
            return false;
        }



        return false;
    }
}

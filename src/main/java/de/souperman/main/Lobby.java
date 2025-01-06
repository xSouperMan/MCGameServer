package de.souperman.main;

import de.souperman.games.Game;
import de.souperman.vars.Vars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Lobby implements Listener {

    // create a gamemode inventory
    public static Inventory GamemodeSelecter = Bukkit.createInventory(null, 36, "§5Select a GameMode");
    private static ItemStack Navigator = new ItemStack(Material.COMPASS);

    public static void lobbyInit() {

        ItemMeta NavigatorMeta = Navigator.getItemMeta();
        NavigatorMeta.setDisplayName("§bNavigator");
        List<String> lore = new ArrayList<String>();
        lore.add("Rightclick to navigate");
        NavigatorMeta.setLore(lore);
        NavigatorMeta.setMaxStackSize(1);
        Navigator.setItemMeta(NavigatorMeta);


        for(Game game : Vars.games) {
            ItemStack gameStack = new ItemStack(game.getIcon());
            ItemMeta gameStackMeta = gameStack.getItemMeta();
            gameStackMeta.setDisplayName(game.getName());
            List<String> lore2 = new ArrayList<>();
            lore2.add(game.getDescription());
            gameStackMeta.setLore(lore);
            gameStack.setItemMeta(gameStackMeta);
            GamemodeSelecter.addItem(gameStack);
        }

    }

    public static Inventory getGamemodeSelecter() {
        return GamemodeSelecter;
    }

    public static void join(Player p)  {
        p.getInventory().clear();
        p.getInventory().setItem(4, Navigator);
        p.teleport(Vars.GLOBAL_SPAWN);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getWorld() != Vars.GLOBAL_SPAWN.getWorld()) { return; }

        if(p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }

        ItemStack item = p.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            switch (item.getType()) {
                case COMPASS: // Navigator item
                    if(itemMeta.hasLore()) {
                        p.openInventory(GamemodeSelecter);
                    }
                    break;

                default:
                    break;

            }
        }
    }
}

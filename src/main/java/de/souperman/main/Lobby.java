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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lobby implements Listener {

    // create a gamemode inventory
    public static Inventory GamemodeSelecter = Bukkit.createInventory(null, 36, "§5Select a GameMode");
    private static ItemStack Navigator = new ItemStack(Material.COMPASS);
    private static ArrayList<Player> players = new ArrayList<Player>();

    public static void lobbyInit() {

        ItemMeta NavigatorMeta = Navigator.getItemMeta();
        Objects.requireNonNull(NavigatorMeta).setDisplayName("§bNavigator");
        List<String> lore = new ArrayList<String>();
        lore.add("Rightclick to navigate");
        NavigatorMeta.setLore(lore);
        NavigatorMeta.setMaxStackSize(1);
        Navigator.setItemMeta(NavigatorMeta);


        for(Game game : Vars.games) {
            ItemStack gameStack = new ItemStack(game.getIcon());
            ItemMeta gameStackMeta = gameStack.getItemMeta();
            Objects.requireNonNull(gameStackMeta).setDisplayName(game.getName());
            List<String> lore2 = new ArrayList<>();
            lore2.add(game.getDescription());
            gameStackMeta.setLore(lore2);
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
        p.teleport(Vars.LOBBY_SPAWN);
        p.setFoodLevel(20);
        p.setHealth(20);
        players.add(p);
    }

    public static void leave(Player p) {
        players.remove(p);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!players.contains(p)) { return; }

        if(p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }

        ItemStack item = p.getItemInHand();
        if (!item.hasItemMeta()) { return; }
        ItemMeta itemMeta = item.getItemMeta();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            switch (item.getType()) {
                case COMPASS: // Navigator item
                    if(Objects.requireNonNull(itemMeta).hasLore()) {
                        p.openInventory(GamemodeSelecter);
                    }
                    break;

                default:
                    break;

            }
        }
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player) || !e.getView().getTitle().equals("§5Select a GameMode")) { return; }
        Player p = (Player) e.getWhoClicked();
        if(!players.contains(p)) { return; }

        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();

        for(Game game : Vars.games) {
            if(game.getIcon() == mat) {
                if(game.isInProgress()) {
                    p.sendMessage(Vars.PRFX_ERR+"Game already in progress.");
                    return;
                }
                p.closeInventory();
                game.join(p);
                leave(p);
            }
        }
    }
}

package de.souperman.games.survivalgames;

import de.souperman.games.Game;
import de.souperman.main.Main;
import de.souperman.vars.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SurvivalGames extends Game implements Listener {

    private final int playersNeeded;
    private final int maxPlayers;
    private final int fixedCountdown;
    private int countdown;
    private boolean runCountdown;
    private boolean runningCountdown;
    private SGMap map;

    public SurvivalGames() {
        super(Vars.SG_MATERIAL, Vars.SG_NAME, Vars.SG_DESC);
        playersNeeded = 2;
        fixedCountdown = 30;
        runCountdown = false;
        runningCountdown = false;
        maxPlayers = SGMap.getSize();
        this.map = new SGMap("test", 20, new ArrayList<Location>());
    }

    public SurvivalGames(SGMap map) {
        super(Vars.SG_MATERIAL, Vars.SG_NAME, Vars.SG_DESC);
        playersNeeded = 2;
        fixedCountdown = 30;
        runCountdown = false;
        runningCountdown = false;
        maxPlayers = SGMap.getSize();
        this.map = map;
    }

    @Override
    public boolean leave(Player p) {
        if (players.contains(p)) {
            players.remove(p);
            this.sendGameMessage(p.getDisplayName()+" left. ("+ players.size() +"/"+ maxPlayers +")");
            p.teleport(Main.getSpawnWorld().getSpawnLocation());
            if(players.size() < playersNeeded) {
                runCountdown = false;
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean join(Player p) {
        if(!players.contains(p) && players.size() < map.getSize()) {
            players.add(p);

            updateInventory(p);

            if(players.size() >= playersNeeded) {
                if(!runningCountdown) {
                    runCountdown = true;
                    countdown = fixedCountdown;
                    start();
                }
            }
            return true;
        }
        return false;
    }



    private void start() {
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                if(!runCountdown) {
                    runningCountdown = false;
                    this.cancel();
                }
            countdown--;
            }
        };
        run.runTaskTimer(Main.getPlugin(), 0, 20);
    }

    private void updateInventory(Player p) {
        p.getInventory().clear();
        ItemStack MapVote = new ItemStack(Material.PAPER);
        ItemMeta MapVoteMeta = MapVote.getItemMeta();
        MapVoteMeta.setMaxStackSize(1);
        MapVoteMeta.setDisplayName("§aMap Vote");
        List<String> MapVoteLore = MapVoteMeta.getLore();
        MapVoteLore.add("Rightclick to Vote for a Map");

        MapVoteMeta.setLore(MapVoteLore);
        MapVote.setItemMeta(MapVoteMeta);

        p.getInventory().setItem(4, MapVote);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if(item.getType() == Material.PAPER && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("Rightclick to Vote for a Map") && item.getItemMeta().getDisplayName() == "§aMap Vote") {
            Inventory SGinv = Bukkit.createInventory(p, 36);

        }
    }
}

package de.souperman.games.countershot;

import de.souperman.games.Game;
import de.souperman.main.Main;
import de.souperman.vars.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CounterShot extends Game implements Listener {

    private static final int size = 10;
    private static final int playersNeeded = 4;
    private static final int countdown = 60;
    private static int counter;
    private static boolean runCountdown;
    private static boolean runningCountdown;
    private static Inventory teamSelect;
    private static Inventory mapVote;

    public CounterShot() {
        super(Vars.CS_MATERIAL, Vars.CS_NAME, Vars.CS_DESC);
        counter = countdown;
        runCountdown = false;
        runningCountdown = false;

        teamSelect = Bukkit.createInventory(null, 36, "Select Team");

    }

    @Override
    protected boolean leave(Player p) {
        return false;
    }

    @Override
    protected boolean join(Player p) {
        if(!players.contains(p) && players.size() < size) {
            players.add(p);

            if(players.size() >= playersNeeded) {
                if(!runningCountdown) {
                    runCountdown = true;
                    counter = countdown;
                    start();
                }
            }

            return true;
        }
        return false;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private void start() {
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                if(!runCountdown) {
                    runningCountdown = false;
                    this.cancel();
                }
                counter--;
            }
        };
        run.runTaskTimer(Main.getPlugin(), 0, 20);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!players.contains(p)) { return;}

        if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && p.getItemInHand().getType() == Material.PAPER) {

        }
    }

}

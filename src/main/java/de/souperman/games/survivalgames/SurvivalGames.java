package de.souperman.games.survivalgames;

import de.souperman.games.Game;
import de.souperman.main.Main;
import de.souperman.vars.Vars;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SurvivalGames extends Game {

    private final int playersNeeded;
    private final int maxPlayers;
    private final int countdown = 30;
    private boolean runCountdown;
    private boolean runningCountdown;

    public SurvivalGames() {
        super(Vars.SG_MATERIAL, Vars.SG_NAME);
        playersNeeded = 2;
        runCountdown = false;
        runningCountdown = false;
        maxPlayers = 20;
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
        if (!players.contains(p)) {
            players.add(p);
            if(players.size() >= playersNeeded) {
                if(!runningCountdown) {
                    runCountdown = true;
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


            }
        };
        run.runTaskTimer(Main.getPlugin(), 0, 20);
    }
}

package de.souperman.listeners;

import de.souperman.games.Game;
import de.souperman.vars.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EVENTleave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for(Game game : Vars.games) {
            if(game.contains(p)) {
                game.leave(p);
            }
        }
    }
}

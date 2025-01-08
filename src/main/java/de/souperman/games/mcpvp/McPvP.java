package de.souperman.games.mcpvp;

import de.souperman.games.Game;
import de.souperman.vars.Vars;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class McPvP extends Game {

    private static ArrayList<Player> players;

    public McPvP() {
        super(Vars.MCPVP_MATERIAL, Vars.MCPVP_NAME, Vars.MCPVP_DESC);
        players = new ArrayList<Player>();
    }

    @Override
    public boolean leave(Player p) {
        return false;
    }

    @Override
    public boolean join(Player p) {
        return false;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }
}

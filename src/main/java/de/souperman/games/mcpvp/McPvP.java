package de.souperman.games.mcpvp;

import de.souperman.games.Game;
import de.souperman.vars.Vars;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class McPvP extends Game {


    public McPvP() {
        super(Vars.MCPVP_MATERIAL, Vars.MCPVP_NAME);

    }

    @Override
    protected boolean leave(Player p) {
        return false;
    }

    @Override
    protected boolean join(Player p) {
        return false;
    }
}

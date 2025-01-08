package de.souperman.games;

import de.souperman.vars.Vars;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class Game {

    protected boolean inProgress;
    protected int elapsedTime;
    private Material icon;
    private String name;
    private String gameDescription;

    public Game(Material icon, String name, String gameDescription) {
        elapsedTime = 0;
        inProgress = false;
        this.icon = icon;
        this.name = name;
        this.gameDescription = gameDescription;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public abstract boolean leave(Player p);

    public abstract boolean join(Player p);

    public boolean contains(Player p) {
        return getPlayers().contains(p);
    }

    public Material getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {return gameDescription;}

    public abstract ArrayList<Player> getPlayers();

    public void sendGameMessage(String message) {
        for(Player p: getPlayers()) {
            p.sendMessage(message);
        }
    }
}

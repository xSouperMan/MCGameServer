package de.souperman.games;

import de.souperman.vars.Vars;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class Game {

    protected boolean inProgress;
    protected int elapsedTime;
    protected ArrayList<Player> players;
    private Material icon;
    private String name;
    private String gameDescription;

    public Game(Material icon, String name, String gameDescription) {
        Vars.games.add(this);
        elapsedTime = 0;
        inProgress = false;
        players = new ArrayList<Player>();
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

    protected abstract boolean leave(Player p);

    protected abstract boolean join(Player p);

    public boolean contains(Player p) {
        return players.contains(p);
    }

    public Material getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {return gameDescription;}

    public void sendGameMessage(String message) {
        for(Player p: players) {
            p.sendMessage(message);
        }
    }
}

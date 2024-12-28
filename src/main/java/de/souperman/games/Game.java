package de.souperman.games;

import de.souperman.vars.Vars;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class Game {

    private boolean inProgress;
    private int elapsedTime;
    protected ArrayList<Player> players;
    private Material icon;
    private String name;

    public Game(Material icon, String name) {
        Vars.games.add(this);
        elapsedTime = 0;
        inProgress = false;
        players = new ArrayList<Player>();
        this.icon = icon;
        this.name = name;
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

    public void sendGameMessage(String message) {
        for(Player p: players) {
            p.sendMessage(message);
        }
    }
}

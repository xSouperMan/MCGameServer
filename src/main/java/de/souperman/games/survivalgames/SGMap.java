package de.souperman.games.survivalgames;

import de.souperman.main.Main;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class SGMap {
    private static World map;
    private static int size;
    private static ArrayList<Location> spawnLocations;

    public SGMap(String map, int size, ArrayList<Location> spawnLocations) {
        this.map = Main.getPlugin().getServer().getWorld(map);
        this.size = size;
        this.spawnLocations = new ArrayList<Location>();
        for(Location loc : spawnLocations) {
            this.spawnLocations.add(new Location(this.map, loc.getX(), loc.getY(), loc.getZ()));
        }


    }

    public static int getSize() {
        return size;
    }

    public static World getMap() {
        return map;
    }

    public static ArrayList<Location> getSpawnLocations() {
        return spawnLocations;
    }
}

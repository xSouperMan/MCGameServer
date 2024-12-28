package de.souperman.vars;

import de.souperman.games.Game;
import de.souperman.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;

public class Vars {

    public static ArrayList<Game> games = new ArrayList<Game>();

    public static Location GLOBAL_SPAWN = new Location(Main.getSpawnWorld(), 0, 150, 0);

    public static String UNKNOWN_USAGE = "§c! §7Unknown Usage.";
    public static String NO_PERM = "§c! §7Insufficient permission.";



    // Game details

    //Survival Games
    public static String SG_NAME = "Survival Games";
    public static Material SG_MATERIAL = Material.STONE_SWORD;


    //McPvP
    public static String MCPVP_NAME = "McPvP";
    public static Material MCPVP_MATERIAL = Material.MUSHROOM_STEW;

}

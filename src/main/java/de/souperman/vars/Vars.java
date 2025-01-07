package de.souperman.vars;

import de.souperman.games.Game;
import de.souperman.games.countershot.CounterShot;
import de.souperman.games.mcpvp.McPvP;
import de.souperman.games.survivalgames.SGMap;
import de.souperman.games.survivalgames.SurvivalGames;
import de.souperman.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;

public class Vars {

    public static Location GLOBAL_SPAWN = new Location(Main.getSpawnWorld(), 0, 150, 0);

    public static final String PRFX_ERR = "§c!§f"; // Prefix for when error
    public static final String PRFX_SCS = "§a>§f"; // Prefix for when success
    public static String UNKNOWN_USAGE = PRFX_ERR+" Unknown Usage.";
    public static String NO_PERM = PRFX_ERR+" Insufficient permission.";



    // Game details

    public static ArrayList<Game> games = new ArrayList<Game>();

    //Survival Games
    public static String SG_NAME = "Survival Games";
    public static String SG_DESC = "coming soon";
    public static Material SG_MATERIAL = Material.STONE_SWORD;


    //McPvP
    public static String MCPVP_NAME = "McPvP";
    public static String MCPVP_DESC = "coming soon";
    public static Material MCPVP_MATERIAL = Material.MUSHROOM_STEW;


    //CounterShot
    public static String CS_NAME = "Counter Shot";
    public static String CS_DESC = "coming soon";
    public static Material CS_MATERIAL = Material.BOW;


    public static void gamesInit() {
        new SurvivalGames();
        new McPvP();
        new CounterShot();
    }
}

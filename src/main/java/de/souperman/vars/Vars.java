package de.souperman.vars;

import de.souperman.games.Game;
import de.souperman.games.countershot.CounterShot;
import de.souperman.games.mcpvp.McPvP;
import de.souperman.games.survivalgames.SurvivalGames;
import de.souperman.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Objects;

public class Vars {

    public static Location LOBBY_SPAWN;
    private static ArrayList<Location> gameLocations;

    private static FileConfiguration config;

    public static final String PRFX_ERR = "§c!§f "; // Prefix for when error
    public static final String PRFX_SCS = "§a>§f "; // Prefix for when success
    public static final Object PRFX_INFO = "§9?§f ";
    public static String UNKNOWN_USAGE = PRFX_ERR+"Unknown Usage."; // for when wrong command usage
    public static String NO_PERM = PRFX_ERR+"Insufficient permission."; // for when no permission


    // Game details

    public static ArrayList<Game> games = new ArrayList<Game>();

    //Survival Games
    public static String SG_NAME = "Survival Games";
    public static String SG_DESC = "coming soon";
    public static Material SG_MATERIAL = Material.STONE_SWORD;
    public static ArrayList<Location> SG_SPAWNLOCATIONS_MAP1;


    //McPvP
    public static String MCPVP_NAME = "McPvP";
    public static String MCPVP_DESC = "coming soon";
    public static Material MCPVP_MATERIAL = Material.MUSHROOM_STEW;


    //CounterShot
    public static String CS_NAME = "Counter Shot";
    public static String CS_DESC = "coming soon";
    public static Material CS_MATERIAL = Material.BOW;
    public static Location CSLOBBY_SPAWN;


    public static void gamesInit() {
        games.add(new SurvivalGames());
        games.add(new CounterShot());
        games.add(new McPvP());

    }


    public static void varsInit() {

        config = Main.getPlugin().getConfig();
        String LobbyWorldString = config.getString("spawn.lobby.world");
        float LobbyX = (float) config.getInt("spawn.lobby.x");
        float LobbyY = (float) config.getInt("spawn.lobby.x");
        float LobbyZ = (float) config.getInt("spawn.lobby.x");
        float LobbyYaw = (float) config.getDouble("spawn.lobby.yaw");
        float LobbyPitch = (float) config.getDouble("spawn.lobby.pitch");

        if(LobbyWorldString == null) {
            LOBBY_SPAWN = new Location(Main.getSpawnWorld(), 0, 0, 0);
        } else {
            World LobbyWorld = Main.getPlugin().getServer().getWorld(LobbyWorldString);
            LOBBY_SPAWN = new Location(LobbyWorld, LobbyX, LobbyY, LobbyZ, LobbyYaw, LobbyPitch);
        }

        String CsWorldString = config.getString("spawn.cslobby.world");
        float CsX = (float) config.getInt("spawn.cslobby.x");
        float CsY = (float) config.getInt("spawn.cslobby.x");
        float CsZ = (float) config.getInt("spawn.cslobby.x");
        float CsYaw = (float) config.getDouble("spawn.cslobby.yaw");
        float CsPitch = (float) config.getDouble("spawn.cslobby.pitch");

        if(LobbyWorldString == null) {
            CSLOBBY_SPAWN = new Location(Main.getSpawnWorld(), 0, 0, 0);
        } else {
            World CsWorld = Main.getPlugin().getServer().getWorld(CsWorldString);
            CSLOBBY_SPAWN = new Location(CsWorld, CsX, CsY, CsZ, CsYaw, CsPitch);
        }

        gameLocations = new ArrayList<>();


    }

    public static String calculateNearestGameLocation(Location location) {
        String unknown = "Unknown Location";
        double distance = 51f;
        String currentNearest = "";
        if (config.getConfigurationSection("gamelocations") != null) {
            for (String name : config.getConfigurationSection("gamelocations").getKeys(false)) {
                String base = "gamelocations." + name;
                String worldName = config.getString(base + ".world");
                String locName = config.getString(base + ".name");
                double x = config.getDouble(base + ".x");
                double y = config.getDouble(base + ".y");
                double z = config.getDouble(base + ".z");
                if(Bukkit.getWorld(worldName) == null) {
                    continue;
                }
                Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);

                double dist = loc.distance(location);
                if(dist < distance) {
                    distance = dist;
                    currentNearest = locName;
                }
            }
        }

        if(distance < 51d) {
            return currentNearest;
        }
        return unknown;

    }
}

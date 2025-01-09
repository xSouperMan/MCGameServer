package de.souperman.games.countershot;

import de.souperman.games.Game;
import de.souperman.main.Main;
import de.souperman.vars.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CounterShot extends Game implements Listener {

    private static final int size = 10;
    private static final int playersNeeded = 2;
    private static final int countdown = 30;
    private static int counter;
    private static boolean runningCountdown;

    private static int round;

    private static ItemStack teamSelectItem;
    private static Inventory teamSelect;
    private static Inventory mapVote;

    private static ArrayList<Player> t;
    private static ArrayList<Player> ct;
    private static ArrayList<Player> players;

    private static BukkitRunnable run;


    public CounterShot() {
        super(Vars.CS_MATERIAL, Vars.CS_NAME, Vars.CS_DESC);
        counter = countdown;
        runningCountdown = false;
        round = 0;

        players = new ArrayList<Player>();
        t = new ArrayList<Player>();
        ct = new ArrayList<Player>();

        teamSelect = Bukkit.createInventory(null, 27, "Select Team");
        mapVote = Bukkit.createInventory(null, 36, "Pick a map");
        teamSelectItem = new ItemStack(Material.PAPER);

        invInit();
        initRunnable();

    }

    @Override
    public boolean leave(Player p) {
        if(players.contains(p)) {
            players.remove(p);

            if(players.size() < playersNeeded) {
                run.cancel();
                runningCountdown = false;
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean join(Player p) {
        if(!players.contains(p) && players.size() < size && !inProgress) {
            players.add(p);
            p.teleport(Vars.CSLOBBY_SPAWN);

            p.getInventory().setItem(4, new ItemStack(Material.PAPER));
            p.getInventory().setHeldItemSlot(4);

            if(players.size() >= playersNeeded && !runningCountdown) {
                counter = countdown;
                initRunnable();
                run.runTaskTimer(Main.getPlugin(), 0, 20);
                runningCountdown = true;

            }

            return true;
        }
        return false;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private void initRunnable() {
        ArrayList<Player> ps = players;
        run = new BukkitRunnable() {
            @Override
            public void run() {
                if(counter <= 0) {
                    startGame();
                    this.cancel();
                    runningCountdown = false;
                }

                switch (counter) {
                    case 60:
                        sendGameMessage("Game starting in 60 seconds!");
                        break;
                    case 30:
                        sendGameMessage("Game starting in 30 seconds!");
                        break;
                    case 10:
                        sendGameMessage("Game starting in 10 seconds!");
                        break;
                    default:
                        if(counter > 5) {break;}
                        for(Player p : ps) {
                            p.sendTitle( ""+counter, null, 0, 5, 5);
                            p.playNote(p.getLocation(), Instrument.BASS_DRUM, Note.flat(0, Note.Tone.D));
                        }
                        break;
                }

                counter--;
            }
        };
    }

    private void startGame() {
        inProgress = true;
        balanceTeams();
        for(Player p : t) {
            p.playNote(p.getLocation(), Instrument.BIT, Note.flat(0, Note.Tone.A));
            p.teleport(Vars.CSLOBBY_SPAWN);
            p.getInventory().clear();

            Inventory inv = p.getInventory();
            ItemStack glock = new ItemStack(Material.STICK);
            ItemStack shop = new ItemStack(Material.EMERALD);

            inv.setItem(0, glock);
            inv.setItem(8, shop);

        }
        for(Player p : ct) {
            p.playNote(p.getLocation(), Instrument.BIT, Note.flat(0, Note.Tone.A));
            p.teleport(Vars.CSLOBBY_SPAWN);
            p.getInventory().clear();

            Inventory inv = p.getInventory();
            ItemStack usps = new ItemStack(Material.STICK);
            ItemStack shop = new ItemStack(Material.EMERALD);

            inv.setItem(0, usps);
            inv.setItem(8, shop);
        }
    }

    private static void balanceTeams() {
        //TODO
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!players.contains(p)) { return; }

        if(inProgress) {
            if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {



            } else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {

            }

        } else {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && p.getItemInHand().getType() == Material.PAPER) {
                p.openInventory(teamSelect);
            }

        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player)) { return; }
        Player p = (Player) e.getWhoClicked();
        if(!players.contains(p)) {return;}

        if(isInProgress()) {
            switch (e.getView().getTitle()) {
                case "Buy Menu":
                    e.setCancelled(true);
                    Inventory inv = e.getClickedInventory();
                    break;

                default:
                    return;
            }



        } else {
            switch (e.getView().getTitle()) {


                case "Select Team":
                    e.setCancelled(true);

                    ItemStack clicked = e.getCurrentItem();

                    switch (clicked.getType()) {
                        case BOOK:
                        case RED_STAINED_GLASS_PANE:
                            if(ct.contains(p)) {
                                ct.remove(p);
                            }
                            if(t.contains(p)) {
                                p.sendMessage(Vars.PRFX_ERR+"You already set your preference to play as a §4Terrorist§f!");
                            } else {
                                t.add(p);
                                p.sendMessage(Vars.PRFX_SCS+"You set your preference to play as a §4Terrorist§f!");
                            }
                            break;
                        case SHEARS:
                        case BLUE_STAINED_GLASS_PANE:
                            if(t.contains(p)) {
                                t.remove(p);
                            }
                            if(ct.contains(p)) {
                                p.sendMessage(Vars.PRFX_ERR+"You already set your preference to play as a §1Counter-Terrorist§f!");
                            } else {
                                ct.add(p);
                                p.sendMessage(Vars.PRFX_SCS+"You set your preference to play as a §1Counter-Terrorist§f!");
                            }
                            break;
                        case GRAY_STAINED_GLASS_PANE:
                                if(t.contains(p)) {
                                    t.remove(p);
                                    p.sendMessage(Vars.PRFX_SCS+"Preference reset!");
                                } else if(ct.contains(p)) {
                                    ct.remove(p);
                                    p.sendMessage(Vars.PRFX_SCS+"Preference reset!");
                                } else {
                                    p.sendMessage(Vars.PRFX_ERR+"You haven't set any preference yet!");
                                }
                            break;
                        default:
                            break;
                    }
                    break;


                default:
                    return;
            }


        }

    }

    private static void invInit() {

        ItemStack t_icon = new ItemStack(Material.BOOK);
        ItemStack t_pane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemStack ct_icon = new ItemStack(Material.SHEARS);
        ItemStack ct_pane = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta t_iconMeta = t_icon.getItemMeta();
        ItemMeta ct_iconMeta = ct_icon.getItemMeta();
        ItemMeta t_paneMeta = t_pane.getItemMeta();
        ItemMeta ct_paneMeta = ct_pane.getItemMeta();
        t_iconMeta.setDisplayName("§4Terrorists");
        t_paneMeta.setDisplayName("§4Terrorists");
        ct_iconMeta.setDisplayName("§1Counter-Terrorists");
        ct_paneMeta.setDisplayName("§1Counter-Terrorists");
        ArrayList<String> t_iconLore = new ArrayList<String>();
        ArrayList<String> ct_iconLore = new ArrayList<String>();
        t_iconLore.add("§fClick to play as a §4Terrorist");
        ct_iconLore.add("§fClick to play as a §1Counter-Terrorist");
        t_iconMeta.setLore(t_iconLore);
        t_paneMeta.setLore(t_iconLore);
        ct_iconMeta.setLore(ct_iconLore);
        ct_paneMeta.setLore(ct_iconLore);
        t_icon.setItemMeta(t_iconMeta);
        t_pane.setItemMeta(t_paneMeta);
        ct_icon.setItemMeta(ct_iconMeta);
        ct_pane.setItemMeta(ct_paneMeta);

        ItemStack grayPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta grayPaneMeta = grayPane.getItemMeta();
        grayPaneMeta.setDisplayName("Reset Choice");
        ArrayList<String> grayPaneLore = new ArrayList<String>();
        grayPaneLore.add("§fClick to reset your selection");
        grayPaneMeta.setLore(grayPaneLore);
        grayPane.setItemMeta(grayPaneMeta);

        for(int i = 0; i < teamSelect.getSize(); i++) {
            int mod = i % 9;
            switch (mod) {
                case 0:
                case 4:
                case 8:
                    teamSelect.setItem(i, grayPane);
                    break;
                default:
                    if(mod < 4) {
                        teamSelect.setItem(i, t_pane);
                    } else {
                        teamSelect.setItem(i, ct_pane);
                    }
            }
        }

        teamSelect.setItem(11, t_icon);
        teamSelect.setItem(15, ct_icon);
    }

    private static void initWeapons() {

    }

}

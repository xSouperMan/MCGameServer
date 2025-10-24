package de.souperman.games.countershot;

import de.souperman.games.Game;
import de.souperman.main.Main;
import de.souperman.vars.Vars;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CounterShot extends Game implements Listener {

    private static final int size = 10;
    private static final int playersNeeded = 2;
    private static final int countdown = 5;
    private static int counter;
    private static boolean runningCountdown;

    private static final int buyPhaseTime = 20;
    private static final int roundTime = 115;
    private static final int bombTime = 115;
    private static final int gameOverTime = 5;
    private static int phaseCounter;

    private static CSphase phase;
    private static int round;

    private static ItemStack teamSelectItem;
    private static Inventory teamSelect;
    private static Inventory mapVote;
    private static Inventory shopCT;
    private static Inventory shopT;

    private static ArrayList<Player> t;
    private static ArrayList<Player> ct;
    private static ArrayList<Player> players;
    private static ArrayList<CSPlayer> CSplayers;

    private static BukkitRunnable run;
    private static BukkitRunnable gameRun;

    private static BossBar buyPhaseTimerBossBar;


    public CounterShot() { //test
        super(Vars.CS_MATERIAL, Vars.CS_NAME, Vars.CS_DESC);

        Main.getPluginManager().registerEvents(this, Main.getPlugin());

        counter = countdown;
        runningCountdown = false;
        round = 0;
        phaseCounter = 0;
        phase = CSphase.PHASE_LOBBY;

        players = new ArrayList<Player>();
        t = new ArrayList<Player>();
        ct = new ArrayList<Player>();
        CSplayers = new ArrayList<CSPlayer>();

        teamSelect = Bukkit.createInventory(null, 27, "Select Team");
        mapVote = Bukkit.createInventory(null, 36, "Pick a map");
        teamSelectItem = new ItemStack(Material.PAPER);

        buyPhaseTimerBossBar = Bukkit.createBossBar(
               "§a$ §fBuy Phase §a$",
                BarColor.GREEN,
                BarStyle.SOLID
        );
        buyPhaseTimerBossBar.setProgress(0d);
        buyPhaseTimerBossBar.setVisible(false);

        invInit();
        initRunnable();
        initGameRunnable();
    }

    @Override
    public boolean leave(Player p) {
        if(players.contains(p)) {
            players.remove(p);
            buyPhaseTimerBossBar.removePlayer(p);
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
            p.setHealth(20);
            p.setFoodLevel(20);
            buyPhaseTimerBossBar.addPlayer(p);

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

    private void switchPhase(CSphase phse) {
        for(CSPlayer p : CSplayers) {
            p.getPlayer().closeInventory();
        }
        switch (phse) {
            case PHASE_BUY:
                phase = phse;
                buyPhaseTimerBossBar.setVisible(true);
                break;
            case PHASE_GAME:
                phase = phse;
                buyPhaseTimerBossBar.setVisible(false);
                break;
            case PHASE_LOBBY:
                phase = phse;
                buyPhaseTimerBossBar.setVisible(false);
                break;
            default:
                phase = phse;
        }
    }

    private void initGameRunnable() {
        gameRun = new BukkitRunnable() {
            @Override
            public void run() {
                switch (phase) {
                    case PHASE_BUY:
                        buyPhaseTimerBossBar.setProgress((double) phaseCounter /20);
                        switch (phaseCounter) {
                            case 20:
                                for(CSPlayer p : CSplayers) {
                                    p.getPlayer().sendTitle("Buy Phase", "Open the shop to buy equipment!", 0, 160, 40);
                                    p.getPlayer().sendMessage("Current balance: §a"+p.getBalance()+"$");
                                }
                                break;
                            case 0: // buy phase over -> game phase starts
                                sendGameMessage("Buy phase ended.");
                                switchPhase(CSphase.PHASE_GAME);
                                phaseCounter = roundTime + 1;
                                break;
                        }
                        break;
                    case PHASE_GAME:
                        switch (phaseCounter) {
                            case 0: // game time ended
                                sendGameMessage("TEST: GAME ENDED TIME");
                                break;
                        }

                        break;
                    case PHASE_BOMB:
                        break;
                    case PHASE_OVER:
                        break;
                    case PHASE_END:
                        break;
                    default:
                        this.cancel();
                        break;
                }
                phaseCounter--;
            }
        };
    }

    private void startGame() {
        this.inProgress = true;
        balanceTeams();
        fillPlayerInventories();
        phaseCounter = buyPhaseTime;
        switchPhase(CSphase.PHASE_BUY);
        gameRun.runTaskTimer(Main.getPlugin(), 0, 20);

    }

    private static void balanceTeams() {
        if(ct.size() + t.size() == players.size()) {
            int delta = ct.size() - t.size();
            if(Math.abs(delta) > 1) { // if too many players in one of the teams
                if(delta < 0) {
                    while (Math.abs(ct.size() - t.size()) > 1 || ct.isEmpty() || t.isEmpty()) {
                        Player rndm = t.remove(ThreadLocalRandom.current().nextInt(t.size()));
                        ct.add(rndm);
                        t.remove(rndm);
                    }
                } else {
                    while (Math.abs(ct.size() - t.size()) > 1 || ct.isEmpty() || t.isEmpty()) {
                        Player rndm = ct.remove(ThreadLocalRandom.current().nextInt(t.size()));
                        t.add(rndm);
                        ct.remove(rndm);
                    }
                }
            }
        } else {
            for(Player p : players) {
                if(ct.contains(p) || t.contains(p)) { continue; }

                if(ct.size() - t.size() < 0) {
                    ct.add(p);
                } else if(ct.size() - t.size() > 0) {
                    t.add(p);
                } else {
                    Random random = new Random();
                    if(random.nextFloat() < 0.5) {
                        t.add(p);
                    } else {
                        ct.add(p);
                    }
                }
            }
        }
    }

    private void fillPlayerInventories() {
        for(Player p : t) {
            p.playNote(p.getLocation(), Instrument.BIT, Note.flat(0, Note.Tone.A));
            p.teleport(Vars.CSLOBBY_SPAWN);
            p.sendMessage(Vars.PRFX_SCS+"You start as a §4Terrorist§f!");
            p.setDisplayName("§4"+p.getName());
            p.getInventory().clear();

            CSPlayer player = new CSPlayer(p, CSteam.TERRORIST);
            CSplayers.add(player);

            Inventory inv = p.getInventory();
            ItemStack shop = new ItemStack(Material.EMERALD);

            inv.setItem(0, player.getKnifeItem());
            inv.setItem(1, player.getPistolItem());
            inv.setItem(2, player.getSecondaryItem());
            inv.setItem(8, shop);

        }
        for(Player p : ct) {
            p.playNote(p.getLocation(), Instrument.BIT, Note.flat(0, Note.Tone.A));
            p.teleport(Vars.CSLOBBY_SPAWN);
            p.sendMessage(Vars.PRFX_SCS+"You start as a §1Counter-Terrorist§f!");
            p.setDisplayName("§1"+p.getName());
            p.getInventory().clear();

            CSPlayer player = new CSPlayer(p, CSteam.COUNTER_TERRORIST);
            CSplayers.add(player);

            Inventory inv = p.getInventory();
            ItemStack shop = new ItemStack(Material.EMERALD);

            inv.setItem(0, player.getKnifeItem());
            inv.setItem(1, player.getPistolItem());
            inv.setItem(8, shop);
        }
    }

    private CSPlayer getPlayer(Player p) throws Exception {
        for(CSPlayer player : CSplayers) {
            if(player.getPlayer() == p) {
                return player;
            }
        }
        throw new Exception("Player not found");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws Exception {
        Player p = e.getPlayer();
        if(!players.contains(p)) { return; }

        if(this.inProgress) {
            if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                e.setCancelled(true);
                if(phase == CSphase.PHASE_GAME || phase == CSphase.PHASE_BOMB) {
                    switch (p.getItemInHand().getType()) {
                        case STICK:
                            getPlayer(p).getPistol().shoot(getPlayer(p), CSplayers);
                            break;

                        case BREEZE_ROD:
                            getPlayer(p).getSecondary().shoot(getPlayer(p), CSplayers);
                            break;

                        case BONE:
                            getPlayer(p).getKnife().meele(p, CSMeeleType.STRONG);
                            break;

                    }
                } else if(phase == CSphase.PHASE_BUY) {
                    switch (p.getItemInHand().getType()) {
                        case EMERALD:
                            if(ct.contains(p)) {
                                p.openInventory(shopCT);
                            } else if(t.contains(p)) {
                                p.openInventory(shopT);
                            }
                    }
                }

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
        if(players == null) { return; }

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
                            p.closeInventory();
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
                            p.closeInventory();
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
                            p.closeInventory();
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

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(players == null) { return; }

        if(phase != CSphase.PHASE_BUY || !players.contains(e.getPlayer())) {return;}
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to == null) return;

        //player can look around but not move
        if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
            to.setX(from.getX());
            to.setY(from.getY());
            to.setZ(from.getZ());
            e.setTo(to);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if(players == null) { return; }

        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(players.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e) {
        if(players == null) { return; }

        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(players.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    private static void invInit() {

        // TEAM SELECT INVENTORY -----------------------------

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

        //-----------------------------------------

        // SHOPS INVENTORY ------------------------

        shopCT = Bukkit.createInventory(null, 36);
        shopT = Bukkit.createInventory(null, 36);

        //items for both teams
        ItemStack grenade = new ItemStack(Material.AIR); //TODO...

    }

    private static void initWeapons() {

    }

}

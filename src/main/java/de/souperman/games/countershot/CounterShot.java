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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CounterShot extends Game implements Listener {

    private static final int size = 10;
    private static final int playersNeeded = 1;
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

    private static BossBar phaseTimerBossBar;


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

        phaseTimerBossBar = Bukkit.createBossBar(
               "§a$ §fBuy Phase §a$",
                BarColor.GREEN,
                BarStyle.SOLID
        );
        phaseTimerBossBar.setProgress(0d);
        phaseTimerBossBar.setVisible(false);

        invInit();
        initRunnable();
        initGameRunnable();
    }

    @Override
    public boolean leave(Player p) {
        if(players.contains(p)) {
            players.remove(p);
            phaseTimerBossBar.removePlayer(p);
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
            phaseTimerBossBar.addPlayer(p);

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

    private void initGameRunnable() {
        gameRun = new BukkitRunnable() {
            @Override
            public void run() {
                switch (phase) {
                    case PHASE_BUY:
                        phaseTimerBossBar.setProgress((double) phaseCounter / buyPhaseTime);
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
                        phaseTimerBossBar.setProgress((double) phaseCounter / roundTime);
                        switch (phaseCounter) {
                            case 5:
                            case 4:
                            case 3:
                            case 2:
                            case 1:
                                for(CSPlayer p : CSplayers) {
                                    p.getPlayer().playNote(p.getPlayer().getLocation(), Instrument.BIT, Note.natural(2, Note.Tone.C));
                                }
                                break;

                            case 0: // game time ended
                                for(CSPlayer p : CSplayers) {
                                    p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f);
                                }
                                sendGameMessage("TEST: GAME ENDED TIME");
                                break;
                        }

                        break;
                    case PHASE_BOMB:
                        phaseTimerBossBar.setProgress((double) phaseCounter / bombTime);
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

    private void switchPhase(CSphase phse) {
        for(CSPlayer p : CSplayers) {
            p.getPlayer().closeInventory();
        }
        switch (phse) {
            case PHASE_BUY:
                round++;
                phaseTimerBossBar.setVisible(true);
                phaseTimerBossBar.setColor(BarColor.GREEN);
                break;
            case PHASE_GAME:
                phaseTimerBossBar.setVisible(true);
                phaseTimerBossBar.setColor(BarColor.WHITE);
                break;
            case PHASE_LOBBY:
                phaseTimerBossBar.setVisible(false);
                break;
            case PHASE_BOMB:
                phaseTimerBossBar.setVisible(true);
                phaseTimerBossBar.setColor(BarColor.RED);
                break;
            case PHASE_OVER:
                phaseTimerBossBar.setVisible(false);
                break;
        }
        phase = phse;
    }

    private void startGame() {
        this.inProgress = true;
        balanceTeams();
        fillPlayerInventories();
        phaseCounter = buyPhaseTime;
        switchPhase(CSphase.PHASE_BUY);
        round = 1;

        for(CSPlayer player : CSplayers) {
            player.getPlayer().setPlayerListName(player.getStats().getStatsPrefix() + player.getPlayer().getDisplayName());
        }

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
            p.setDisplayName("§4"+p.getName()+ "§f");
            p.getInventory().clear();

            CSPlayer player = new CSPlayer(p, CSteam.TERRORIST);
            CSplayers.add(player);

            Inventory inv = p.getInventory();
            ItemStack shop = new ItemStack(Material.EMERALD);
            ItemMeta shopMeta = shop.getItemMeta();
            shopMeta.setCustomModelData(10000001);
            shopMeta.setDisplayName("§cShop §7- right click to buy");
            shopMeta.setHideTooltip(true);
            shop.setItemMeta(shopMeta);

            inv.setItem(0, player.getKnifeItem());
            inv.setItem(1, player.getPistolItem());
            inv.setItem(2, player.getSecondaryItem());
            inv.setItem(8, shop);
            //TEMPORARY: ---------
            ItemStack c4 = new ItemStack(Material.BOOK);
            ItemMeta c4Meta = c4.getItemMeta();
            c4Meta.setDisplayName("§cBomb");
            c4Meta.setCustomModelData(10000001);
            c4.setItemMeta(c4Meta);
            inv.setItem(5, c4);
            //--------------------
        }
        for(Player p : ct) {
            p.playNote(p.getLocation(), Instrument.BIT, Note.flat(0, Note.Tone.A));
            p.teleport(Vars.CSLOBBY_SPAWN);
            p.sendMessage(Vars.PRFX_SCS+"You start as a §1Counter-Terrorist§f!");
            p.setDisplayName("§1"+p.getName() + "§f");
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
        CSPlayer csplayer;
        try {
            csplayer = getPlayer(p);
        } catch(Exception ex) {
            Bukkit.broadcastMessage("DEBUG: onClick() Method: "+ p.getDisplayName()  +" is not a csplayer");
            return;
        }

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

                case "§aShop - left-click to buy, right-click to sell":
                    e.setCancelled(true);

                    if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR ) {
                        return;
                    }
                    String clickedName = e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase();
                    if(clickedName.contains("usps")) {
                            if(csplayer.getPistol().getType() != CSWeaponType.USPS) {
                                if(csplayer.getBalance() < CSWeaponType.USPS.getCost()) {

                                } else { //TODO: dropped items are reloaded when picked up
                                    p.getWorld().dropItem(p.getLocation(), csplayer.getPistolItem());
                                    csplayer.setPistol(new CSWeapon(CSWeaponType.USPS));
                                    p.getInventory().setItem(1, csplayer.getPistolItem());
                                }
                            }
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

        shopCT = Bukkit.createInventory(null, 54, "§aShop - left-click to buy, right-click to sell");
        shopT = Bukkit.createInventory(null, 54, "§aShop - left-click to buy, right-click to sell");


        //items for both teams
        ItemStack deagle = CSWeaponType.DEAGLE.getItem();
        ItemStack p250 = CSWeaponType.P250.getItem();
        ItemStack nova = CSWeaponType.NOVA.getItem();
        ItemStack xm1015 = CSWeaponType.XM1015.getItem();
        ItemStack p90 = CSWeaponType.P90.getItem();
        ItemStack ssg08 = CSWeaponType.SSG08.getItem();
        ItemStack awp = CSWeaponType.AWP.getItem();

        ItemMeta deagleMeta = deagle.getItemMeta();
        List<String> deagleLore = new ArrayList<>();
        deagleLore.add("§7Price: §a"+CSWeaponType.DEAGLE.getCost());
        deagleLore.add("§7- A lethal headshot at any range");
        deagleLore.add("§7- Consider in eco rounds");
        deagleLore.add("§7- Difficulty: §c☆☆☆☆");
        deagleMeta.setLore(deagleLore);
        deagle.setItemMeta(deagleMeta);

        ItemMeta p250Meta = p250.getItemMeta();
        List<String> p250Lore = new ArrayList<>();
        p250Lore.add("§7Price: §a"+CSWeaponType.P250.getCost());
        p250Lore.add("§7- A popular, if modest, damage upgrade");
        p250Lore.add("§7- Consider in eco rounds");
        p250Lore.add("§7- Difficulty: §a☆");
        p250Meta.setLore(p250Lore);
        p250.setItemMeta(p250Meta);

        ItemMeta novaMeta = nova.getItemMeta();
        List<String> novaLore = new ArrayList<>();
        novaLore.add("§7Price: §a"+CSWeaponType.NOVA.getCost());
        novaLore.add("§7- Versatile shotgun for close quarter combat");
        novaLore.add("§7- Consider in force buy rounds");
        novaLore.add("§7- Difficulty: §e☆☆");
        novaMeta.setLore(novaLore);
        nova.setItemMeta(novaMeta);

        ItemMeta xm1015Meta = xm1015.getItemMeta();
        List<String> xm1015Lore = new ArrayList<>();
        xm1015Lore.add("§7Price: §a"+CSWeaponType.XM1015.getCost());
        xm1015Lore.add("§7- A full-auto rapid-fire monster");
        xm1015Lore.add("§7- Consider in force buy rounds");
        xm1015Lore.add("§7- Difficulty: §a☆");
        xm1015Meta.setLore(xm1015Lore);
        xm1015.setItemMeta(xm1015Meta);

        ItemMeta p90Meta = p90.getItemMeta();
        List<String> p90Lore = new ArrayList<>();
        p90Lore.add("§7Price: §a"+CSWeaponType.P90.getCost());
        p90Lore.add("§7- An endless bullet hose bested only by rifles");
        p90Lore.add("§7- Consider in full buy rounds");
        p90Lore.add("§7- Difficulty: §a☆");
        p90Meta.setLore(p90Lore);
        p90.setItemMeta(p90Meta);

        ItemMeta ssg08Meta = ssg08.getItemMeta();
        List<String> ssg08Lore = new ArrayList<>();
        ssg08Lore.add("§7Price: §a"+CSWeaponType.SSG08.getCost());
        ssg08Lore.add("§7- Light and powerful long-distance damage dealer");
        ssg08Lore.add("§7- Consider in force buy rounds");
        ssg08Lore.add("§7- Difficulty: §c☆☆☆☆");
        ssg08Meta.setLore(ssg08Lore);
        ssg08.setItemMeta(ssg08Meta);

        ItemMeta awpMeta = awp.getItemMeta();
        List<String> awpLore = new ArrayList<>();
        awpLore.add("§7Price: §a"+CSWeaponType.AWP.getCost());
        awpLore.add("§7- Devastating power for the confident sniper");
        awpLore.add("§7- Consider in full buy rounds");
        awpLore.add("§7- Difficulty: §6☆☆☆");
        awpMeta.setLore(awpLore);
        awp.setItemMeta(awpMeta);


        //items for cts
        ItemStack usps = CSWeaponType.USPS.getItem();
        ItemStack fiveseven = CSWeaponType.FIVESEVEN.getItem();
        ItemStack mp9 = CSWeaponType.MP9.getItem();
        ItemStack famas = CSWeaponType.FAMAS.getItem();
        ItemStack aug = CSWeaponType.AUG.getItem();
        ItemStack m4a4 = CSWeaponType.M4A4.getItem();

        ItemMeta uspsMeta = usps.getItemMeta();
        List<String> uspsLore = new ArrayList<>();
        uspsLore.add("§7Price: §a"+CSWeaponType.USPS.getCost());
        uspsLore.add("§7- Precise, silent, and (somewhat) deadly");
        uspsLore.add("§7- Consider in the first round of the half");
        uspsLore.add("§7- Difficulty: §e☆☆");
        uspsMeta.setLore(uspsLore);
        usps.setItemMeta(uspsMeta);

        ItemMeta fivesevenMeta = fiveseven.getItemMeta();
        List<String> fivesevenLore = new ArrayList<>();
        fivesevenLore.add("§7Price: §a"+CSWeaponType.FIVESEVEN.getCost());
        fivesevenLore.add("§7- Power, speed and precision for a price");
        fivesevenLore.add("§7- Consider in eco rounds");
        fivesevenLore.add("§7- Difficulty: §e☆☆");
        fivesevenMeta.setLore(fivesevenLore);
        fiveseven.setItemMeta(fivesevenMeta);

        ItemMeta mp9Meta = mp9.getItemMeta();
        List<String> mp9Lore = new ArrayList<>();
        mp9Lore.add("§7Price: §a"+CSWeaponType.MP9.getCost());
        mp9Lore.add("§7- A burst of damage to deny an enemy rush");
        mp9Lore.add("§7- Consider in the first three rounds of the half");
        mp9Lore.add("§7- Difficulty: §a☆");
        mp9Meta.setLore(mp9Lore);
        mp9.setItemMeta(mp9Meta);

        ItemMeta famasMeta = famas.getItemMeta();
        List<String> famasLore = new ArrayList<>();
        famasLore.add("§7Price: §a"+CSWeaponType.FAMAS.getCost());
        famasLore.add("§7- Cheap but effective against armored enemies");
        famasLore.add("§7- Consider in force buy rounds");
        famasLore.add("§7- Difficulty: §6☆☆☆");
        famasMeta.setLore(famasLore);
        famas.setItemMeta(famasMeta);

        ItemMeta augMeta = aug.getItemMeta();
        List<String> augLore = new ArrayList<>();
        augLore.add("§7Price: §a"+CSWeaponType.AUG.getCost());
        augLore.add("§7- An optional scope for exceptional accuracy");
        augLore.add("§7- Consider in full buy rounds");
        augLore.add("§7- Difficulty: §6☆☆☆");
        augMeta.setLore(augLore);
        aug.setItemMeta(augMeta);

        ItemMeta m4a4Meta = m4a4.getItemMeta();
        List<String> m4a4Lore = new ArrayList<>();
        m4a4Lore.add("§7Price: §a"+CSWeaponType.M4A4.getCost());
        m4a4Lore.add("§7- Good accuracy, with ammo to spare");
        m4a4Lore.add("§7- Consider in full buy rounds");
        m4a4Lore.add("§7- Difficulty: §6☆☆☆");
        m4a4Meta.setLore(m4a4Lore);
        m4a4.setItemMeta(m4a4Meta);


        //items for ts
        ItemStack glock = CSWeaponType.GLOCK.getItem();
        ItemStack tec9 = CSWeaponType.TEC9.getItem();
        ItemStack mac10 = CSWeaponType.MAC10.getItem();
        ItemStack galil = CSWeaponType.GALIL.getItem();
        ItemStack ak47 = CSWeaponType.AK47.getItem();
        ItemStack sg553 = CSWeaponType.SG553.getItem();

        ItemMeta glockMeta = glock.getItemMeta();
        List<String> glockLore = new ArrayList<>();
        glockLore.add("§7Price: §a"+CSWeaponType.GLOCK.getCost());
        glockLore.add("§7- Deadly up close, just a nuisance at range");
        glockLore.add("§7- Consider in the first round of the half");
        glockLore.add("§7- Difficulty: §e☆☆");
        glockMeta.setLore(glockLore);
        glock.setItemMeta(glockMeta);

        ItemMeta tec9Meta = tec9.getItemMeta();
        List<String> tec9Lore = new ArrayList<>();
        tec9Lore.add("§7Price: §a"+CSWeaponType.TEC9.getCost());
        tec9Lore.add("§7- Highly mobile, effective at range and up close");
        tec9Lore.add("§7- Consider in eco rounds");
        tec9Lore.add("§7- Difficulty: §6☆☆☆");
        tec9Meta.setLore(tec9Lore);
        tec9.setItemMeta(tec9Meta);

        ItemMeta mac10Meta = mac10.getItemMeta();
        List<String> mac10Lore = new ArrayList<>();
        mac10Lore.add("§7Price: §a"+CSWeaponType.MAC10.getCost());
        mac10Lore.add("§7- A run and gun tool for breaching bomb sites");
        mac10Lore.add("§7- Consider in the first three rounds of the half");
        mac10Lore.add("§7- Difficulty: §a☆");
        mac10Meta.setLore(mac10Lore);
        mac10.setItemMeta(mac10Meta);

        ItemMeta galilMeta = galil.getItemMeta();
        List<String> galilLore = new ArrayList<>();
        galilLore.add("§7Price: §a"+CSWeaponType.GALIL.getCost());
        galilLore.add("§7- Cheap but effective against armored enemies");
        galilLore.add("§7- Consider in force buy rounds");
        galilLore.add("§7- Difficulty: §6☆☆☆");
        galilMeta.setLore(galilLore);
        galil.setItemMeta(galilMeta);

        ItemMeta ak47Meta = ak47.getItemMeta();
        List<String> ak47Lore = new ArrayList<>();
        ak47Lore.add("§7Price: §a"+CSWeaponType.AK47.getCost());
        ak47Lore.add("§7- The classic, accurate and deadly workhorse");
        ak47Lore.add("§7- Consider in full buy rounds");
        ak47Lore.add("§7- Difficulty: §c☆☆☆☆");
        ak47Meta.setLore(ak47Lore);
        ak47.setItemMeta(ak47Meta);

        ItemMeta sg553Meta = sg553.getItemMeta();
        List<String> sg553Lore = new ArrayList<>();
        sg553Lore.add("§7Price: §a"+CSWeaponType.SG553.getCost());
        sg553Lore.add("§7- A lethal weapon made deadlier with a scope");
        sg553Lore.add("§7- Consider in full buy rounds");
        sg553Lore.add("§7- Difficulty: §6☆☆☆");
        sg553Meta.setLore(sg553Lore);
        sg553.setItemMeta(sg553Meta);



        //fill invs:

        shopCT.setItem(18, usps);
        shopCT.setItem(27, p250);
        shopCT.setItem(36, fiveseven);
        shopCT.setItem(45, deagle);

        shopCT.setItem(20, nova);
        shopCT.setItem(29, xm1015);
        shopCT.setItem(38, p90);
        shopCT.setItem(47, mp9);

        shopCT.setItem(22, famas);
        shopCT.setItem(23, m4a4);
        shopCT.setItem(31, ssg08);
        shopCT.setItem(40, aug);
        shopCT.setItem(49, awp);


        shopT.setItem(18, glock);
        shopT.setItem(27, p250);
        shopT.setItem(36, tec9);
        shopT.setItem(45, deagle);

        shopT.setItem(20, nova);
        shopT.setItem(29, xm1015);
        shopT.setItem(38, p90);
        shopT.setItem(47, mac10);

        shopT.setItem(22, galil);
        shopT.setItem(23, ak47);
        shopT.setItem(31, ssg08);
        shopT.setItem(40, sg553);
        shopT.setItem(49, awp);

    }

    private static void initWeapons() {

    }

}

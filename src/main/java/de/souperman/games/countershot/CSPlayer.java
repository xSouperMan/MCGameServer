package de.souperman.games.countershot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CSPlayer {

    private Player player;
    private double health;
    private int balance;
    private CSWeapon knife;
    private CSWeapon pistol;
    private CSWeapon secondary;
    private CSteam team;
    private ArrayList<CSGrenade> grenades;
    private boolean isAlive;
    private CSStats stats;

    public CSPlayer(Player player, CSteam team) {
        this.team = team;
        this.player = player;
        this.health = 100;
        this.balance = 800;
        this.grenades = new ArrayList<CSGrenade>();
        this.grenades.add(new CSGrenade(CSGrenadeType.NONE));
        this.grenades.add(new CSGrenade(CSGrenadeType.NONE));
        this.grenades.add(new CSGrenade(CSGrenadeType.NONE));
        this.knife = new CSWeapon(CSWeaponType.KNIFE);
        this.isAlive = true;
        this.stats = new CSStats();

        if(team == CSteam.TERRORIST) {
            this.pistol = new CSWeapon(CSWeaponType.GLOCK);
        } else {
            this.pistol = new CSWeapon(CSWeaponType.USPS);
        }
        this.secondary = new CSWeapon(CSWeaponType.AWP);
    }

    public CSteam getTeam() {
        return this.team;
    }

    public void setPistol(CSWeapon pistol) { this.pistol = pistol; }

    public void setAlive(boolean b) { this.isAlive = b; }

    public void addBalance(int amount) {
        balance += amount;
        player.sendMessage("§a+"+amount+"$");
    }

    public void setBalance(int amount) { balance = amount; }

    public CSWeapon getKnife() {
        return knife;
    }

    public CSWeapon getPistol() {
        return pistol;
    }

    public CSWeapon getSecondary() {
        return secondary;
    }

    public ItemStack getKnifeItem() {
        return knife.getItem();
    }

    public ItemStack getPistolItem() {
        return pistol.getItem();
    }

    public ItemStack getSecondaryItem() {
        return secondary.getItem();
    }

    public int getBalance() { return this.balance; }

    public Player getPlayer() {
        return player;
    }

    public boolean isAlive() { return isAlive; }

    public void damage(double damage, CSPlayer damager) {
        if(damage >= health) {
            this.health = 0;
            this.killedBy(damager);
            this.player.damage(0);
            this.player.setHealth(0);

        } else {
            this.health -= damage;
            this.player.damage(0);
            this.player.setHealth(Math.ceil(this.health/100.0 * 20.0));
        }
    }
    public void killedBy(CSPlayer damager) {
        this.setAlive(false);

    }

    public CSStats getStats() {
        return this.stats;
    }
}

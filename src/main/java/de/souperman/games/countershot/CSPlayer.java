package de.souperman.games.countershot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CSPlayer {

    private Player player;
    private int health;
    private int money;
    private CSWeapon knife;
    private CSWeapon pistol;
    private CSWeapon secondary;
    private CSteam team;
    private ArrayList<CSGrenade> grenades;

    public CSPlayer(Player player, CSteam team) {
        this.team = team;
        this.player = player;
        this.money = 800;
        this.grenades = new ArrayList<CSGrenade>();
        this.grenades.add(new CSGrenade(CSGrenadeType.NONE));
        this.grenades.add(new CSGrenade(CSGrenadeType.NONE));
        this.grenades.add(new CSGrenade(CSGrenadeType.NONE));
        this.knife = new CSWeapon(CSWeaponType.KNIFE);
        if(team == CSteam.TERRORIST) {
            this.pistol = new CSWeapon(CSWeaponType.GLOCK);
        } else {
            this.pistol = new CSWeapon(CSWeaponType.USPS);
        }
        this.secondary = new CSWeapon(CSWeaponType.NONE);
    }

    public CSteam getTeam() {
        return this.team;
    }

    public void setPistol(CSWeapon pistol) {

    }

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

    public Player getPlayer() {
        return player;
    }

    public void damage(int damage, CSPlayer damager) {
        if(damage >= health) {
            this.health = 0;
            //TODO: player dies
        } else {
            health -= damage;
        }
    }
}

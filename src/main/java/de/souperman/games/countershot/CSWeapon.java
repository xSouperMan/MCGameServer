package de.souperman.games.countershot;

import de.souperman.main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class CSWeapon {

    private CSWeaponType type;
    private CSWeaponClass weaponClass;
    private ItemStack item;
    private int rounds;
    private int magSize;
    private int mag;
    private int reloadTime; // Ticks
    private int shootDelay; // Ticks
    private int killAward;
    private int damage;
    private float armorPen;
    private float hsMultiply;
    private boolean onDelay = false;
    private boolean onReload = false;


    public CSWeapon(CSWeaponType type) {
        this.type = type;

        ItemStack weapon = new ItemStack(Material.AIR);
        ItemMeta weaponMeta;
        switch (type) {
            case USPS:
                this.rounds = 24;
                this.magSize = 12;
                this.reloadTime = 43;
                this.shootDelay = 4;
                this.killAward = 300;
                this.damage = 35;
                this.armorPen = 0.575f;
                this.hsMultiply = 4.0f;
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("USPS");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.PISTOL;
                break;
            case FIVESEVEN:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 45;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Five-SeveN");
                weaponMeta.setCustomModelData(10000002);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.PISTOL;
                break;
            case GLOCK:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 45;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Glock-18");
                weaponMeta.setCustomModelData(10000003);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.PISTOL;
                break;
            case TEC9:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 51;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Tec-9");
                weaponMeta.setCustomModelData(10000004);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.PISTOL;
                break;
            case DEAGLE:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 44;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Desert Eagle");
                weaponMeta.setCustomModelData(10000005);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.PISTOL;
                break;
            case P250:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 45;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("P250");
                weaponMeta.setCustomModelData(10000006);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.PISTOL;
                break;
            case MP9:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 43;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BLAZE_ROD);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case MAC10:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 51;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Mac-10");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case NOVA:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 95;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Nova");
                weaponMeta.setCustomModelData(10000002);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case XM1015:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 84;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Xm1015");
                weaponMeta.setCustomModelData(10000003);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case P90:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 67;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("P90");
                weaponMeta.setCustomModelData(10000004);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case GALIL:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 61;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Galil AR");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case FAMAS:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 66;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Famas");
                weaponMeta.setCustomModelData(10000002);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case AUG:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 75;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("AUG");
                weaponMeta.setCustomModelData(10000003);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case SG553:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 55;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("SG553");
                weaponMeta.setCustomModelData(10000004);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case M4A4:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 61;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("M4A4");
                weaponMeta.setCustomModelData(10000005);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case AK47:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 49;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("AK-47");
                weaponMeta.setCustomModelData(10000006);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case SSG08:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 74;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("SSG08");
                weaponMeta.setCustomModelData(10000007);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case AWP:
                this.rounds = 7;
                this.magSize = 1;
                this.reloadTime = 73 ;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("AWP");
                weaponMeta.setCustomModelData(10000008);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.SECONDARY;
                break;
            case KNIFE:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 0;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.BONE);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Knife");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                this.weaponClass = CSWeaponClass.KNIFE;
                break;
            default:
                this.rounds = 0;
                this.magSize = 0;
                this.reloadTime = 0;
                this.shootDelay = 0;
                this.killAward = 0;
                this.damage = 0;
                this.armorPen = 0f;
                this.hsMultiply = 0f;
                weapon.setType(Material.AIR);
                this.weaponClass = CSWeaponClass.NONE;
                break;
        }
        this.mag = magSize;
        this.item = weapon;
    }

    public void shoot(Player player) {
        if (this.onDelay) {
            return;
        }

        Particle particle = Particle.ASH;

        if (weaponClass == CSWeaponClass.PISTOL) {
            if (!(type == CSWeaponType.DEAGLE)) {
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 0);
            } else {
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 1, 0);
            }
        } else if (weaponClass == CSWeaponClass.SECONDARY) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 1, 0);
        }

        Location start = player.getEyeLocation();
        Vector direction = start.getDirection();
        double step = 0.5;
        int particleCount = 100;

        for (int i = 0; i < particleCount; i++) {
            Location particleLocation = start.clone().add(((Vector) direction).clone().multiply(i * step));
            player.getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0);
        }

        this.onDelay = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                CSWeapon.this.onDelay = false;
            }
        }.runTaskLater(Main.getPlugin(), shootDelay);
    }

    public void meele(Player p, CSMeeleType meeleType) {
        if(this.weaponClass != CSWeaponClass.KNIFE) {
            return;
        }

    }


    public void reload() {

    }

    public ItemStack getItem() {
        return item;
    }
}

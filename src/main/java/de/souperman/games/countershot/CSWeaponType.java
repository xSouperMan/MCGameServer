package de.souperman.games.countershot;

import org.bukkit.inventory.ItemStack;

public enum CSWeaponType {
    USPS(CSVar.usps_item , CSWeaponClass.PISTOL, 24, 12, 43, 4, 300, 35, 0.575f, 4f, 200),       // CT
    FIVESEVEN(CSVar.fiveseven_item,CSWeaponClass.PISTOL, 0, 0, 45, 0, 0, 0, 0f, 0f, 0),          // CT
    GLOCK(CSVar.glock_item,CSWeaponClass.PISTOL, 120, 20, 45, 0, 0, 30, 0f, 0f, 0),                 // T
    TEC9(CSVar.tec9_item,CSWeaponClass.PISTOL, 0, 0, 51, 0, 0, 0, 0f, 0f, 0),                    // T
    DEAGLE(CSVar.deagle_item,CSWeaponClass.PISTOL, 0, 0, 44, 0, 0, 0, 0f, 0f, 0),                 // BOTH
    P250(CSVar.p250_item, CSWeaponClass.SECONDARY, 0, 0, 45, 0, 0, 0, 0f, 0f, 0),                // BOTH
    MP9(CSVar.mp9_item, CSWeaponClass.SECONDARY, 0, 0, 43, 0, 0, 0, 0f, 0f, 0),                  // CT
    MAC10(CSVar.mac10_item, CSWeaponClass.SECONDARY, 0, 0, 51, 0, 0, 0, 0f, 0f, 0),              // T
    NOVA(CSVar.nova_item, CSWeaponClass.SECONDARY, 0, 0, 95, 0, 0, 0, 0f, 0f, 0),                // BOTH
    XM1015(CSVar.xm1015_item, CSWeaponClass.SECONDARY, 0, 0, 84, 0, 0, 0, 0f, 0f, 0),            // BOTH
    P90(CSVar.p90_item, CSWeaponClass.SECONDARY, 0, 0, 67, 0, 0, 0, 0f, 0f, 0),                  // BOTH
    GALIL(CSVar.galil_item, CSWeaponClass.SECONDARY, 0, 0, 61, 0, 0, 0, 0f, 0f, 0),              // T
    FAMAS(CSVar.famas_item, CSWeaponClass.SECONDARY, 0, 0, 66, 0, 0, 0, 0f, 0f, 0),              // CT
    AUG(CSVar.aug_item, CSWeaponClass.SECONDARY, 0, 0, 75, 0, 0, 0, 0f, 0f, 0),                  // CT
    SG553(CSVar.sg553_item, CSWeaponClass.SECONDARY, 0, 0, 55, 0, 0, 0, 0f, 0f, 0),              // T
    M4A4(CSVar.m4a4_item, CSWeaponClass.SECONDARY, 0, 0, 61, 0, 0, 0, 0f, 0f, 0),                // CT
    AK47(CSVar.ak47_item, CSWeaponClass.SECONDARY, 0, 0, 49, 0, 0, 0, 0f, 0f, 0),                // T
    SSG08(CSVar.ssg08_item, CSWeaponClass.SECONDARY, 0, 0, 74, 0, 0, 0, 0f, 0f, 0),              // BOTH
    AWP(CSVar.awp_item, CSWeaponClass.SECONDARY, 30, 5, 73, 29, 100, 115, 97.5f, 4f, 4750),       // BOTH
    KNIFE(CSVar.knife_item, CSWeaponClass.KNIFE, 0, 0, 0, 0, 0, 0, 0f, 0f, 0),                   // -
    NONE(CSVar.none_item, CSWeaponClass.NONE, 0, 0, 0, 0, 0, 0, 0f, 0f, 0);                      // -

    private final ItemStack item;
    private final CSWeaponClass weaponClass;
    private int rounds;
    private final int magSize;
    private final int reloadTime; // Ticks
    private final int shootDelay; // Ticks
    private final int killAward;
    private final int damage;
    private final float armorPen;
    private final float hsMultiply;
    private final int cost;

    CSWeaponType(ItemStack item, CSWeaponClass weaponClass, int rounds, int magSize, int reloadTime, int shootDelay, int killAward, int damage, float armorPen, float hsMultiply, int cost) {
        this.item = item;
        this.weaponClass = weaponClass;
        this.rounds = rounds;
        this.magSize = magSize;
        this.reloadTime = reloadTime;
        this.shootDelay = shootDelay;
        this.killAward = killAward;
        this.damage = damage;
        this.armorPen = armorPen;
        this.hsMultiply = hsMultiply;
        this.cost = cost;
    }

    public ItemStack getItem() {
        return item;
    }

    public CSWeaponClass getWeaponClass() {
        return weaponClass;
    }

    public int getRounds() {
        return rounds;
    }

    public int getMagSize() {
        return magSize;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getShootDelay() {
        return shootDelay;
    }

    public int getKillAward() {
        return killAward;
    }

    public int getDamage() {
        return damage;
    }

    public float getArmorPen() {
        return armorPen;
    }

    public float getHsMultiply() {
        return hsMultiply;
    }

    public int getCost() {
        return cost;
    }
}

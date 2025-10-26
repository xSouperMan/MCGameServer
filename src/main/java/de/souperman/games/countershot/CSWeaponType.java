package de.souperman.games.countershot;

import org.bukkit.inventory.ItemStack;

public enum CSWeaponType {
    USPS(CSVar.usps_item , CSWeaponClass.PISTOL, 24, 12, 43, 4, 300, 35, 0.575f, 4f, 200),       // CT
    FIVESEVEN(CSVar.fiveseven_item,CSWeaponClass.PISTOL, 100, 20, 45, 3, 300, 32, 0.9115f, 4f, 500),          // CT
    GLOCK(CSVar.glock_item,CSWeaponClass.PISTOL, 120, 20, 45, 3, 300, 30, 0.47f, 4f, 200),                 // T
    TEC9(CSVar.tec9_item,CSWeaponClass.PISTOL, 90, 18, 51, 2, 300, 33, 0.906f, 4f, 500),                    // T
    DEAGLE(CSVar.deagle_item,CSWeaponClass.PISTOL, 35, 7, 44, 5, 300, 53, 0.932f, 3.9f, 700),                 // BOTH
    P250(CSVar.p250_item, CSWeaponClass.SECONDARY, 26, 13, 45, 3, 300, 38, 0.64f, 4f, 300),                // BOTH
    MP9(CSVar.mp9_item, CSWeaponClass.SECONDARY, 120, 30, 43, 1, 600, 26, 0.6f, 4f, 1250),                  // CT
    MAC10(CSVar.mac10_item, CSWeaponClass.SECONDARY, 100, 30, 51, 1, 600, 29, 0.575f, 4f, 1050),              // T
    NOVA(CSVar.nova_item, CSWeaponClass.SECONDARY, 32, 8, 95, 18, 900, 26, 0.5f, 4f, 1050),                // BOTH
    XM1015(CSVar.xm1015_item, CSWeaponClass.SECONDARY, 32, 7, 84, 7, 600, 20, 0.8f, 4f, 2000),            // BOTH
    P90(CSVar.p90_item, CSWeaponClass.SECONDARY, 100, 50, 67, 1, 300, 26, 0.69f, 4f, 2350),                  // BOTH
    GALIL(CSVar.galil_item, CSWeaponClass.SECONDARY, 90, 35, 61, 2, 300, 30, 0.775f, 4f, 1800),              // T
    FAMAS(CSVar.famas_item, CSWeaponClass.SECONDARY, 90, 25, 66, 2, 300, 30, 0.7f, 4f, 1950),              // CT
    AUG(CSVar.aug_item, CSWeaponClass.SECONDARY, 90, 30, 75, 2, 300, 28, 0.9f, 4f, 3300),                  // CT
    SG553(CSVar.sg553_item, CSWeaponClass.SECONDARY, 90, 30, 55, 2, 300, 30, 1f, 4f, 3000),              // T
    M4A4(CSVar.m4a4_item, CSWeaponClass.SECONDARY, 90, 30, 61, 2, 300, 33, 0.7f, 4f, 2900),                // CT
    AK47(CSVar.ak47_item, CSWeaponClass.SECONDARY, 90, 30, 49, 2, 300, 36, 77.5f, 4f, 2700),                // T
    SSG08(CSVar.ssg08_item, CSWeaponClass.SECONDARY, 90, 10, 74, 25, 300, 88, 0.85f, 4f, 1700),              // BOTH
    AWP(CSVar.awp_item, CSWeaponClass.SECONDARY, 30, 5, 73, 29, 100, 115, 0.975f, 4f, 4750),       // BOTH
    KNIFE(CSVar.knife_item, CSWeaponClass.KNIFE, 0, 0, 0, 0, 0, 0, 0f, 0f, 0),                   // -
    NONE(CSVar.none_item, CSWeaponClass.NONE, 0, 0, 0, 0, 0, 0, 0f, 0f, 0);                      // -

    private final ItemStack item;
    private final CSWeaponClass weaponClass;
    private final int rounds;
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
        return item.clone();
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

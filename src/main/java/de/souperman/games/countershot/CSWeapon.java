package de.souperman.games.countershot;

public class CSWeapon {

    private static CSWeaponType type;
    private static int rounds;
    private static int magSize;
    private static float reloadTime;
    private static float shootDelay;


    public CSWeapon(CSWeaponType type) {
        this.type = type;
    }
}

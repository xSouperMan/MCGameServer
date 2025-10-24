package de.souperman.games.countershot;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CSVar {

    public static String killPrefix = "\uD83D\uDD2B";
    public static String deathPrefix = "\uD83D\uDC80";



    public static ItemStack usps_item;
    public static ItemStack fiveseven_item;
    public static ItemStack glock_item;
    public static ItemStack tec9_item;
    public static ItemStack deagle_item;
    public static ItemStack p250_item;
    public static ItemStack mp9_item;
    public static ItemStack mac10_item;
    public static ItemStack nova_item;
    public static ItemStack xm1015_item;
    public static ItemStack p90_item;
    public static ItemStack galil_item;
    public static ItemStack famas_item;
    public static ItemStack aug_item;
    public static ItemStack sg553_item;
    public static ItemStack m4a4_item;
    public static ItemStack ak47_item;
    public static ItemStack ssg08_item;
    public static ItemStack awp_item;
    public static ItemStack knife_item;
    public static ItemStack none_item;


    public static void initCSVars() {
        usps_item = new ItemStack(Material.STICK);
        fiveseven_item = new ItemStack(Material.STICK);
        glock_item = new ItemStack(Material.STICK);
        tec9_item = new ItemStack(Material.STICK);
        deagle_item = new ItemStack(Material.STICK);
        p250_item = new ItemStack(Material.STICK);
        usps_item = new ItemStack(Material.BLAZE_ROD);

        ItemStack weapon = new ItemStack(Material.AIR);
        ItemMeta weaponMeta = weapon.getItemMeta();
        switch (type) {
            case USPS:
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("USPS");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                break;
            case FIVESEVEN:
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Five-SeveN");
                weaponMeta.setCustomModelData(10000002);
                weapon.setItemMeta(weaponMeta);
                break;
            case GLOCK:
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Glock-18");
                weaponMeta.setCustomModelData(10000003);
                weapon.setItemMeta(weaponMeta);
                break;
            case TEC9:
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Tec-9");
                weaponMeta.setCustomModelData(10000004);
                weapon.setItemMeta(weaponMeta);
                break;
            case DEAGLE:
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Desert Eagle");
                weaponMeta.setCustomModelData(10000005);
                weapon.setItemMeta(weaponMeta);
                break;
            case P250:
                weapon.setType(Material.STICK);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("P250");
                weaponMeta.setCustomModelData(10000006);
                weapon.setItemMeta(weaponMeta);
                break;
            case MP9:
                weapon.setType(Material.BLAZE_ROD);
                break;
            case MAC10:
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Mac-10");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                break;
            case NOVA:
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Nova");
                weaponMeta.setCustomModelData(10000002);
                weapon.setItemMeta(weaponMeta);
                break;
            case XM1015:
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Xm1015");
                weaponMeta.setCustomModelData(10000003);
                weapon.setItemMeta(weaponMeta);
                break;
            case P90:
                weapon.setType(Material.BLAZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("P90");
                weaponMeta.setCustomModelData(10000004);
                weapon.setItemMeta(weaponMeta);
                break;
            case GALIL:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Galil AR");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                break;
            case FAMAS:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Famas");
                weaponMeta.setCustomModelData(10000002);
                weapon.setItemMeta(weaponMeta);
                break;
            case AUG:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("AUG");
                weaponMeta.setCustomModelData(10000003);
                weapon.setItemMeta(weaponMeta);
                break;
            case SG553:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("SG553");
                weaponMeta.setCustomModelData(10000004);
                weapon.setItemMeta(weaponMeta);
                break;
            case M4A4:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("M4A4");
                weaponMeta.setCustomModelData(10000005);
                weapon.setItemMeta(weaponMeta);
                break;
            case AK47:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("AK-47");
                weaponMeta.setCustomModelData(10000006);
                weapon.setItemMeta(weaponMeta);
                break;
            case SSG08:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("SSG08");
                weaponMeta.setCustomModelData(10000007);
                weapon.setItemMeta(weaponMeta);
                break;
            case AWP:
                weapon.setType(Material.BREEZE_ROD);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("AWP");
                weaponMeta.setCustomModelData(10000008);
                weapon.setItemMeta(weaponMeta);
                break;
            case KNIFE:
                weapon.setType(Material.BONE);
                weaponMeta = weapon.getItemMeta();
                weaponMeta.setDisplayName("Knife");
                weaponMeta.setCustomModelData(10000001);
                weapon.setItemMeta(weaponMeta);
                break;
            default:
                weapon.setType(Material.AIR);
                break;
        }
    }

}

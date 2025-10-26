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
        mp9_item = new ItemStack(Material.BLAZE_ROD);
        mac10_item = new ItemStack(Material.BLAZE_ROD);
        nova_item = new ItemStack(Material.BLAZE_ROD);
        xm1015_item = new ItemStack(Material.BLAZE_ROD);
        p90_item = new ItemStack(Material.BLAZE_ROD);
        galil_item = new ItemStack(Material.BREEZE_ROD);
        famas_item = new ItemStack(Material.BREEZE_ROD);
        aug_item = new ItemStack(Material.BREEZE_ROD);
        sg553_item = new ItemStack(Material.BREEZE_ROD);
        m4a4_item = new ItemStack(Material.BREEZE_ROD);
        ak47_item = new ItemStack(Material.BREEZE_ROD);
        ssg08_item = new ItemStack(Material.BREEZE_ROD);
        awp_item = new ItemStack(Material.BREEZE_ROD);
        knife_item = new ItemStack(Material.BONE);
        none_item = new ItemStack(Material.AIR);

        ItemMeta uspsItemItemMeta = usps_item.getItemMeta();
        uspsItemItemMeta.setDisplayName("§f§lUSPS");
        uspsItemItemMeta.setCustomModelData(10000001);
        usps_item.setItemMeta(uspsItemItemMeta);

        fiveseven_item.setType(Material.STICK);
        ItemMeta fivesevenItemItemMeta = fiveseven_item.getItemMeta();
        fivesevenItemItemMeta.setDisplayName("§f§lFive-SeveN");
        fivesevenItemItemMeta.setCustomModelData(10000002);
        fiveseven_item.setItemMeta(fivesevenItemItemMeta);


        glock_item.setType(Material.STICK);
        ItemMeta glockItemItemItemMeta = glock_item.getItemMeta();
        glockItemItemItemMeta.setDisplayName("§f§lGlock-18");
        glockItemItemItemMeta.setCustomModelData(10000003);
        glock_item.setItemMeta(glockItemItemItemMeta);


        tec9_item.setType(Material.STICK);
        ItemMeta tec9ItemItemMeta = tec9_item.getItemMeta();
        tec9ItemItemMeta.setDisplayName("§f§lTec-9");
        tec9ItemItemMeta.setCustomModelData(10000004);
        tec9_item.setItemMeta(tec9ItemItemMeta);


        deagle_item.setType(Material.STICK);
        ItemMeta deagleItemItemMeta = deagle_item.getItemMeta();
        deagleItemItemMeta.setDisplayName("§f§lDesert Eagle");
        deagleItemItemMeta.setCustomModelData(10000005);
        deagle_item.setItemMeta(deagleItemItemMeta);


        p250_item.setType(Material.STICK);
        ItemMeta p250ItemItemMeta = p250_item.getItemMeta();
        p250ItemItemMeta.setDisplayName("§f§lP250");
        p250ItemItemMeta.setCustomModelData(10000006);
        p250_item.setItemMeta(p250ItemItemMeta);


        mp9_item.setType(Material.BLAZE_ROD);
        ItemMeta mp9ItemItemMeta = mp9_item.getItemMeta();
        mp9ItemItemMeta.setDisplayName("§f§lMP9");
        mp9ItemItemMeta.setCustomModelData(10000007);
        mp9_item.setItemMeta(mp9ItemItemMeta);


        mac10_item.setType(Material.BLAZE_ROD);
        ItemMeta mac10ItemItemMeta = mac10_item.getItemMeta();
        mac10ItemItemMeta.setDisplayName("§f§lMac-10");
        mac10ItemItemMeta.setCustomModelData(10000008);
        mac10_item.setItemMeta(mac10ItemItemMeta);


        nova_item.setType(Material.BLAZE_ROD);
        ItemMeta novaItemItemMeta = nova_item.getItemMeta();
        novaItemItemMeta.setDisplayName("§f§lNova");
        novaItemItemMeta.setCustomModelData(10000009);
        nova_item.setItemMeta(novaItemItemMeta);


        xm1015_item.setType(Material.BLAZE_ROD);
        ItemMeta xm1015ItemItemMeta = xm1015_item.getItemMeta();
        xm1015ItemItemMeta.setDisplayName("§f§lXm1015");
        xm1015ItemItemMeta.setCustomModelData(10000010);
        xm1015_item.setItemMeta(xm1015ItemItemMeta);


        p90_item.setType(Material.BLAZE_ROD);
        ItemMeta p90ItemItemMeta = p90_item.getItemMeta();
        p90ItemItemMeta.setDisplayName("§f§lP90");
        p90ItemItemMeta.setCustomModelData(10000011);
        p90_item.setItemMeta(p90ItemItemMeta);


        galil_item.setType(Material.BREEZE_ROD);
        ItemMeta galilItemItemMeta = galil_item.getItemMeta();
        galilItemItemMeta.setDisplayName("§f§lGalil AR");
        galilItemItemMeta.setCustomModelData(10000012);
        galil_item.setItemMeta(galilItemItemMeta);


        famas_item.setType(Material.BREEZE_ROD);
        ItemMeta famasItemItemMeta = famas_item.getItemMeta();
        famasItemItemMeta.setDisplayName("§f§lFamas");
        famasItemItemMeta.setCustomModelData(10000013);
        famas_item.setItemMeta(famasItemItemMeta);


        aug_item.setType(Material.BREEZE_ROD);
        ItemMeta augItemItemMeta = aug_item.getItemMeta();
        augItemItemMeta.setDisplayName("§f§lAUG");
        augItemItemMeta.setCustomModelData(10000014);
        aug_item.setItemMeta(augItemItemMeta);


        sg553_item.setType(Material.BREEZE_ROD);
        ItemMeta sg553ItemItemMeta = sg553_item.getItemMeta();
        sg553ItemItemMeta.setDisplayName("§f§lSG553");
        sg553ItemItemMeta.setCustomModelData(10000015);
        sg553_item.setItemMeta(sg553ItemItemMeta);


        m4a4_item.setType(Material.BREEZE_ROD);
        ItemMeta m4a4ItemItemMeta = m4a4_item.getItemMeta();
        m4a4ItemItemMeta.setDisplayName("§f§lM4A4");
        m4a4ItemItemMeta.setCustomModelData(10000016);
        m4a4_item.setItemMeta(m4a4ItemItemMeta);


        ak47_item.setType(Material.BREEZE_ROD);
        ItemMeta ak47ItemItemMeta = ak47_item.getItemMeta();
        ak47ItemItemMeta.setDisplayName("§f§lAK-47");
        ak47ItemItemMeta.setCustomModelData(10000017);
        ak47_item.setItemMeta(ak47ItemItemMeta);


        ssg08_item.setType(Material.BREEZE_ROD);
        ItemMeta ssg08ItemItemMeta = ssg08_item.getItemMeta();
        ssg08ItemItemMeta.setDisplayName("§f§lSSG08");
        ssg08ItemItemMeta.setCustomModelData(10000018);
        ssg08_item.setItemMeta(ssg08ItemItemMeta);


        awp_item.setType(Material.BREEZE_ROD);
        ItemMeta awpItemItemMeta = awp_item.getItemMeta();
        awpItemItemMeta.setDisplayName("§f§lAWP");
        awpItemItemMeta.setCustomModelData(10000019);
        awp_item.setItemMeta(awpItemItemMeta);


        knife_item.setType(Material.BONE);
        ItemMeta knifeItemItemMeta = knife_item.getItemMeta();
        knifeItemItemMeta.setDisplayName("§f§lKnife");
        knifeItemItemMeta.setCustomModelData(10000020);
        knife_item.setItemMeta(knifeItemItemMeta);

    }

}

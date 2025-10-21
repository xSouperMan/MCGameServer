package de.souperman.games.countershot;

import de.souperman.main.Main;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;

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
                this.damage = 30;
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
                this.damage = 115;
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

    private static BoundingBox headBox(BoundingBox body) {
        double h = body.getMaxY() - body.getMinY();
        double headH = Math.max(0.25, 0.30 * h);
        double minY = Math.max(body.getMinY(), body.getMaxY() - headH);
        return new BoundingBox(
                body.getMinX(), minY, body.getMinZ(),
                body.getMaxX(), body.getMaxY(), body.getMaxZ()
        );
    }

    private ArrayList<ShotHit> calculatePlayersHitByShot(CSPlayer player, ArrayList<CSPlayer> players, Location start, Vector direction, double distance) {
        Vector o = start.toVector();
        Vector d = direction.clone();
        double len = d.length();
        ArrayList<ShotHit> hits = new ArrayList<>();
        if (len == 0.0) return hits;
        d.multiply(1.0 / len);

        for (CSPlayer p : players) {
            if (p == null || !p.isAlive() || p == player) continue;
            Player bp = p.getPlayer();

            BoundingBox body = bp.getBoundingBox();
            BoundingBox head = headBox(body);

            Double tHead = rayAabbHitT(o, d, head, distance);
            if (tHead != null && tHead >= 0.0 && tHead <= distance) {
                hits.add(new ShotHit(p, tHead, true));
                continue;
            }

            Double tBody = rayAabbHitT(o, d, body, distance);
            if (tBody != null && tBody >= 0.0 && tBody <= distance) {
                hits.add(new ShotHit(p, tBody, false));
            }
        }
        return hits;
    }

    public void shoot(CSPlayer csplayer, ArrayList<CSPlayer> players) {
        if (this.onDelay) return;

        Player player = csplayer.getPlayer();
        Particle particle = Particle.ASH;
        Location start = player.getEyeLocation();
        Vector direction = start.getDirection();
        double step = 0.5;
        int particleCount = 400;
        Location loc = player.getLocation();


        if (weaponClass == CSWeaponClass.PISTOL) {

            switch (type) {
                case USPS:
                    player.getWorld().playSound(loc, Sound.ENTITY_IRON_GOLEM_DAMAGE, 1f, 2f);
                    break;
                case DEAGLE:
                    player.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                    break;
                default:
                    player.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                    break;
            }

        } else if (weaponClass == CSWeaponClass.SECONDARY) {

            switch (type) {
                case AWP:
                    player.playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                    player.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.2f);
                    player.playSound(loc, Sound.BLOCK_ANVIL_PLACE, 0.2f, 2f);
                    break;
                default:
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
                    break;
            }

        }


        player.getWorld().spawnParticle(Particle.POOF, player.getEyeLocation(), 1, 0, 0, 0);
        int maxdist = (int) Math.round(particleCount * step);
        double drawDist = firstSolidBlockDistance(start, direction, maxdist);
        for (int i = 1; i < drawDist / step; i++) {
            Location particleLocation = start.clone().add(direction.clone().multiply(i * step));
            player.getWorld().spawnParticle(particle, particleLocation, 1, 0, 0, 0);
        }

        RayTraceResult rr = start.getWorld().rayTraceBlocks(start, direction, maxdist, FluidCollisionMode.NEVER, true);
        if (rr != null && rr.getHitBlock() != null) {
            BlockData data = rr.getHitBlock().getBlockData();
            Location hitLoc = rr.getHitPosition().toLocation(player.getWorld());
            int count = Math.max(1, damage / 2);
            player.getWorld().spawnParticle(Particle.BLOCK, hitLoc, count, 0.35, 0.35, 0.35, 0.12, data);
        }
        ArrayList<ShotHit> hits = calculatePlayersHitByShot(csplayer, players, start, direction, drawDist);
        hits.sort((a, b) -> Double.compare(a.t, b.t));

        double baseDamage = this.damage;
        double minFactor = 0.15;
        for (int i = 0; i < hits.size(); i++) {
            ShotHit h = hits.get(i);
            CSPlayer victim = h.target;
            double rankFactor = Math.max(minFactor, Math.pow(0.8, i));
            double hsFactor = h.headshot ? this.hsMultiply : 1.0;
            double dealt = baseDamage * rankFactor * hsFactor;
            victim.damage(dealt, csplayer);
        }

        this.onDelay = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                CSWeapon.this.onDelay = false;
            }
        }.runTaskLater(Main.getPlugin(), shootDelay);
    }

    private double firstSolidBlockDistance(Location start, Vector direction, double maxDist) {
        RayTraceResult r = start.getWorld().rayTraceBlocks(start, direction, maxDist, FluidCollisionMode.NEVER, true);
        if (r != null && r.getHitBlock() != null) {
            Vector hit = r.getHitPosition();
            return hit.clone().subtract(start.toVector()).length();
        }
        return maxDist;
    }

    private Double rayAabbHitT(Vector o, Vector dUnit, org.bukkit.util.BoundingBox bb, double wallHit) {
        final double EPS = 1e-12;

        double ox = o.getX(), oy = o.getY(), oz = o.getZ();
        double dx = dUnit.getX(), dy = dUnit.getY(), dz = dUnit.getZ();

        double tmin = 0.0;
        double tmax = wallHit;

        // X
        if (!slab(ox, dx, bb.getMinX(), bb.getMaxX())) return null;
        double[] tx = slabTs(ox, dx, bb.getMinX(), bb.getMaxX());
        tmin = Math.max(tmin, tx[0]); tmax = Math.min(tmax, tx[1]);
        if (tmin > tmax) return null;

        // Y
        if (!slab(oy, dy, bb.getMinY(), bb.getMaxY())) return null;
        double[] ty = slabTs(oy, dy, bb.getMinY(), bb.getMaxY());
        tmin = Math.max(tmin, ty[0]); tmax = Math.min(tmax, ty[1]);
        if (tmin > tmax) return null;

        // Z
        if (!slab(oz, dz, bb.getMinZ(), bb.getMaxZ())) return null;
        double[] tz = slabTs(oz, dz, bb.getMinZ(), bb.getMaxZ());
        tmin = Math.max(tmin, tz[0]); tmax = Math.min(tmax, tz[1]);
        if (tmin > tmax) return null;

        double tHit = (tmin >= 0.0) ? tmin : tmax;
        return (tHit >= 0.0 && tHit <= wallHit) ? tHit : null;
    }

    private static boolean slab(double o, double d, double min, double max) {
        if (Math.abs(d) < 1e-12) return (o >= min && o <= max);
        return true;
    }

    private static double[] slabTs(double o, double d, double min, double max) {
        if (Math.abs(d) < 1e-12) return new double[]{Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
        double inv = 1.0 / d;
        double t1 = (min - o) * inv;
        double t2 = (max - o) * inv;
        if (t1 > t2) { double tmp = t1; t1 = t2; t2 = tmp; }
        return new double[]{t1, t2};
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

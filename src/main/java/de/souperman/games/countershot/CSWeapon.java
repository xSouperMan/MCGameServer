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
    private int mag;
    private boolean onDelay = false;
    private boolean onReload = false;

    public CSWeapon(CSWeaponType type) {
        this.type = type;
        this.mag = this.type.getMagSize();
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

    private ArrayList<CSShotHit> calculatePlayersHitByShot(CSPlayer player, ArrayList<CSPlayer> players, Location start, Vector direction, double distance) {
        Vector o = start.toVector();
        Vector d = direction.clone();
        double len = d.length();
        ArrayList<CSShotHit> hits = new ArrayList<>();
        if (len == 0.0) return hits;
        d.multiply(1.0 / len);

        for (CSPlayer p : players) {
            if (p == null || !p.isAlive() || p == player) continue;
            Player bp = p.getPlayer();

            BoundingBox body = bp.getBoundingBox();
            BoundingBox head = headBox(body);

            Double tHead = rayAabbHitT(o, d, head, distance);
            if (tHead != null && tHead >= 0.0 && tHead <= distance) {
                hits.add(new CSShotHit(p, tHead, true));
                continue;
            }

            Double tBody = rayAabbHitT(o, d, body, distance);
            if (tBody != null && tBody >= 0.0 && tBody <= distance) {
                hits.add(new CSShotHit(p, tBody, false));
            }
        }
        return hits;
    }





    public void shoot(CSPlayer csplayer, ArrayList<CSPlayer> players) {
        if (this.onDelay || this.onReload) return;

        if(this.mag <= 0) {
            csplayer.getPlayer().playSound(csplayer.getPlayer().getLocation(), Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 1f, 1f);
            return;
        }

        Player player = csplayer.getPlayer();
        Particle particle = Particle.ASH;
        Location start = player.getEyeLocation();
        Vector direction = start.getDirection();
        double step = 0.5;
        int particleCount = 400;
        Location loc = player.getLocation();


        if (this.type.getWeaponClass() == CSWeaponClass.PISTOL) {

            switch (type) {
                case USPS:
                    player.playNote(player.getLocation(), Instrument.BASS_DRUM, Note.flat(0, Note.Tone.D));
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_FRAME_PLACE, 1f, 1f);
                    break;
                case DEAGLE:
                    player.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                    break;
                default:
                    player.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                    break;
            }

        } else if (this.type.getWeaponClass() == CSWeaponClass.SECONDARY) {

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
            int count = Math.max(1, this.type.getDamage() / 2);
            player.getWorld().spawnParticle(Particle.BLOCK, hitLoc, count, 0.35, 0.35, 0.35, 0.12, data);
        }
        ArrayList<CSShotHit> hits = calculatePlayersHitByShot(csplayer, players, start, direction, drawDist);
        hits.sort((a, b) -> Double.compare(a.t, b.t));

        double baseDamage = this.type.getDamage();
        double minFactor = 0.15;
        for (int i = 0; i < hits.size(); i++) {
            CSShotHit h = hits.get(i);
            CSPlayer victim = h.target;
            double rankFactor = Math.max(minFactor, Math.pow(0.8, i));
            double hsFactor = h.headshot ? this.type.getHsMultiply() : 1.0;
            double dealt = baseDamage * rankFactor * hsFactor;
            victim.damage(dealt, csplayer);
        }

        this.onDelay = true;
        if(this.mag > 0) {
            this.mag--;
        }
        delay();
    }




    private void delay() {
        new BukkitRunnable() {
            @Override
            public void run() {
                onDelay = false;
            }
        }.runTaskLater(Main.getPlugin(), this.type.getShootDelay());
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
        if(this.type.getWeaponClass() != CSWeaponClass.KNIFE) {
            return;
        }

    }






    public void reload() {

    }





    public ItemStack getItem() {
        return this.type.getItem();
    }
}

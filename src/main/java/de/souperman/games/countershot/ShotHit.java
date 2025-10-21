package de.souperman.games.countershot;

public class ShotHit {
    final CSPlayer target;
    final double t;
    final boolean headshot;
    ShotHit(CSPlayer target, double t, boolean headshot) {
        this.target = target; this.t = t; this.headshot = headshot;
    }
}

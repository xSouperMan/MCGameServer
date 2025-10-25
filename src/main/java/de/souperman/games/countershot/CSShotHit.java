package de.souperman.games.countershot;

public class CSShotHit {
    final CSPlayer target;
    final double t;
    final boolean headshot;
    CSShotHit(CSPlayer target, double t, boolean headshot) {
        this.target = target; this.t = t; this.headshot = headshot;
    }
}

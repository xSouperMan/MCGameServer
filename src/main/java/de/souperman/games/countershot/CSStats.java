package de.souperman.games.countershot;

public class CSStats {

    private int kills;
    private int deaths;
    private int headshot; //not used yet

    public CSStats() {
        this.kills = 0;
        this.deaths = 0;
        this.headshot = 0;

    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getHeadshot() {
        return this.headshot;
    }

    public void addKill() {
        this.kills++;
    }

    public void addKills(int amount) {
        this.kills += amount;
    }

    public void addDeath() {
        this.deaths++;
    }

    public void addDeaths(int amount) {
        this.deaths +=amount;
    }

    public String getStatsPrefix() {
        return ("| " + CSVar.killPrefix +":"+ this.kills + " | " + CSVar.deathPrefix +":"+ this.deaths + " | ");
    }
}

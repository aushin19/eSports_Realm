package com.cornerdesk.esportrealm.Helper;

public class OngoingMatchesItem {

    public String Map, Mode, MatchDate, Status;
    public int PrizePool, PerKill, Contestant;
    public String YT_Link;

    public OngoingMatchesItem(String map, String mode, String matchDate, int prizePool, int perKill, int contestant, String status, String yt_link) {
        Map = map;
        Mode = mode;
        MatchDate = matchDate;
        PrizePool = prizePool;
        PerKill = perKill;
        Contestant = contestant;
        Status = status;
        YT_Link = yt_link;
    }

    public String getMap() {
        return Map;
    }

    public String getMode() {
        return Mode;
    }

    public String getMatchDate() {
        return MatchDate;
    }

    public int getPrizePool() {
        return PrizePool;
    }

    public int getPerKill() {
        return PerKill;
    }

    public int getContestant() {
        return Contestant;
    }

    public String getStatus() {
        return Status;
    }

    public String getYT_Link() {
        return YT_Link;
    }
}

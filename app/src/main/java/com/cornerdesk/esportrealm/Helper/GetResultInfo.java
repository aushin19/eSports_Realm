package com.cornerdesk.esportrealm.Helper;

public class GetResultInfo {

    public String Map, Mode, MatchDate, Status, First, Second, Third, firstPrize, secondPrize, thirdPrize;
    public int PrizePool, PerKill;

    public GetResultInfo(String map, String mode, String matchDate, String status, String first, String second, String third, String firstPrize, String secondPrize, String thirdPrize, int prizePool, int perKill) {
        Map = map;
        Mode = mode;
        MatchDate = matchDate;
        Status = status;
        First = first;
        Second = second;
        Third = third;
        PrizePool = prizePool;
        PerKill = perKill;
        this.firstPrize = firstPrize;
        this.secondPrize = secondPrize;
        this.thirdPrize = thirdPrize;
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

    public String getStatus() {
        return Status;
    }

    public String getFirst() {
        return First;
    }

    public String getSecond() {
        return Second;
    }

    public String getThird() {
        return Third;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public String getSecondPrize() {
        return secondPrize;
    }

    public String getThirdPrize() {
        return thirdPrize;
    }
}

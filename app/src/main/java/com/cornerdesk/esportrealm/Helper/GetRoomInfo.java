package com.cornerdesk.esportrealm.Helper;

public class GetRoomInfo {

    public String matchName = "NULL", matchID, matchPass;

    public GetRoomInfo() {}

    public GetRoomInfo(String matchName, String matchID, String matchPass) {
        this.matchName = matchName;
        this.matchID = matchID;
        this.matchPass = matchPass;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getMatchPass() {
        return matchPass;
    }

    public void setMatchPass(String matchPass) {
        this.matchPass = matchPass;
    }
}

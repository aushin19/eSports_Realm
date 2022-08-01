package com.cornerdesk.esportrealm.Helper;

public class GetGameCardInfo {

    public String info;
    public int spotsLeft;
    public String docID;

    public GetGameCardInfo(){}

    public GetGameCardInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getSpotsLeft() {
        return spotsLeft;
    }

    public void setSpotsLeft(int spotsLeft) {
        this.spotsLeft = spotsLeft;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

}

package com.cornerdesk.esportrealm.Helper;

import android.content.Context;

public class addContest {

    static Context ctx;

    public addContest(Context ctx){
        this.ctx = ctx;
    }

    public void setContest(String contestName){
        TinyDB tb = new TinyDB(ctx);
        tb.putString(contestName, contestName);
    }

    public static boolean getContest(String contestName){
        TinyDB tb = new TinyDB(ctx);
        if(tb.getString(contestName).equals(contestName))
            return true;
        return false;
    }

}

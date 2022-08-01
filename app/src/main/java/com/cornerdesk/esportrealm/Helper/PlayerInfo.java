package com.cornerdesk.esportrealm.Helper;

import android.content.Context;

public class PlayerInfo {
    public static TinyDB tinyDB;

    public static String getName(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getString("playerName");
    }

    public static String getMailID(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getString("playerMailID");
    }

    public static void setName(Context ctx, String name){
        tinyDB = new TinyDB(ctx);
        tinyDB.putString("playerName", name);
    }

    public static void setMailID(Context ctx, String mailID){
        tinyDB = new TinyDB(ctx);
        tinyDB.putString("playerMailID", mailID);
    }

    public static String getRefCode(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getString("playerRefCode");
    }

    public static void setRefCode(Context ctx, String refCode){
        tinyDB = new TinyDB(ctx);
        tinyDB.putString("playerRefCode", refCode);
    }

    public static String getRefBy(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getString("playerRefBy");
    }

    public static void setRefBy(Context ctx, String refBy){
        tinyDB = new TinyDB(ctx);
        tinyDB.putString("playerRefBy", refBy);
    }

    public static int getCoin(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getInt("playerCoin");
    }

    public static void setCoin(Context ctx, int coin){
        tinyDB = new TinyDB(ctx);
        tinyDB.putInt("playerCoin", coin);
    }

    public static int getWallet(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getInt("playerWallet");
    }

    public static void setWallet(Context ctx, int wallet){
        tinyDB = new TinyDB(ctx);
        tinyDB.putInt("playerWallet", wallet);
    }

    public static String getScratchDate(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getString("scratchDate");
    }

    public static void setScratchDate(Context ctx, String todayDate){
        tinyDB = new TinyDB(ctx);
        tinyDB.putString("scratchDate", todayDate);
    }

}

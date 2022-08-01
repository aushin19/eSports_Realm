package com.cornerdesk.esportrealm.Helper;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionInfo {
    public static String date;
    public static String month;
    public static TinyDB tinyDB;

    public static void addTransaction(Context mContext, String symbol, String amt, String reason, String subReason){

        ArrayList<String> amtSet;
        ArrayList<String> reasonSet;
        ArrayList<String> subReasonSet;
        ArrayList<String> dateSet;
        ArrayList<String> monthSet;

        TinyDB tinyDB = new TinyDB(mContext);

        amtSet = new ArrayList<>();
        reasonSet = new ArrayList<>();
        subReasonSet = new ArrayList<>();
        dateSet = new ArrayList<>();
        monthSet = new ArrayList<>();

        List<String> empty = new ArrayList<>();
        empty = tinyDB.getListString("amtSet");

        if (empty.size() != 0)
        {
            amtSet = tinyDB.getListString("amtSet");
            reasonSet = tinyDB.getListString("reasonSet");
            subReasonSet = tinyDB.getListString("subReasonSet");
            dateSet = tinyDB.getListString("dateSet");
            monthSet = tinyDB.getListString("monthSet");

            getDate();
            getMonth();

            amtSet.add(symbol+"₹"+amt);
            reasonSet.add(reason);
            subReasonSet.add(subReason);
            dateSet.add(date);
            monthSet.add(month);

            tinyDB.putListString("amtSet",amtSet);
            tinyDB.putListString("reasonSet",reasonSet);
            tinyDB.putListString("subReasonSet",subReasonSet);
            tinyDB.putListString("dateSet",dateSet);
            tinyDB.putListString("monthSet",monthSet);

        }else{
            getDate();
            getMonth();

            amtSet.add(symbol+"₹"+amt);
            reasonSet.add(reason);
            subReasonSet.add(subReason);
            dateSet.add(date);
            monthSet.add(month);

            tinyDB.putListString("amtSet",amtSet);
            tinyDB.putListString("reasonSet",reasonSet);
            tinyDB.putListString("subReasonSet",subReasonSet);
            tinyDB.putListString("dateSet",dateSet);
            tinyDB.putListString("monthSet",monthSet);
        }
    }

    private static void getMonth() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("MMM", Locale.getDefault()); //dd MMM yyyy
        month = df.format(c);
    }

    public static  void getDate()
    {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault()); //dd MMM yyyy
        date = df.format(c);
    }

    public static void setDepositCash(Context ctx, float amount){
        tinyDB = new TinyDB(ctx);
        tinyDB.putFloat("depositCash", amount);
    }

    public static float getDepositCash(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getFloat("depositCash");
    }

    public static void setCashWon(Context ctx, float amount){
        tinyDB = new TinyDB(ctx);
        tinyDB.putFloat("cashWon", amount);
    }

    public static float getCashWon(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getFloat("cashWon");
    }

    public static void setBonusCash(Context ctx, float amount){
        tinyDB = new TinyDB(ctx);
        tinyDB.putFloat("bonusCash", amount);
    }

    public static float getBonusCash(Context ctx){
        tinyDB = new TinyDB(ctx);
        return tinyDB.getFloat("bonusCash");
    }

}

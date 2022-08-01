package com.cornerdesk.esportrealm.Helper;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.cornerdesk.esportrealm.BillingService;
import com.cornerdesk.esportrealm.Fragments.MyWallet;
import com.cornerdesk.esportrealm.GetTodayDate;
import com.cornerdesk.esportrealm.Home;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.notificationServices.Simple_Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class addCoins {

    private final static String KEY_ID = "rzp_test_xjz89hCI7zeRZg";
    private final static String KEY_SECRET = "MEpzX8MDKC9wzzWLbf7OjzrJ";
    Context ctx;
    String username;
    String payID;
    int coins;
    int REFER_CREDIT_COIN = 100;
    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    static DatabaseReference reference4;
    static ValueEventListener valueEventListener4;

    public addCoins(Context ctx, int coins, String payID){
        this.ctx = ctx;
        this.payID = payID;
        username = PlayerInfo.getName(ctx);
        this.coins = coins;

        try {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(username);
            reference.child("wallet").child("coins").setValue(ServerValue.increment(coins)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isComplete()){

                        PlayerInfo.setCoin(ctx, PlayerInfo.getCoin(ctx) + (coins));
                        BillingService.show_TV.setText("1/3");

                        if(!PlayerInfo.getRefBy(ctx).equals("NULL")){
                            creditAmountToRefBy(PlayerInfo.getRefBy(ctx).trim());
                        }else{
                            BillingService.show_TV.setText("Payment Successful!");
                            BillingService.show_TV.setTextColor(ctx.getColor(R.color.green));
                            BillingService.animWindow_LT.setAnimation(R.raw.majja);
                            new callBottomSheet(ctx).addMoneySuccess("Payment Id : " + payID, "Payment Successful!");
                        }

                        new Simple_Notification(ctx, "⚡ Top Up Completed!",
                                "\uD83D\uDD25 " + coins + " coins credited in your wallet");

                    }else{

                        new initiateRefund(ctx, KEY_ID, KEY_SECRET, payID); //Initiate Payments

                        BillingService.show_TV.setText("Payment Failed!");
                        BillingService.show_TV.setTextColor(ctx.getColor(R.color.fail));
                        BillingService.animWindow_LT.setVisibility(View.GONE);
                        new callBottomSheet(ctx).addMoneyFail("Transaction got failed, deducted amount will be get " +
                                "credited within 5-6 working days!");

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void creditAmountToRefBy(String refBy){

        reference2 = FirebaseDatabase.getInstance().getReference("Users").child(refBy).child("wallet").child("coins");
        reference2.setValue(ServerValue.increment(REFER_CREDIT_COIN)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    BillingService.show_TV.setText("2/3");
                    setNULL();
                }else{
                    new initiateRefund(ctx,KEY_ID, KEY_SECRET, payID); //Initiate Payments

                    BillingService.show_TV.setText("Payment Failed!");
                    BillingService.show_TV.setTextColor(ctx.getColor(R.color.fail));
                    BillingService.animWindow_LT.setVisibility(View.GONE);
                    new callBottomSheet(ctx).addMoneyFail("Transaction got failed, deducted amount will be get " +
                            "credited within 5-6 working days!");
                }
            }
        });

    }

    public void setNULL(){

        reference3 = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx)).child("refBy");
        reference3.setValue("NULL").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
                    PlayerInfo.setRefBy(ctx, "NULL");
                    BillingService.show_TV.setText("Payment Successful!");
                    BillingService.show_TV.setTextColor(ctx.getColor(R.color.green));
                    BillingService.animWindow_LT.setAnimation(R.raw.majja);
                    new callBottomSheet(ctx).addMoneySuccess( "Payment Id :" + payID, "Payment Successful!");
                }else{
                    new initiateRefund(ctx,KEY_ID, KEY_SECRET, payID); //Initiate Payments

                    BillingService.show_TV.setText("Payment Failed!");
                    BillingService.show_TV.setTextColor(ctx.getColor(R.color.fail));
                    BillingService.animWindow_LT.setVisibility(View.GONE);
                    new callBottomSheet(ctx).addMoneyFail("Transaction got failed, deducted amount will be get " +
                            "credited within 5-6 working days!");
                }
            }
        });

    }

    public static void addScratchCoins(Context ctx, int amt){

        valueEventListener4 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx)).child("wallet").child("coins");
                    reference.setValue(ServerValue.increment(amt)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                }

                if(valueEventListener4!=null){
                    reference4.removeEventListener(valueEventListener4);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(valueEventListener4!=null){
                    reference4.removeEventListener(valueEventListener4);
                }
            }
        };

        reference4 = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx));
        reference4.addListenerForSingleValueEvent(valueEventListener4);

        PlayerInfo.setCoin(ctx, PlayerInfo.getCoin(ctx) + amt);
        TransactionInfo.addTransaction(ctx, "+", String.valueOf(amt), "Scratch Card Coins", amt + " coins won from scratch card");
        MyWallet.refreshWallet(ctx);
        Home.getPlayerInfo();

        new Simple_Notification(ctx, "✨ Scratch Coins Credited Successfully!",
                "\uD83E\uDD47 " + amt +" coins credited in your wallet");

    }

}

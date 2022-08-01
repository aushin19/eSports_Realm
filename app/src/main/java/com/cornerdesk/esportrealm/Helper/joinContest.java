package com.cornerdesk.esportrealm.Helper;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cornerdesk.esportrealm.BillingService;
import com.cornerdesk.esportrealm.Fragments.MyDashboard;
import com.cornerdesk.esportrealm.Fragments.MyWallet;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.cornerdesk.esportrealm.notificationServices.subTopic;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class joinContest {
    static FirebaseFirestore db, db2;
    static Context ctx;
    static String entryCoins;
    static String matchNumber;
    static int Spots;
    static String userName;
    static String chardID;
    static ValueEventListener valueEventListener, valueEventListener1;
    static DatabaseReference ref;

    joinContest(Context context, String entryCoins, int Spots, String userName, String chardID){
        this.ctx = context;
        this.entryCoins = entryCoins;
        this.Spots = Spots;
        this.chardID = chardID;
        this.userName = userName;
        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();
    }

    public static void updateSpots(String docID, String topic, String msg){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    matchNumber = topic;

                    CountDownTimer ct = new CountDownTimer(30000, 1000){
                        public void onTick(long millisUntilFinished) {}
                        public void onFinish() {
                            if(callBottomSheet.dialog.isShowing()){
                                callBottomSheet.dialog.dismiss();
                                new callBottomSheet(ctx).addMoneyFail("");
                            }
                        }
                    }.start();

                    final int min = 1;
                    final int max = 10;
                    final int random = new Random().nextInt((max - min) + 1) + min;

                    new CountDownTimer(random * 1000, 1000){    //Check for current spots after random secs
                        public void onTick(long millisUntilFinished) {}
                        public void onFinish() {

                            db.collection("GameCard").document(docID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if(!task.isComplete()){
                                        if(callBottomSheet.dialog.isShowing()){
                                            callBottomSheet.dialog.dismiss();
                                            new callBottomSheet(ctx).addMoneyFail("");
                                        }
                                    }

                                    if(task.getResult().getLong("spotsLeft") < Spots){
                                        db.collection("GameCard").document(docID).update("spotsLeft", FieldValue.increment(1))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        ct.cancel();

                                                        new addContest(ctx).setContest(topic); // Add Contest in TinyDB
                                                        new subTopic(ctx, topic, "NULL"); //Subscribe Contest Topic
                                                        MyDashboard.refreshDashboard(); // Refresh Current Spots in Dashboard
                                                        PlayerInfo.setCoin(ctx, PlayerInfo.getCoin(ctx) - Integer.parseInt(entryCoins)); // Deduct Entry Coins from User Coins
                                                        Home.getPlayerInfo(); // Refresh User Wallet
                                                        TransactionInfo.addTransaction(ctx, "-", entryCoins, "Contest Joined","Joined Contest for " + (Integer.parseInt(entryCoins)) + " Coins");
                                                        deductCoins(); //Deduct FB Coins
                                                        addParticipation(); // Add Matches in FB
                                                        addEntryToSheet(); //Add Entry to Google Sheets
                                                        sendPaymentMail();
                                                        MyWallet.refreshWallet(ctx);
                                                        new Simple_Notification(ctx, "\uD83D\uDE0D Contest Joined Successfully!",
                                                                "\uD83E\uDD47 " + entryCoins + " coins deducted from your wallet for joining contest " + matchNumber);

                                                        if(!msg.equals("Login")){
                                                            if(callBottomSheet.dialog.isShowing()){
                                                                callBottomSheet.dialog.dismiss();
                                                                new callBottomSheet(ctx).addMoneySuccess("You've successfully joined " + matchNumber
                                                                        + ", Room ID and Password will be get shared before 15 mins of commencement of the match.", "Contest Joined Successfully!");
                                                            }
                                                        }

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                ct.cancel();
                                                if(callBottomSheet.dialog.isShowing()){
                                                    callBottomSheet.dialog.dismiss();
                                                    new callBottomSheet(ctx).addMoneyFail("");
                                                }
                                            }
                                        });
                                    }else{

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    ct.cancel();
                                    if(callBottomSheet.dialog.isShowing()){
                                        callBottomSheet.dialog.dismiss();
                                        new callBottomSheet(ctx).addMoneyFail("");
                                    }
                                }
                            });

                        }
                    }.start();
                    if(valueEventListener!=null){
                        ref.removeEventListener(valueEventListener);
                    }
                }else{
                    if(callBottomSheet.dialog.isShowing()){
                        callBottomSheet.dialog.dismiss();
                        new callBottomSheet(ctx).moreInfo("Banned", "You've been banned from using this app",
                                ctx.getDrawable(R.drawable.ic_close), "Exit!", ctx.getColor(R.color.fail));
                    }
                    if(valueEventListener!=null){
                        ref.removeEventListener(valueEventListener);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(valueEventListener!=null){
                    ref.removeEventListener(valueEventListener);
                }
            }
        };

        ref = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx));
        ref.addListenerForSingleValueEvent(valueEventListener);

    }

    public static void deductCoins(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx)).child("wallet").child("coins");
        reference.setValue(ServerValue.increment(-Integer.parseInt(entryCoins)));
    }

    public static void addParticipation(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx)).child("participation");
        valueEventListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx)).child("participation");
                    reference2.setValue(snapshot.getValue().toString() + ", " + matchNumber);
                }
                if(valueEventListener1!=null){
                    reference.removeEventListener(valueEventListener1);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(valueEventListener1!=null){
                    reference.removeEventListener(valueEventListener1);
                }
            }
        };

        reference.addListenerForSingleValueEvent(valueEventListener1);
    }

    private static void  addEntryToSheet() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzGTEesyT5VvqOhOTLjUG8uG-4qhpAUEDh5IQ4-OtNKttYVlC0LH8rJpa8uEg2kxKWsoQ/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                parmas.put("action",matchNumber);
                parmas.put("matchName",matchNumber);
                parmas.put("userName",PlayerInfo.getName(ctx));
                parmas.put("gameUsername", userName);
                parmas.put("gameCharID", chardID);
                parmas.put("kills", String.valueOf(0));
                parmas.put("coins", String.valueOf(0));
                parmas.put("cash", String.valueOf(0));

                return parmas;
            }
        };

        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(stringRequest);
    }

    public static void sendPaymentMail(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbz5gQG8HLCjU3rIZk7lTgvpbMZex8HBI-MGpchSe_XMYaA4Yg56JSI6LZKV_im2l391iw/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                parmas.put("userMailID",PlayerInfo.getMailID(ctx));
                parmas.put("userName",PlayerInfo.getName(ctx));
                parmas.put("userContest",matchNumber);
                parmas.put("userEntryFee",entryCoins);

                return parmas;
            }
        };

        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(stringRequest);
    }


}

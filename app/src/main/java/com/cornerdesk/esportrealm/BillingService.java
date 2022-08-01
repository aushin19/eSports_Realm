package com.cornerdesk.esportrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.cornerdesk.esportrealm.Helper.TransactionInfo;
import com.cornerdesk.esportrealm.Helper.addCoins;
import com.cornerdesk.esportrealm.Helper.callBottomSheet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Value;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class BillingService extends AppCompatActivity implements PaymentResultListener {

    int samount;
    public static TextView show_TV;
    public static LottieAnimationView animWindow_LT;
    public static Activity billing_service;
    DatabaseReference ref;
    ValueEventListener valueEventListener;
    private final static String KEY_ID = "rzp_test_xjz89hCI7zeRZg";
    private final static String KEY_SECRET = "MEpzX8MDKC9wzzWLbf7OjzrJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_service);
        Checkout.preload(this);

        billing_service = this;

        getWindow().setStatusBarColor(this.getColor(R.color.font));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        show_TV = findViewById(R.id.show_TV);
        animWindow_LT = findViewById(R.id.animWindow_LT);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    startPayment();
                }else{
                    show_TV.setText("Payment Failed!");
                    show_TV.setTextColor(BillingService.this.getColor(R.color.fail));
                    animWindow_LT.setVisibility(View.GONE);
                    new callBottomSheet(BillingService.this).addMoneyFail("You've been banned for using this app!");
                }

                if(valueEventListener!=null){
                    ref.removeEventListener(valueEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                if(valueEventListener!=null){
                    ref.removeEventListener(valueEventListener);
                }

            }
        };

        ref = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(this));
        ref.addListenerForSingleValueEvent(valueEventListener);

    }

    public void startPayment(){

        Checkout.preload(BillingService.this);

        Bundle extras = getIntent().getExtras();

        samount = extras.getInt("amt");

        int amount = Math.round(samount * 100);

        Checkout checkout = new Checkout();

        checkout.setKeyID(KEY_ID);

        checkout.setImage(R.drawable.rzp_logo);

        try {
            JSONObject object = new JSONObject();

            object.put("name", "eSports Realm");
            object.put("description", "Test payment");
            object.put("theme.color", "04b232");
            object.put("currency", "INR");
            object.put("amount", amount);

            checkout.open(BillingService.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        addAmts(s);
        TransactionInfo.addTransaction(this,"+", String.valueOf(samount*10), "Coins Added", samount*10 + " Coins Added in your wallet");
    }

    @Override
    public void onPaymentError(int i, String s){
        show_TV.setText("Payment Failed!");
        show_TV.setTextColor(this.getColor(R.color.fail));
        animWindow_LT.setVisibility(View.GONE);
        new callBottomSheet(BillingService.this).addMoneyFail("Transaction got failed, deducted amount will be get " +
                "credited within 5-6 working days!");
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please wait, we are currently working on it!", Toast.LENGTH_SHORT).show();
    }

    public void addAmts(String payID)
    {
        new addCoins(this, (samount * 10), payID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null){
            ref.removeEventListener(valueEventListener);
        }
        Log.d("shivam", "Billing");
    }
}
package com.cornerdesk.esportrealm.Helper;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cornerdesk.esportrealm.Fragments.MyWallet;
import com.cornerdesk.esportrealm.Home;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class WithdrawCash {

    int amount;
    Context ctx;
    String payDetail;

    public WithdrawCash(Context ctx, int amount, String payDetail){
        this.ctx = ctx;
        this.amount = amount;
        this.payDetail = payDetail;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(PlayerInfo.getName(ctx)).child("wallet").child("cash");
        reference.setValue(ServerValue.increment(-amount));

        PlayerInfo.setWallet(ctx, PlayerInfo.getWallet(ctx) - amount);
        Home.getPlayerInfo();
        payDetailsToSheet();

    }

    public void payDetailsToSheet(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzJFurXDFWd4Gtn7hHS0dyzejKsMBS80i7I-klJZ8mHAnFfn75r80wxHPldG_A6ySy9/exec",
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

                parmas.put("action","Sheet1");
                parmas.put("userName", PlayerInfo.getName(ctx));
                parmas.put("payDetail", payDetail);
                parmas.put("amount", String.valueOf(amount));


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

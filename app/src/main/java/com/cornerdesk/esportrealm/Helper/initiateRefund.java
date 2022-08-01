package com.cornerdesk.esportrealm.Helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;

import org.json.JSONException;
import org.json.JSONObject;

public class initiateRefund  extends AsyncTask<Void, Void, Void> {

    String KEY_ID;
    String KEY_SECRET;
    String payID;
    Context ctx;

    public initiateRefund(Context ctx, String KEY_ID, String KEY_SECRET, String payID) {
        this.ctx = ctx;
        this.KEY_ID = KEY_ID;
        this.KEY_SECRET = KEY_SECRET;
        this.payID = payID;
        execute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        initiateRefund();
        return null;
    }

    public void initiateRefund(){
        RazorpayClient razorpayClient = null;
        try {
            razorpayClient = new RazorpayClient(KEY_ID, KEY_SECRET);
            JSONObject refundRequest = new JSONObject();
            refundRequest.put("payment_id", "pay_IsGrApqXxYVjdA");
            //Refund refund = razorpayClient.Payments.refund(refundRequest);
            Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
        } catch (RazorpayException | JSONException e) {
            e.printStackTrace();
            Log.d("shivam", e.getMessage());
        }
    }

}

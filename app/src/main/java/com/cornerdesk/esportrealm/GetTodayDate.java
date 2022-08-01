package com.cornerdesk.esportrealm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cornerdesk.esportrealm.Helper.GetAllNotificationInfo;
import com.cornerdesk.esportrealm.Helper.PlayerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GetTodayDate extends AsyncTask<Void, Void, Void> {

    Context ctx;
    public static String todayDate;

    GetTodayDate(Context ctx){
        this.ctx = ctx;
        execute();
    }

    public void getDate() {
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,"https://get-app-links.herokuapp.com/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            todayDate = response.getString("today_date");
                            new CheckScratchCard(ctx, todayDate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(jsonObjRequest);


    }

    @Override
    protected Void doInBackground(Void... voids) {
        getDate();
        return null;
    }
}

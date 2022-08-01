package com.cornerdesk.esportrealm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cornerdesk.esportrealm.Fragments.MyOngoing;
import com.cornerdesk.esportrealm.Helper.GetAllNotificationInfo;
import com.cornerdesk.esportrealm.Helper.OngoingMatchesItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllNotification extends AsyncTask<Void, Void, Void> {

    Context ctx;
    ArrayList<GetAllNotificationInfo> item;

    public GetAllNotification(Context ctx){
        this.ctx = ctx;
        execute();
    }

    public void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxQ9fi22Q-3HRwiew3YhA2D7TtpiglO3QLqHl57jZkdHrj37DPn5bzczVFJBbFlyywQ/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                        if(response.isEmpty()){
                            if(AllNotification.pd.isShowing()){
                                AllNotification.pd.dismiss();
                            }
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(AllNotification.pd.isShowing()){
                            AllNotification.pd.dismiss();
                        }
                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");
            item = new ArrayList<>();
            AllNotification.getAllNoti(item, ctx);

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String title = jo.getString("title");
                String body = jo.getString("body");
                String date = jo.getString("date/time");
                String link = jo.getString("link");

                item.add(new GetAllNotificationInfo(title, body, date, link));
                AllNotification.refresh();
            }

            if(item.size() == 0){
                if(AllNotification.pd.isShowing()){
                    AllNotification.pd.dismiss();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Void doInBackground(Void... voids) {
        getItems();
        return null;
    }
}

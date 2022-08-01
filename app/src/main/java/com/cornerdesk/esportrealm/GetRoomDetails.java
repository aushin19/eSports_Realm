package com.cornerdesk.esportrealm;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cornerdesk.esportrealm.Fragments.MyResults;
import com.cornerdesk.esportrealm.Helper.GetResultInfo;
import com.cornerdesk.esportrealm.Helper.GetRoomInfo;
import com.cornerdesk.esportrealm.Helper.addContest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetRoomDetails extends AsyncTask<Void, Void, Void> {

    Context ctx;
    View view;
    ArrayList<GetRoomInfo> item;

    public GetRoomDetails(Context ctx){
        this.ctx = ctx;
        execute();
    }

    public void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbyvEj0H_swgMLVeCCZ4qqK9_6yOlVLZkBnB6nu09YHMaHXUj-jYAc1cq3SoKmtd31_R/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
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

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(stringRequest);

    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");
            item = new ArrayList<>();

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String matchNumber = jo.getString("Match Number");
                String matchID = jo.getString("Room ID");
                String matchPass = jo.getString("Password");

                if(new addContest(ctx).getContest(matchNumber)){
                    item.add(new GetRoomInfo(matchNumber, matchID, matchPass));
                    Home.getRoomDetails(item);
                    break;
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

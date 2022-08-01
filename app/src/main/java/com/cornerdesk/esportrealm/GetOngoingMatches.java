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
import com.cornerdesk.esportrealm.Fragments.MyOngoing;
import com.cornerdesk.esportrealm.Helper.OngoingMatchesItem;
import com.cornerdesk.esportrealm.ViewHolder.ongoingameCardViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetOngoingMatches extends AsyncTask<Void, Void, Void> {

    Context ctx;
    View view;
    ArrayList<OngoingMatchesItem> item;

    public GetOngoingMatches(Context ctx, View view){
        this.ctx = ctx;
        this.view = view;
        execute();
    }

    public void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbwVERz0l04XhpJhBE6PcqyONFsOd1H6LWZ5zysqUUxOElzAZ6Ld5lpFhBvgH-1d9n5zvw/exec",
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
            MyOngoing.getUpcomingMatches(view, item);

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                int PrizePool = jo.getInt("PrizePool");
                int PerKill = jo.getInt("PerKill");
                String Map = jo.getString("Map");
                String Mode = jo.getString("Mode");
                String MatchDate = jo.getString("MatchDate");
                int Contestant = jo.getInt("Contestant");
                String Status = jo.getString("Status");
                String ytLink = jo.getString("YT");

                item.add(new OngoingMatchesItem(Map, Mode, MatchDate, PrizePool, PerKill, Contestant, Status, ytLink));
                MyOngoing.refresh();
            }
            MyOngoing.shimmerFrameLayout.stopShimmer();
            MyOngoing.shimmerFrameLayout.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
            MyOngoing.shimmerFrameLayout.stopShimmer();
            MyOngoing.shimmerFrameLayout.setVisibility(View.GONE);
            MyOngoing.ongoinggameCard_RCV.setVisibility(View.GONE);
            MyOngoing.noMatches_TV.setVisibility(View.VISIBLE);
            MyOngoing.animSad.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected Void doInBackground(Void... voids) {
        getItems();
        return null;
    }
}

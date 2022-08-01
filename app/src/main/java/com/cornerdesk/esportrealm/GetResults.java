package com.cornerdesk.esportrealm;

import static com.cornerdesk.esportrealm.Fragments.MyResults.*;

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
import com.cornerdesk.esportrealm.Fragments.MyResults;
import com.cornerdesk.esportrealm.Helper.GetResultInfo;
import com.cornerdesk.esportrealm.Helper.OngoingMatchesItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetResults extends AsyncTask<Void, Void, Void> {

    Context ctx;
    View view;
    ArrayList<GetResultInfo> item;

    public GetResults(Context ctx, View view){
        this.ctx = ctx;
        this.view = view;
        execute();
    }

    public void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzTpkN_F036LS7b5fLL8oowW2wQ8puRju5PefHiwd1CA4IovSsT3bwL_KbH1gcB91hC4w/exec",
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
            MyResults.getUpcomingMatches(view, item);

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                int PrizePool = jo.getInt("PrizePool");
                int PerKill = jo.getInt("PerKill");
                String Map = jo.getString("Map");
                String Mode = jo.getString("Mode");
                String MatchDate = jo.getString("MatchDate");
                String Status = jo.getString("Status");
                String first = jo.getString("1st");
                String second= jo.getString("2nd");
                String third = jo.getString("3rd");
                String firstPrize = jo.getString("1st Prize");
                String secondPrize = jo.getString("2nd Prize");
                String thirdPrize = jo.getString("3rd Prize");

                item.add(new GetResultInfo(Map, Mode, MatchDate, Status, first, second, third,firstPrize, secondPrize, thirdPrize,
                        PrizePool, PerKill));
                MyResults.refresh();
            }

            MyResults.shimmerFrameLayout_2.stopShimmer();
            MyResults.shimmerFrameLayout_2.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
            MyResults.shimmerFrameLayout_2.stopShimmer();
            MyResults.shimmerFrameLayout_2.setVisibility(View.GONE);
            /*MyOngoing.ongoinggameCard_RCV.setVisibility(View.GONE);
            MyOngoing.noMatches_TV.setVisibility(View.VISIBLE);
            MyOngoing.animSad.setVisibility(View.VISIBLE);*/
        }

    }

    @Override
    protected Void doInBackground(Void... voids) {
        getItems();
        return null;
    }
}

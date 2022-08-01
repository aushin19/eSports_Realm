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
import com.cornerdesk.esportrealm.Helper.SliderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetPoster extends AsyncTask<Void, Void, Void> {

    Context ctx;
    View view;
    List<SliderItem> item;

    public GetPoster(Context ctx){
        this.ctx = ctx;
        execute();
    }

    public void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbwIyHWbB2qW8lgCseZLo0y7MRMASu8kfPjIN8TAVr7SLDuOAZE1_zUBtG65vTM8FUry/exec",
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
            Home.imageSlider(item);

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String Image = jo.getString("Image");
                String Link = jo.getString("Link");

                item.add(new SliderItem(Image, Link));
            }

            Home.shimmerFrameLayout.stopShimmer();
            Home.shimmerFrameLayout.setVisibility(View.GONE);

            Home.wormDotsIndicator.setVisibility(View.VISIBLE);
            Home.viewPager2.setVisibility(View.VISIBLE);
            Home.matchStatus_TV.setVisibility(View.VISIBLE);
            Home.pager2.setVisibility(View.VISIBLE);
            Home.mainLayout1.setVisibility(View.VISIBLE);
            Home.mainLayout2.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Void doInBackground(Void... voids) {
        Home.wormDotsIndicator.setVisibility(View.GONE);
        Home.viewPager2.setVisibility(View.GONE);
        Home.matchStatus_TV.setVisibility(View.GONE);
        Home.pager2.setVisibility(View.GONE);
        Home.mainLayout1.setVisibility(View.GONE);
        Home.mainLayout2.setVisibility(View.GONE);
        getItems();
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        new GetRoomDetails(ctx);
        super.onPostExecute(unused);
    }
}

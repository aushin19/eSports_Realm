package com.cornerdesk.esportrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cornerdesk.esportrealm.ViewHolder.allNotiViewHolder;

import java.util.ArrayList;

public class AllNotification extends AppCompatActivity {

    static RecyclerView allNotiCard_RCV;
    static allNotiViewHolder myAdapter;
    static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notification);

        getWindow().setStatusBarColor(this.getColor(R.color.font));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        pd.setMessage("Please Wait....");
        pd.setCancelable(false);
        pd.show();

        findViewById(R.id.back_IMG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        allNotiCard_RCV = findViewById(R.id.notification_RCV);
        new GetAllNotification(this);

    }

    public static void getAllNoti(ArrayList InfoArrayList, Context ctx){
        allNotiCard_RCV.setLayoutManager(new LinearLayoutManager(ctx));

        myAdapter = new allNotiViewHolder(ctx, InfoArrayList);
        allNotiCard_RCV.setAdapter(myAdapter);
    }

    public static void refresh(){
        myAdapter.notifyDataSetChanged();
        if(pd.isShowing())
            pd.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
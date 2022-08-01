package com.cornerdesk.esportrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cornerdesk.esportrealm.Helper.AsyncTasks;
import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.cornerdesk.esportrealm.Helper.TinyDB;
import com.cornerdesk.esportrealm.Helper.callBottomSheet;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Button moreInfo_BTN, withdrawReq_BTN;
    TextView noTransaction_TV, playerName_TV, playerMailID_TV;
    ListView transaction_RCV;
    ListView dataList;
    String[] amt;
    String[] reason;
    String[] date;
    String[] month;

    public ArrayList<String> amtSet;
    public ArrayList<String> reasonSet;
    public ArrayList<String> dateSet;
    public ArrayList<String> monthSet;

    TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.green));

        moreInfo_BTN = findViewById(R.id.moreInfo_BTN);
        withdrawReq_BTN = findViewById(R.id.withdrawReq_BTN);

        noTransaction_TV = findViewById(R.id.noTransaction_TV);
        playerName_TV = findViewById(R.id.playerName_TV);
        playerMailID_TV = findViewById(R.id.playerMailID_TV);

        transaction_RCV = findViewById(R.id.transaction_RCV);

        dataList = findViewById(R.id.transaction_RCV);

        tinydb = new TinyDB(this);

        moreInfo_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new callBottomSheet(Profile.this).moreInfo("Payment Failed!", "Payment has cancelled",
                        Profile.this.getDrawable(R.drawable.ic_info), "OK", getColor(R.color.darkGrey));
            }
        });

        withdrawReq_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new callBottomSheet(Profile.this).withdrawReq();
            }
        });

        amtSet = new ArrayList<>();
        reasonSet = new ArrayList<>();
        dateSet = new ArrayList<>();
        monthSet = new ArrayList<>();

        //tinydb.clear();
        //TransactionInfo.addTransaction(this,"-", "56.5", "Amount Credited");

        new AsyncTasks() {
            @Override
            public void onPreExecute() {
                // before execution
            }

            @Override
            public void doInBackground() {
                getPlayerInfo();
                getTransactionHistory();
            }

            @Override
            public void onPostExecute() {
                if(!isShutdown())
                    shutdown();
            }
        }.execute();

    }

    public void getTransactionHistory(){

        ArrayList<String> empty = new ArrayList<>();
        empty = tinydb.getListString("amtSet");

        amt = new String[empty.size()];
        reason = new String[amt.length];
        date = new String[amt.length];
        month = new String[amt.length];

        amtSet = tinydb.getListString("amtSet");
        reasonSet = tinydb.getListString("reasonSet");
        dateSet = tinydb.getListString("dateSet");
        monthSet = tinydb.getListString("monthSet");

        amt = amtSet.stream().toArray(String[]::new);
        reason = reasonSet.stream().toArray(String[]::new);
        date = dateSet.stream().toArray(String[]::new);
        month = monthSet.stream().toArray(String[]::new);

        if(amt.length != 0){
            noTransaction_TV.setVisibility(View.GONE);
            transaction_RCV.setVisibility(View.VISIBLE);

            //transactionViewHolder adapter = new transactionViewHolder(this, amt, reason, subReason, date, month);
            dataList = (ListView) findViewById(R.id.transaction_RCV);
            //dataList.setAdapter((ListAdapter) adapter);
        }else{
            noTransaction_TV.setVisibility(View.VISIBLE);
            transaction_RCV.setVisibility(View.GONE);
        }
    }

    public void getPlayerInfo(){
        playerName_TV.setText(PlayerInfo.getName(this).toUpperCase());
        playerMailID_TV.setText(PlayerInfo.getMailID(this));
    }

}
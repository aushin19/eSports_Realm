package com.cornerdesk.esportrealm;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckScratchCard {

    public CheckScratchCard(Context ctx, String todayDate){

        if(!PlayerInfo.getScratchDate(ctx).equals(todayDate)){
            Home.scratch_IN.setVisibility(View.VISIBLE);
        }else{
            Home.scratch_IN.setVisibility(View.GONE);
        }

    }

}

package com.cornerdesk.esportrealm.Helper;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setLastCheckIn {

    setLastCheckIn(Context ctx, String todayDate){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(PlayerInfo.getName(ctx)).child("last_check_in");
        ref.setValue(todayDate);

    }

}

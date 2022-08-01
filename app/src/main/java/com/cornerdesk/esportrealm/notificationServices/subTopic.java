package com.cornerdesk.esportrealm.notificationServices;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cornerdesk.esportrealm.Helper.callBottomSheet;
import com.cornerdesk.esportrealm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class subTopic {

    public subTopic(Context ctx, String topic, String msg){
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        /*if(!msg.equals("Login")){
                            if(callBottomSheet.dialog.isShowing()){
                                callBottomSheet.dialog.dismiss();
                                new callBottomSheet(ctx).addMoneySuccess("");
                            }

                            if (!task.isSuccessful()) {
                                if(callBottomSheet.dialog.isShowing()){
                                    callBottomSheet.dialog.dismiss();
                                    new callBottomSheet(ctx).addMoneySuccess("");
                                }
                            }
                        }*/
                    }
                });
    }

}

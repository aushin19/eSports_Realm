package com.cornerdesk.esportrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.cornerdesk.esportrealm.InternetBG_Check.MyService;

import java.io.IOException;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class MainActivity extends AppCompatActivity {

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        startService(new Intent(getBaseContext(), MyService.class));

        action();
    }

    private void action(){
        if (isOnline()) {
            if(!CheckRoot.isDeviceRooted()){
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        finish();
                        if(!PlayerInfo.getName(MainActivity.this).equals("")){
                            startActivity(new Intent(MainActivity.this, Home.class)); //Send Home
                        }else{
                            startActivity(new Intent(MainActivity.this, Register.class)); //Send Registration
                        }
                    }
                },3000);
            }else{
                BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(this)
                        .setTitle("Rooted!")
                        .setMessage("This app not works on\nRooted Devices!")
                        .setCancelable(false)
                        .setPositiveButton("Exit", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                finish();
                            }
                        })
                        .build();
                mDialog.show();
            }
        }else{
            BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(this)
                    .setTitle("Oh Snap!")
                    .setMessage("No internet connection found\nCheck your internet connection")
                    .setCancelable(false)
                    .setPositiveButton("Try again!", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            action();
                        }
                    })
                    .setNegativeButton("Exit", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finish();
                        }
                    })
                    .build();
            mDialog.show();
        }
    }

    private boolean isOnline() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
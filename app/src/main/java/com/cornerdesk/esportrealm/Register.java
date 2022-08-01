package com.cornerdesk.esportrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cornerdesk.esportrealm.ViewHolder.RegisterFragmentAdapter;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class Register extends AppCompatActivity {

    public static Activity register;
    public static ViewPager2 pager2;
    RegisterFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = this;

        pager2 = findViewById(R.id.register_ViewPager2);

        pager2.setOffscreenPageLimit(2);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new RegisterFragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Exit", R.drawable.ic_check, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        mDialog.show();
    }

    public static void slideScreen(int screenPosition){
        pager2.setCurrentItem(screenPosition);
    }

}
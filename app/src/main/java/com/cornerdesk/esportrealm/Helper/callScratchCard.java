package com.cornerdesk.esportrealm.Helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.anupkumarpanwar.scratchview.ScratchView;
import com.cornerdesk.esportrealm.CheckScratchCard;
import com.cornerdesk.esportrealm.GetTodayDate;
import com.cornerdesk.esportrealm.R;
import java.util.Random;

public class callScratchCard {

    public static Dialog dialog;
    ConstraintLayout scratchHolder_CL;
    ImageView Shine;
    int amtAdd;
    Context ctx;

    public callScratchCard(Context ctx){

        this.ctx = ctx;

        dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.scratch_card);

        LottieAnimationView scratchAnim_LT = dialog.findViewById(R.id.scratchAnim_LT);
        ScratchView scratch = dialog.findViewById(R.id.scratch_view);
        Shine = dialog.findViewById(R.id.shineScratch);
        CardView scratchCardView = dialog.findViewById(R.id.scratchCardView);
        scratchHolder_CL = dialog.findViewById(R.id.scratchHolder_CL);
        ConstraintLayout betterLuck_CL = dialog.findViewById(R.id.betterLuck_CL);
        TextView coinWon_TV = dialog.findViewById(R.id.coinsWon_TV);

        Animation anim = AnimationUtils.loadAnimation(ctx, R.anim.scratch_pop_up);

        randomNum();

        if (amtAdd%2 == 0)
            betterLuck_CL.setVisibility(View.VISIBLE);
        else
            scratchHolder_CL.setVisibility(View.VISIBLE);

        coinWon_TV.setText(amtAdd + " Coins");


        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scratch.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {
                scratchView.clear();
                scratchCardView.startAnimation(anim);
                scratchAnim_LT.playAnimation();
                if(scratchHolder_CL.getVisibility() == View.VISIBLE){
                    addCoins.addScratchCoins(ctx, amtAdd);
                }

                PlayerInfo.setScratchDate(ctx, GetTodayDate.todayDate);
                new CheckScratchCard(ctx, GetTodayDate.todayDate);
                new setLastCheckIn(ctx, GetTodayDate.todayDate);

            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {
                if(percent>=0.75)
                    scratchView.reveal();
            }
        });

        dialog.findViewById(R.id.close_CL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.bottomSheetAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
    }

    private void animate() {
        Animation animation = new TranslateAnimation(
                0,scratchHolder_CL.getWidth() + Shine.getWidth(),0,0);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        Shine.startAnimation(animation);
    }

    private void randomNum(){
        final int min = 1;
        final int max = 20;
        amtAdd = new Random().nextInt((max - min) + 1) + min;
    }

}

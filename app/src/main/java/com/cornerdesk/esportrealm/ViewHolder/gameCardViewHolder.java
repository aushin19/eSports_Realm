package com.cornerdesk.esportrealm.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cornerdesk.esportrealm.Helper.GetGameCardInfo;
import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.cornerdesk.esportrealm.Helper.addContest;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.Helper.callBottomSheet;
import com.razorpay.Checkout;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.iwgang.countdownview.CountdownView;

public class gameCardViewHolder extends RecyclerView.Adapter<gameCardViewHolder.MyViewHolder> {

    Context context;
    ArrayList<GetGameCardInfo> gameCardInfoArrayList;
    CountDownTimer ct;

    public gameCardViewHolder(Context context, ArrayList<GetGameCardInfo> gameCardInfoArrayList) {
        this.context = context;
        this.gameCardInfoArrayList = gameCardInfoArrayList;
        Checkout.preload(context);
    }

    @NonNull
    @Override
    public gameCardViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.game_cards_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull gameCardViewHolder.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

       GetGameCardInfo gameCard = gameCardInfoArrayList.get(position);

        String info = gameCard.info;
        int spotsLeft = gameCard.spotsLeft;
        String[] infoList = info.split(",");

        holder.prizePool_TV.setText(context.getString(R.string.rupee) + String.valueOf(getFormatedAmount(Integer.parseInt(infoList[0]))));
        holder.perKill_TV.setText(infoList[1].trim());
        holder.entryFee_TV.setText(infoList[2].trim());
        holder.spotsLeft_TV.setText((spotsLeft) + " Spots Filled");
        holder.spots_TV.setText(String.valueOf(infoList[3]) + " Spots");
        holder.map_TV.setText(infoList[4].trim());
        holder.mode_TV.setText(infoList[5].trim());

        String label = "Match on " + infoList[7].trim();
        holder.matchDate_TV.setText(label);

        holder.spots_PB.setProgress(spotsLeft);

        holder.entryButton_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayerInfo.getCoin(context) >= Integer.parseInt(infoList[2].trim())){
                    new callBottomSheet(context).joinContest("sufficient", infoList[2].trim(), infoList[11].trim(), infoList[11].trim(), Integer.parseInt(infoList[3].trim()));
                }else{
                    new callBottomSheet(context).joinContest("insufficient", infoList[2].trim(), infoList[11].trim(), infoList[11].trim(), Integer.parseInt(infoList[3].trim()));
                }

            }
        });

        holder.joined_CL.setVisibility(View.GONE);
        holder.entryButton_CV.setVisibility(View.GONE);
        holder.matchFull_CL.setVisibility(View.GONE);

        if(new addContest(context).getContest(infoList[11].trim())){
            holder.joined_CL.setVisibility(View.VISIBLE);
        }else if(spotsLeft >= Integer.parseInt(infoList[3].trim())){
            holder.matchFull_CL.setVisibility(View.VISIBLE);
        }else{
            holder.entryButton_CV.setVisibility(View.VISIBLE);
        }

        if(spotsLeft >= Integer.parseInt(infoList[3].trim())){
            holder.spotsLeft_TV.setText("No Spots Left");
        }

        holder.firstPrice_TV.setText(context.getString(R.string.rupee) + infoList[8].trim());
        holder.secondPrice_TV.setText(context.getString(R.string.rupee) + infoList[9].trim());
        holder.thirdPrice_TV.setText(context.getString(R.string.rupee) + infoList[10].trim());

        holder.matchNumber_TV.setText("#" + infoList[11].trim());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //21-02-2022 15:00:00
        String countDate = infoList[6].trim();
        Date now = new Date();

        try {
            Date date = sdf.parse(countDate);
            long currentTime = now.getTime();
            long matchDate = date.getTime();
            long countDownToMatchDate = matchDate - currentTime;
            holder.mCvCountdownView.start(countDownToMatchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                //holder.mCvCountdownView.setVisibility(View.GONE);
                cv.setVisibility(View.GONE);
            }
        });

        ct = new CountDownTimer(5000, 1000){
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                animate(holder);
                start();
            }
        }.start();


        holder.priceDist_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.priceDistView_CL.getVisibility() == View.VISIBLE){
                    holder.priceDistView_CL.setVisibility(View.GONE);
                    holder.priceDist_BTN.setBackground(context.getDrawable(R.drawable.ic_up_arrow));
                }else{
                    holder.priceDistView_CL.setVisibility(View.VISIBLE);
                    holder.priceDist_BTN.setBackground(context.getDrawable(R.drawable.ic_down_arrow));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return gameCardInfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView prizePool_TV, perKill_TV, entryFee_TV, spotsLeft_TV, spots_TV, map_TV, mode_TV, matchDate_TV, firstPrice_TV, secondPrice_TV, thirdPrice_TV, matchNumber_TV;
        ProgressBar spots_PB;
        CountdownView mCvCountdownView;
        ImageView Shine, coin_IMG;
        CardView entryButton_CV;
        ConstraintLayout priceDistView_CL, joined_CL, matchFull_CL;
        Button priceDist_BTN;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mCvCountdownView = itemView.findViewById(R.id.mycountdown);

            entryButton_CV = itemView.findViewById(R.id.liveAnim_LT);
            joined_CL = itemView.findViewById(R.id.joined_CL);
            matchFull_CL = itemView.findViewById(R.id.matchFull_CL);

            prizePool_TV = itemView.findViewById(R.id.prizePool_TV);
            perKill_TV = itemView.findViewById(R.id.perKill_TV);
            entryFee_TV = itemView.findViewById(R.id.entryFee_TV);
            map_TV = itemView.findViewById(R.id.map_TV);
            mode_TV = itemView.findViewById(R.id.mode_TV);
            spots_TV = itemView.findViewById(R.id.spots_TV);
            spotsLeft_TV = itemView.findViewById(R.id.spotsLeft_TV);
            spots_PB = itemView.findViewById(R.id.spots_PB);
            matchDate_TV = itemView.findViewById(R.id.matchDate_TV);
            matchNumber_TV = itemView.findViewById(R.id.matchNumber_TV);

            Shine = itemView.findViewById(R.id.shine);
            coin_IMG = itemView.findViewById(R.id.coin_IMG);

            priceDist_BTN = itemView.findViewById(R.id.priceDist_BTN);
            priceDistView_CL = itemView.findViewById(R.id.priceDistVIew_CL);

            firstPrice_TV = itemView.findViewById(R.id.firstPrice_TV);
            secondPrice_TV = itemView.findViewById(R.id.secondPrice_TV);
            thirdPrice_TV = itemView.findViewById(R.id.thirdPrice_TV);

        }

    }

    private String getFormatedAmount(int amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    private void animate(@NonNull gameCardViewHolder.MyViewHolder holder) {
        Animation animation = new TranslateAnimation(
                0,holder.entryButton_CV.getWidth()+holder.Shine.getWidth(),0,0);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        holder.Shine.startAnimation(animation);
    }

}

package com.cornerdesk.esportrealm.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cornerdesk.esportrealm.Helper.GetResultInfo;
import com.cornerdesk.esportrealm.R;
import com.razorpay.Checkout;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class resultCardViewHolder extends RecyclerView.Adapter<resultCardViewHolder.MyViewHolder> {

    Context context;
    ArrayList<GetResultInfo> gameCardInfoArrayList;

    public resultCardViewHolder(Context context, ArrayList<GetResultInfo> gameCardInfoArrayList) {
        this.context = context;
        this.gameCardInfoArrayList = gameCardInfoArrayList;
        Checkout.preload(context);
    }

    @NonNull
    @Override
    public resultCardViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.result_cards_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull resultCardViewHolder.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

       GetResultInfo gameCard = gameCardInfoArrayList.get(position);

        holder.prizePool_TV.setText(context.getString(R.string.rupee) + String.valueOf(getFormatedAmount(gameCard.PrizePool)));
        holder.perKill_TV.setText(String.valueOf(gameCard.PerKill));
        holder.map_TV.setText(gameCard.Map);
        holder.mode_TV.setText(gameCard.Mode);
        holder.status_TV.setText(gameCard.Status);

        String label = "Match on " + gameCard.MatchDate;
        holder.matchDate_TV.setText(label);

        holder.player1_TV.setText(gameCard.First);
        holder.player2_TV.setText(gameCard.Second);
        holder.player3_TV.setText(gameCard.Third);

        holder.player1Prize_TV.setText("Won ₹" + gameCard.firstPrize);
        holder.player2Prize_TV.setText("Won ₹" + gameCard.secondPrize);
        holder.player3Prize_TV.setText("Won ₹" + gameCard.thirdPrize);

    }

    @Override
    public int getItemCount() {
        return gameCardInfoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView prizePool_TV, perKill_TV, map_TV, mode_TV, matchDate_TV, status_TV, player1Prize_TV, player2Prize_TV, player3Prize_TV;
        TextView player1_TV, player2_TV, player3_TV;
        ImageView firstBadge_IMG;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            prizePool_TV = itemView.findViewById(R.id.prizePool_TV);
            perKill_TV = itemView.findViewById(R.id.perKill_TV);
            map_TV = itemView.findViewById(R.id.map_TV3);
            mode_TV = itemView.findViewById(R.id.mode_TV3);
            matchDate_TV = itemView.findViewById(R.id.matchDate_TV);
            status_TV = itemView.findViewById(R.id.status_TV);
            player1Prize_TV = itemView.findViewById(R.id.player1Prize_TV);
            player2Prize_TV = itemView.findViewById(R.id.player2Prize_TV);
            player3Prize_TV = itemView.findViewById(R.id.player3Prize_TV);
            player1_TV = itemView.findViewById(R.id.player1_TV);
            player2_TV = itemView.findViewById(R.id.player2_TV);
            player3_TV = itemView.findViewById(R.id.player3_TV);

            firstBadge_IMG = itemView.findViewById(R.id.firstBadge_IMG);
        }
    }

    private String getFormatedAmount(int amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

}

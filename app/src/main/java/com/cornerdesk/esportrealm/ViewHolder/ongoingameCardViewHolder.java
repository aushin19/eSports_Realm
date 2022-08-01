package com.cornerdesk.esportrealm.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.Helper.OngoingMatchesItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ongoingameCardViewHolder extends RecyclerView.Adapter<ongoingameCardViewHolder.MyViewHolder> {

    Context context;
    ArrayList<OngoingMatchesItem> gameCardInfoArrayList;

    public ongoingameCardViewHolder(Context context, ArrayList<OngoingMatchesItem> gameCardInfoArrayList) {
        this.context = context;
        this.gameCardInfoArrayList = gameCardInfoArrayList;
    }

    @NonNull
    @Override
    public ongoingameCardViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.ongoing_game_cards_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ongoingameCardViewHolder.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        OngoingMatchesItem gameCard = gameCardInfoArrayList.get(position);

        //PrizePool, PerKill, Map, Mode, MatchDate

        holder.prizePool_TV.setText(context.getString(R.string.rupee) + String.valueOf(getFormatedAmount(gameCard.PrizePool)));
        holder.perKill_TV.setText(String.valueOf(gameCard.PerKill));
        holder.map_TV.setText(gameCard.Map);
        holder.mode_TV.setText(gameCard.Mode);

        String label = "Match on " + gameCard.MatchDate;
        holder.matchDate_TV.setText(label);

        holder.participant_TV.setText(gameCard.Contestant + " Contestants");
        holder.status_TV.setText(gameCard.Status);
        holder.live_IMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent link = new Intent(Intent.ACTION_VIEW);
                link.setData(Uri.parse(gameCard.YT_Link));
                context.startActivity(link);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gameCardInfoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView prizePool_TV, perKill_TV, map_TV, mode_TV, matchDate_TV, participant_TV, status_TV;
        ImageView live_IMG;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            prizePool_TV = itemView.findViewById(R.id.prizePool_TV);
            perKill_TV = itemView.findViewById(R.id.perKill_TV);
            map_TV = itemView.findViewById(R.id.map_TV);
            mode_TV = itemView.findViewById(R.id.mode_TV);
            matchDate_TV = itemView.findViewById(R.id.matchDate_TV);
            participant_TV = itemView.findViewById(R.id.participants_TV);
            status_TV = itemView.findViewById(R.id.status_TV);
            live_IMG = itemView.findViewById(R.id.live_IMG);
        }
    }

    private String getFormatedAmount(int amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

}

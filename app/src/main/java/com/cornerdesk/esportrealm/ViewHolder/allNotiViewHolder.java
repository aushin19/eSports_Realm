package com.cornerdesk.esportrealm.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cornerdesk.esportrealm.Helper.GetAllNotificationInfo;
import com.cornerdesk.esportrealm.R;

import java.net.URI;
import java.util.ArrayList;

public class allNotiViewHolder extends RecyclerView.Adapter<allNotiViewHolder.MyViewHolder> {

    Context context;
    ArrayList<GetAllNotificationInfo> notiInfoArrayList;

    public allNotiViewHolder(Context context, ArrayList<GetAllNotificationInfo> notiInfoArrayList) {
        this.context = context;
        this.notiInfoArrayList = notiInfoArrayList;
    }

    @NonNull
    @Override
    public allNotiViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.all_notification_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull allNotiViewHolder.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

       GetAllNotificationInfo infoCard = notiInfoArrayList.get(position);

        holder.notiTitle_TV.setText(infoCard.getTitle());
        holder.notiBody_TV.setText(infoCard.getBody());
        holder.notiDate_TV.setText(infoCard.getDate());

        if(!infoCard.getLink().equals("NULL"))
            holder.notiLink_TV.setVisibility(View.VISIBLE);

        holder.notiLink_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(infoCard.getLink()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notiInfoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView notiTitle_TV, notiBody_TV, notiDate_TV, notiLink_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notiTitle_TV = itemView.findViewById(R.id.notiTitle_TV);
            notiBody_TV = itemView.findViewById(R.id.notiBody_TV);
            notiDate_TV = itemView.findViewById(R.id.notiDate_TV);
            notiLink_TV = itemView.findViewById(R.id.notiLink_TV);
        }
    }

}

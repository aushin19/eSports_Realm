package com.cornerdesk.esportrealm.ViewHolder;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cornerdesk.esportrealm.R;
import com.razorpay.Card;

public class transactionViewHolder extends ArrayAdapter<String> {

    public  Activity context;
    String[] amt;
    String[] reason;
    String[] subReason;
    String[] date;
    String[] month;
    LayoutInflater inflater;

    public transactionViewHolder(Activity ctx, String[] amt, String[] reason, String[] subReason, String[] date, String[] month){
        super(ctx, R.layout.transaction_list, reason);
        this.context = ctx;
        this.amt = amt;
        this.reason = reason;
        this.subReason = subReason;
        this.date = date;
        this.month = month;
        this.inflater = LayoutInflater.from(ctx);
    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.transaction_list, null,true);

        TextView amt_TV = (TextView) rowView.findViewById(R.id.amt_TV);
        TextView reason_TV = (TextView) rowView.findViewById(R.id.reason_TV);
        TextView subReason_TV = (TextView) rowView.findViewById(R.id.subReason_TV);
        TextView date_TV = (TextView) rowView.findViewById(R.id.date_TV);
        TextView month_TV = (TextView) rowView.findViewById(R.id.month_TV);
        ImageView zCoin_IMG = rowView.findViewById(R.id.zCoin_IMG);
        CardView amtHistory = rowView.findViewById(R.id.amtHistory_CV);

        if(amt[position].contains("-")){
            amt_TV.setTextColor(getContext().getColorStateList(R.color.amtDebit));
            amtHistory.setBackgroundTintList(getContext().getColorStateList(R.color.trans_red));
        }

        if(reason[position].contains("Cash")){
            zCoin_IMG.setImageDrawable(getContext().getDrawable(R.drawable.rupee_cash));
            amt_TV.setText(amt[position]);
        }

        if(reason[position].contains("Coins") | reason[position].contains("Contest")){
            amt_TV.setText(amt[position].replace("₹", ""));
        }

        reason_TV.setText(reason[position]);
        subReason_TV.setText(subReason[position]);
        date_TV.setText(date[position]);
        month_TV.setText(month[position]);

        return rowView;

    }
}

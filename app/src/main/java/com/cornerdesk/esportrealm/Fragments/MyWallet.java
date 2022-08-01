package com.cornerdesk.esportrealm.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.cornerdesk.esportrealm.Helper.TinyDB;
import com.cornerdesk.esportrealm.Helper.WithdrawCash;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.ViewHolder.transactionViewHolder;
import com.cornerdesk.esportrealm.Helper.callBottomSheet;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyWallet#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MyWallet extends Fragment {

    static ListView transaction_RCV;
    static ListView dataList;
    static String[] amt;
    static String[] reason;
    static String[] subReason;
    static String[] date;
    static String[] month;

    static View viewOK;

    public static transactionViewHolder adapter;
    public static TextView noTransaction_TV;

    public static ArrayList<String> amtSet;
    public static ArrayList<String> reasonSet;
    public static ArrayList<String> subReasonSet;
    public static ArrayList<String> dateSet;
    public static ArrayList<String> monthSet;

    Button withdraw_BTN;

    static TinyDB tinydb;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyWallet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyWallet.
     */
    // TODO: Rename and change types and number of parameters
    public static MyWallet newInstance(String param1, String param2) {
        MyWallet fragment = new MyWallet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //tinydb.clear();
        //TransactionInfo.addTransaction(getContext(),"-", "56.5", "Amount Credited");

        withdraw_BTN = view.findViewById(R.id.withdraw_BTN);
        Button withdrawReq_BTN = view.findViewById(R.id.withdrawReq_BTN);
        Button addCoin_BTN = view.findViewById(R.id.addCoin_BTN);

        viewOK = view;
        getBalanceView(getActivity());
        getTransactionHistory(getActivity());

        withdraw_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new callBottomSheet(getContext()).withdrawReq();
            }
        });

        withdrawReq_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new callBottomSheet(getContext()).withdrawReq();
            }
        });

        addCoin_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new callBottomSheet(getContext()).addMoney();
            }
        });

    }

    public void getBalanceView(Context ctx){
        TextView cashWon = viewOK.findViewById(R.id.cashWon_TV);
        TextView coinBal = viewOK.findViewById(R.id.coinBal_TV);

        cashWon.setText(getContext().getString(R.string.rupee) + PlayerInfo.getWallet(ctx));
        coinBal.setText(PlayerInfo.getCoin(ctx)+"");
    }

    public static void getTransactionHistory(Context ctx){

        noTransaction_TV = viewOK.findViewById(R.id.noTransaction_TV);
        transaction_RCV = viewOK.findViewById(R.id.transaction_RCV);
        dataList = viewOK.findViewById(R.id.transaction_RCV);
        tinydb = new TinyDB(ctx);

        amtSet = new ArrayList<>();
        reasonSet = new ArrayList<>();
        subReasonSet = new ArrayList<>();
        dateSet = new ArrayList<>();
        monthSet = new ArrayList<>();

        ArrayList<String> empty = new ArrayList<>();
        empty = tinydb.getListString("amtSet");

        amt = new String[empty.size()];
        reason = new String[amt.length];
        subReason = new String[amt.length];
        date = new String[amt.length];
        month = new String[amt.length];

        amtSet = tinydb.getListString("amtSet");
        reasonSet = tinydb.getListString("reasonSet");
        subReasonSet = tinydb.getListString("subReasonSet");
        dateSet = tinydb.getListString("dateSet");
        monthSet = tinydb.getListString("monthSet");

        amt = amtSet.stream().toArray(String[]::new);
        reason = reasonSet.stream().toArray(String[]::new);
        subReason = subReasonSet.stream().toArray(String[]::new);
        date = dateSet.stream().toArray(String[]::new);
        month = monthSet.stream().toArray(String[]::new);

        if(amt.length != 0){
            noTransaction_TV.setVisibility(View.GONE);
            transaction_RCV.setVisibility(View.VISIBLE);

            adapter = new transactionViewHolder((Activity) ctx, amt, reason, subReason, date, month);
            dataList = (ListView) viewOK.findViewById(R.id.transaction_RCV);
            dataList.setAdapter((ListAdapter) adapter);
        }else{
            noTransaction_TV.setVisibility(View.VISIBLE);
            transaction_RCV.setVisibility(View.GONE);
        }
    }

    public static void refreshWallet(Context ctx){
        getTransactionHistory(ctx);
    }

}
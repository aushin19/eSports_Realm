package com.cornerdesk.esportrealm.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cornerdesk.esportrealm.GetOngoingMatches;
import com.cornerdesk.esportrealm.GetResults;
import com.cornerdesk.esportrealm.Helper.GetResultInfo;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.ViewHolder.resultCardViewHolder;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOngoing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyResults extends Fragment {

    static RecyclerView resultCard_RCV;
    static ArrayList<GetResultInfo> resultCardInfoArrayList;
    static resultCardViewHolder myAdapter;
    static Context ctx;
    static View viewOK;
    public static ShimmerFrameLayout shimmerFrameLayout_2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyResults() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOngoing.
     */
    // TODO: Rename and change types and number of parameters
    public static MyResults newInstance(String param1, String param2) {
        MyResults fragment = new MyResults();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_results ,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewOK = getView();
        ctx = getContext();

        shimmerFrameLayout_2 = view.findViewById(R.id.shimmer3);
        shimmerFrameLayout_2.setVisibility(View.VISIBLE);
        shimmerFrameLayout_2.startShimmer();

        new GetResults(getContext(), view);
    }

    public static void getUpcomingMatches(View view, ArrayList gameCardInfoArrayList){
        resultCard_RCV = view.findViewById(R.id.resultCard_RCV);
        resultCard_RCV.setLayoutManager(new LinearLayoutManager(ctx));

        resultCardInfoArrayList = new ArrayList<>();
        myAdapter = new resultCardViewHolder(ctx, gameCardInfoArrayList);
        resultCard_RCV.setAdapter(myAdapter);
    }

    public static void refresh(){
        myAdapter.notifyDataSetChanged();
    }


}
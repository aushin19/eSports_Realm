package com.cornerdesk.esportrealm.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.cornerdesk.esportrealm.GetOngoingMatches;
import com.cornerdesk.esportrealm.Helper.GetOngoingGameInfo;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.ViewHolder.ongoingameCardViewHolder;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOngoing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOngoing extends Fragment {

    public static RecyclerView ongoinggameCard_RCV;
    public static LottieAnimationView animSad;
    public static TextView noMatches_TV;
    static ongoingameCardViewHolder myAdapter;
    static Context ctx;
    static View viewOK;
    public static ShimmerFrameLayout shimmerFrameLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyOngoing() {
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
    public static MyOngoing newInstance(String param1, String param2) {
        MyOngoing fragment = new MyOngoing();
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
        return inflater.inflate(R.layout.fragment_my_ongoing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewOK = getView();
        ctx = getContext();

        noMatches_TV = view.findViewById(R.id.noMatches_TV);
        shimmerFrameLayout = view.findViewById(R.id.shimmer2);
        animSad = view.findViewById(R.id.animSad_LT);
        ongoinggameCard_RCV = view.findViewById(R.id.ongoinggameCard_RCV);

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        new GetOngoingMatches(getContext(), view);
    }

    public static void getUpcomingMatches(View view, ArrayList gameCardInfoArrayList ){
        ongoinggameCard_RCV.setLayoutManager(new LinearLayoutManager(ctx));
        myAdapter = new ongoingameCardViewHolder(ctx, gameCardInfoArrayList);
        ongoinggameCard_RCV.setAdapter(myAdapter);
    }

    public static void refresh(){
        myAdapter.notifyDataSetChanged();
    }

}
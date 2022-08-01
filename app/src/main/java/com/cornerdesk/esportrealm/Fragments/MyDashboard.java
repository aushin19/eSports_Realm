package com.cornerdesk.esportrealm.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cornerdesk.esportrealm.CheckScratchCard;
import com.cornerdesk.esportrealm.GetRoomDetails;
import com.cornerdesk.esportrealm.Helper.GetGameCardInfo;
import com.cornerdesk.esportrealm.Home;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.ViewHolder.gameCardViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDashboard extends Fragment {

    static RecyclerView gameCard_RCV;
    static ArrayList<GetGameCardInfo> gameCardInfoArrayList;
    static gameCardViewHolder myAdapter;
    static FirebaseFirestore db;
    static Context ctx;
    static View viewOK;
    static Activity activity;
    static ConstraintLayout noUpcomingContest;
    static SwipeRefreshLayout swipeRefreshDashboard;
    static CountDownTimer countDownTimer;
    Boolean canRefresh;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyDashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDashboard newInstance(String param1, String param2) {
        MyDashboard fragment = new MyDashboard();
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
        return inflater.inflate(R.layout.fragment_my_dashboard, container, false);
    }

    @SuppressLint("CutPasteId")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewOK = getView();
        ctx = getContext();
        activity = getActivity();
        swipeRefreshDashboard = viewOK.findViewById(R.id.swipeDashboard);

        canRefresh = false;

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                canRefresh = true;
            }
        }.start();

        swipeRefreshDashboard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(canRefresh){
                    getUpcomingMatches(viewOK);
                    EventChangeListener();
                    canRefresh = false;
                    countDownTimer.start();
                }
                swipeRefreshDashboard.setRefreshing(false);
            }
        });

        getUpcomingMatches(viewOK);
        EventChangeListener();
    }

    public static void getUpcomingMatches(View view){
        noUpcomingContest = view.findViewById(R.id.noUpcomingContest_CL);
        gameCard_RCV = view.findViewById(R.id.gameCard_RCV);
        gameCard_RCV.setLayoutManager(new LinearLayoutManager(ctx));

        db = FirebaseFirestore.getInstance();
        gameCardInfoArrayList = new ArrayList<>();
        myAdapter = new gameCardViewHolder(ctx, gameCardInfoArrayList);
        gameCard_RCV.setAdapter(myAdapter);

    }

    public static void EventChangeListener() {

        db.collection("GameCard").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try {
                    if(!task.isComplete()){
                        noMatches();
                        return;
                    }

                    for(DocumentChange dc : task.getResult().getDocumentChanges()){

                        if(dc.getType() == DocumentChange.Type.ADDED){
                            gameCardInfoArrayList.add(dc.getDocument().toObject(GetGameCardInfo.class));
                        }

                        if(dc.getType() == DocumentChange.Type.MODIFIED){
                            gameCardInfoArrayList.remove(dc.getOldIndex());
                            gameCardInfoArrayList.add(dc.getOldIndex(), dc.getDocument().toObject(GetGameCardInfo.class));
                        }

                        myAdapter.notifyDataSetChanged();

                    }

                    /*Home.shimmerFrameLayout.stopShimmer();
                    Home.shimmerFrameLayout.setVisibility(View.GONE);

                    Home.wormDotsIndicator.setVisibility(View.VISIBLE);
                    Home.viewPager2.setVisibility(View.VISIBLE);
                    Home.matchStatus_TV.setVisibility(View.VISIBLE);
                    Home.pager2.setVisibility(View.VISIBLE);
                    Home.mainLayout1.setVisibility(View.VISIBLE);
                    Home.mainLayout2.setVisibility(View.VISIBLE);*/
                } catch (Exception e) {
                    e.printStackTrace();
                    noMatches();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                noMatches();
            }
        });

        /*db.collection("GameCard")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if(e != null){
                            return;
                        }

                        if(queryDocumentSnapshots.isEmpty())
                            noMatches();

                        for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                gameCardInfoArrayList.add(dc.getDocument().toObject(GetGameCardInfo.class));
                            }

                            if(dc.getType() == DocumentChange.Type.MODIFIED){
                                gameCardInfoArrayList.remove(dc.getOldIndex());
                                gameCardInfoArrayList.add(dc.getOldIndex(), dc.getDocument().toObject(GetGameCardInfo.class));
                            }

                            myAdapter.notifyDataSetChanged();

                        }

                    }
                });*/
    }

    public static void noMatches(){
        gameCard_RCV.setVisibility(View.GONE);
        swipeRefreshDashboard.setVisibility(View.GONE);
        noUpcomingContest.setVisibility(View.VISIBLE);
    }

    public static void refreshDashboard(){
        getUpcomingMatches(viewOK);
        EventChangeListener();
    }

}
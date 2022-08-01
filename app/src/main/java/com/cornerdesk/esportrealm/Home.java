package com.cornerdesk.esportrealm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cornerdesk.esportrealm.Helper.AsyncTasks;
import com.cornerdesk.esportrealm.Helper.GetRoomInfo;
import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.cornerdesk.esportrealm.Helper.SliderItem;
import com.cornerdesk.esportrealm.Helper.addContest;
import com.cornerdesk.esportrealm.Helper.callBottomSheet;
import com.cornerdesk.esportrealm.Helper.callScratchCard;
import com.cornerdesk.esportrealm.ViewHolder.FragmentAdapter;
import com.cornerdesk.esportrealm.ViewHolder.SliderAdapter;
import com.cornerdesk.esportrealm.notificationServices.Simple_Notification;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class Home extends AppCompatActivity {

    public static Activity home;
    public static ViewPager2 pager2;
    public static ViewPager2 viewPager2;
    public static ConstraintLayout mainLayout1, mainLayout2;
    FragmentAdapter adapter;
    Button addMoney_BTN;
    ImageButton dashBoard_IBTN, dashBoard_IBTN2, dashBoard_IBTN3, dashBoard_IBTN4;
    public static TextView matchStatus_TV;
    public static WormDotsIndicator wormDotsIndicator;
    MaterialCardView upcoming_CV, ongoing_CV, result_CV, wallet_CV;
    static TextView matchName, matchID, matchPass;
    static ConstraintLayout roomDetails_IN;
    static ConstraintLayout scratch_IN;
    static ImageView copy_rDetails, copy_rDetails2;

    @SuppressLint("StaticFieldLeak")
    public static TextView subtitle_TV1, subtitle_TV2, subtitle_TV3, coin_TV, wallet_TV;

    public static List<SliderItem> sliderItem;

    public static Context ctx;
    public static Activity activity;

    public static Handler sliderHandler = new Handler();

    Animation anim;

    public static ShimmerFrameLayout shimmerFrameLayout;
    private FirebaseAnalytics mFirebaseAnalytics; // Firebase Analytics Stats


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home = this;
        ctx = getApplicationContext();
        activity = this;

        changeStatusBarColor(R.color.font);

        pager2 = findViewById(R.id.view_pager2);
        viewPager2 = findViewById(R.id.adImages_VP2);
        wormDotsIndicator = findViewById(R.id.wormDotsIndicator);

        shimmerFrameLayout = findViewById(R.id.shimmer);

        mainLayout1 = findViewById(R.id.mainLayout1);
        mainLayout2 = findViewById(R.id.mainLayout2);

        dashBoard_IBTN = findViewById(R.id.dashBoard_IBTN);
        dashBoard_IBTN2 = findViewById(R.id.dashBoard_IBTN2);
        dashBoard_IBTN3 = findViewById(R.id.dashBoard_IBTN3);
        dashBoard_IBTN4 = findViewById(R.id.dashBoard_IBTN4);

        subtitle_TV1 = findViewById(R.id.subtitle_TV1);
        subtitle_TV2 = findViewById(R.id.subtitle_TV2);
        subtitle_TV3 = findViewById(R.id.subtitle_TV3);

        coin_TV = findViewById(R.id.coin_TV);
        wallet_TV = findViewById(R.id.wallet_TV);

        matchStatus_TV = findViewById(R.id.matchStatus_TV);

        addMoney_BTN = findViewById(R.id.addMoney_BTN);

        upcoming_CV = findViewById(R.id.upcoming_CV);
        ongoing_CV = findViewById(R.id.ongoing_CV);
        result_CV = findViewById(R.id.result_CV);
        wallet_CV = findViewById(R.id.wallet_CV);

        matchName = findViewById(R.id.matchName_TV);
        matchID = findViewById(R.id.matchID_TV);
        matchPass = findViewById(R.id.matchPass_TV);

        roomDetails_IN = findViewById(R.id.roomDetails_IN);
        scratch_IN = findViewById(R.id.scratch_IN);

        copy_rDetails = findViewById(R.id.copyRoom_IMG);
        copy_rDetails2 = findViewById(R.id.copyRoom_IMG2);

        copy_rDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("rID", matchID.getText().toString().replace("Room ID : ", ""));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Home.this, "Room ID Copied", Toast.LENGTH_SHORT).show();
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.pop_up);
                copy_rDetails.startAnimation(anim);
            }
        });

        copy_rDetails2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("rPass", matchPass.getText().toString().replace("Room Pass : ", ""));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Home.this, "Room Pass Copied", Toast.LENGTH_SHORT).show();
                Animation anim = AnimationUtils.loadAnimation(Home.this, R.anim.pop_up);
                copy_rDetails2.startAnimation(anim);
            }
        });

        findViewById(R.id.notification_CV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, AllNotification.class));
            }
        });

        findViewById(R.id.scratch_IN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new callScratchCard(Home.this);
            }
        });

        new GetPoster(this);
        new GetTodayDate(this);
        getPlayerInfo();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this); // Firebase Analytics Stats

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        FragmentManager fragmentManager = getSupportFragmentManager();

      new AsyncTasks() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void doInBackground() {
                pager2.setOffscreenPageLimit(4);
            }

            @Override
            public void onPostExecute() {
                pager2.setAdapter(new FragmentAdapter(fragmentManager, getLifecycle()));

                if(!isShutdown())
                    shutdown();
            }
        }.execute();



        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

            }
        });

        dashBoard_IBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(0);
            }
        });

        dashBoard_IBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(1);
            }
        });

        dashBoard_IBTN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(2);
            }
        });

        dashBoard_IBTN4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(3);
            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setBtnColor(pager2.getCurrentItem());
            }
        });

        addMoney_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new callBottomSheet(Home.this).addMoney();
            }
        });

        List<SliderItem> imageList;
        imageList = new ArrayList<>();
        imageList.add(new SliderItem("", "https://google.com"));
        imageSlider(imageList);
    }

    public static void imageSlider(List<SliderItem> imageList) {

        sliderItem = imageList;
        viewPager2.setAdapter(new SliderAdapter(ctx, sliderItem, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                //page.setScaleY(1 - Math.abs(position));
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });
        wormDotsIndicator.setViewPager2(viewPager2);
    }

    private void setBtnColor(int currentItem) {

        anim = AnimationUtils.loadAnimation(this, R.anim.pop_up);

        dashBoard_IBTN.setBackgroundTintList(getColorStateList(R.color.grey));
        dashBoard_IBTN2.setBackgroundTintList(getColorStateList(R.color.grey));
        dashBoard_IBTN3.setBackgroundTintList(getColorStateList(R.color.grey));
        dashBoard_IBTN4.setBackgroundTintList(getColorStateList(R.color.grey));

        subtitle_TV1.setTextColor(getColor(R.color.grey));
        subtitle_TV2.setTextColor(getColor(R.color.grey));
        subtitle_TV3.setTextColor(getColor(R.color.grey));

        upcoming_CV.setCardBackgroundColor(getColor(R.color.font));
        ongoing_CV.setCardBackgroundColor(getColor(R.color.font));
        result_CV.setCardBackgroundColor(getColor(R.color.font));
        wallet_CV.setCardBackgroundColor(getColor(R.color.font));

        switch(currentItem){
            case 0 : dashBoard_IBTN.setBackgroundTintList(getColorStateList(R.color.green));
                subtitle_TV1.setTextColor(getColor(R.color.trans_green));
                upcoming_CV.setCardBackgroundColor(getColor(R.color.trans_green));
                dashBoard_IBTN.startAnimation(anim);
                     matchStatus_TV.setText("Upcoming Matches");
                     break;
            case 1 : dashBoard_IBTN2.setBackgroundTintList(getColorStateList(R.color.green));
                subtitle_TV2.setTextColor(getColor(R.color.trans_green));
                ongoing_CV.setCardBackgroundColor(getColor(R.color.trans_green));
                dashBoard_IBTN2.startAnimation(anim);
                     matchStatus_TV.setText("Ongoing Matches");
                break;
            case 2 : dashBoard_IBTN3.setBackgroundTintList(getColorStateList(R.color.green));
                subtitle_TV3.setTextColor(getColor(R.color.trans_green));
                result_CV.setCardBackgroundColor(getColor(R.color.trans_green));
                dashBoard_IBTN3.startAnimation(anim);
                     matchStatus_TV.setText("Results");
                break;

            case 3 : dashBoard_IBTN4.setBackgroundTintList(getColorStateList(R.color.green));
                wallet_CV.setCardBackgroundColor(getColor(R.color.trans_green));
                dashBoard_IBTN4.startAnimation(anim);
                matchStatus_TV.setText("Wallet");
                break;
        }
    }

    private  static Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if(sliderItem.size() == viewPager2.getCurrentItem() + 1)
                viewPager2.setCurrentItem(0);
            else
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    public void changeStatusBarColor(int color) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(this.getColor(R.color.font));
    }

    public static void getPlayerInfo(){
        coin_TV.setText(String.valueOf(PlayerInfo.getCoin(ctx)));
        wallet_TV.setText("₹" + String.valueOf(PlayerInfo.getWallet(ctx)));
    }

    public static void getRoomDetails(ArrayList<GetRoomInfo> item) {

        GetRoomInfo rDetails = item.get(0);

        if(addContest.getContest(rDetails.matchName)){
            Home.roomDetails_IN.setVisibility(View.VISIBLE);
            matchName.setText(rDetails.matchName + " Room Details");
            matchID.setText("Room ID : " + rDetails.matchID);
            matchPass.setText("Room Pass : " + rDetails.matchPass);
        }
    }

    @Override
    public void onBackPressed() {
        BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Exit", R.drawable.ic_check, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        mDialog.show();
    }

}
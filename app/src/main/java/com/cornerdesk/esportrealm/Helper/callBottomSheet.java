package com.cornerdesk.esportrealm.Helper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cornerdesk.esportrealm.BillingService;
import com.cornerdesk.esportrealm.Fragments.MyDashboard;
import com.cornerdesk.esportrealm.Fragments.MyWallet;
import com.cornerdesk.esportrealm.Home;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.Register;
import com.google.android.material.textfield.TextInputLayout;

public class callBottomSheet {

    public static Dialog dialog;
    public static ConstraintLayout moreInfo_CL;
    public static ConstraintLayout refer_CL;
    public static ConstraintLayout withdrawReq_CL;
    public static ConstraintLayout addCoin_CL;
    public static ConstraintLayout addMoneySuccess_CL;
    public static ConstraintLayout addMoneyFail_CL;
    public static ConstraintLayout joinContest_CL;
    public static ConstraintLayout signupSuccess_CL;
    public static ConstraintLayout loginSuccess_CL;
    public static ConstraintLayout registration_CL;

    public static TextView infoTitle, infoMessage, failReason_TV, payID_TV, coinBal_TV, entryCoin_TV, currentCoin_TV, toPayCoin_TV, insufficientCoin_TV, currentCash_TV, paySuccessTitle_TV;
    public static Button infoButton, payFail_BTN, paySuccess_BTN, joinContest_BTN, addMoneyContest_BTN, submitReg_BTN;
    public static ImageView infoImage;
    public static Context ctx;
    private static String entryCoins;
    public int moneyAmt = 19;
    public static int SpotsLeft;
    public static ImageButton close_IBTN;
    public static String docID;
    public static String subtopic;
    public static EditText userName_TB, charID_TB;
    TextInputLayout userName_TIL, charID_TIL;

   public callBottomSheet(Context ctx){

       this.ctx = ctx;

       dialog = new Dialog(ctx);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setContentView(R.layout.bottomsheet);

       moreInfo_CL = dialog.findViewById(R.id.moreInfo_CL);
       withdrawReq_CL = dialog.findViewById(R.id.withdrawReq_CL);
       refer_CL = dialog.findViewById(R.id.refer_CL);
       addCoin_CL = dialog.findViewById(R.id.addCoin_CL);
       addMoneySuccess_CL = dialog.findViewById(R.id.addMoneySuccess_CL);
       addMoneyFail_CL = dialog.findViewById(R.id.addMoneyFail_CL);
       joinContest_CL = dialog.findViewById(R.id.joinContest_CL);
       signupSuccess_CL = dialog.findViewById(R.id.signupSuccess_CL);
       loginSuccess_CL = dialog.findViewById(R.id.loginSuccess_CL);
       registration_CL = dialog.findViewById(R.id.registration_CL);

       RadioButton upiID_RBTN = dialog.findViewById(R.id.upiID_RBTN);
       RadioButton paytm_RBTN = dialog.findViewById(R.id.paytm_RBTN);
       currentCash_TV = dialog.findViewById(R.id.currentCash_TV);
       Button redeem_BTN = dialog.findViewById(R.id.redeem_BTN);
       EditText amt_TB = dialog.findViewById(R.id.amt_TB);
       EditText detail_TB = dialog.findViewById(R.id.detail_TB);

       close_IBTN = dialog.findViewById(R.id.close_IBTN);

       infoTitle = dialog.findViewById(R.id.infoTitle);
       infoMessage = dialog.findViewById(R.id.infoMessage);
       failReason_TV = dialog.findViewById(R.id.failReason_TV);
       payID_TV = dialog.findViewById(R.id.payID_TV);
       coinBal_TV = dialog.findViewById(R.id.coinBal_TV3);
       paySuccessTitle_TV = dialog.findViewById(R.id.paySuccessTitle_TV);

       TextView amtAddDisplay_TV = dialog.findViewById(R.id.amtAddDisplay_TV);
       TextView addCoin_TV = dialog.findViewById(R.id.addCoin_TV);

       infoButton = dialog.findViewById(R.id.infoButton);

       infoImage = dialog.findViewById(R.id.infoImage);

       EditText addAmt_TB = dialog.findViewById(R.id.addAmt_TB);
       Button amt1_BTN = dialog.findViewById(R.id.amt1_BTN);
       Button amt2_BTN = dialog.findViewById(R.id.amt2_BTN);
       Button amt3_BTN = dialog.findViewById(R.id.amt3_BTN);
       Button amt4_BTN = dialog.findViewById(R.id.amt4_BTN);
       CardView addMoney_FBTN = dialog.findViewById(R.id.addMoney_FBTN);

       toPayCoin_TV = dialog.findViewById(R.id.toPayCoin_TV);
       currentCoin_TV = dialog.findViewById(R.id.currentCoin_TV);
       entryCoin_TV = dialog.findViewById(R.id.entryCoin_TV);

       insufficientCoin_TV = dialog.findViewById(R.id.insufficientCoin_TV);

       paySuccess_BTN = dialog.findViewById(R.id.paySuccess_BTN);
       payFail_BTN = dialog.findViewById(R.id.payFail_BTN);

       joinContest_BTN = dialog.findViewById(R.id.joinContest_BTN);
       addMoneyContest_BTN = dialog.findViewById(R.id.addMoneyContest_BTN);
       submitReg_BTN = dialog.findViewById(R.id.submitReg_BTN);

       userName_TB = dialog.findViewById(R.id.userName_TB);
       userName_TIL = dialog.findViewById(R.id.userName_TIL);
       charID_TB = dialog.findViewById(R.id.charID_TB);
       charID_TIL = dialog.findViewById(R.id.charID_TIL);

       moreInfo_CL.setVisibility(View.GONE);
       withdrawReq_CL.setVisibility(View.GONE);
       refer_CL.setVisibility(View.GONE);
       addCoin_CL.setVisibility(View.GONE);
       addMoneySuccess_CL.setVisibility(View.GONE);
       addMoneyFail_CL.setVisibility(View.GONE);
       joinContest_CL.setVisibility(View.GONE);
       signupSuccess_CL.setVisibility(View.GONE);
       loginSuccess_CL.setVisibility(View.GONE);
       registration_CL.setVisibility(View.GONE);

       upiID_RBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(paytm_RBTN.isChecked())
                   paytm_RBTN.setChecked(false);
               detail_TB.setHint("Enter UPI ID / Mobile Number");
           }
       });

       paytm_RBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(upiID_RBTN.isChecked())
                   upiID_RBTN.setChecked(false);
               detail_TB.setHint("Enter Paytm Number");
           }
       });

       redeem_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!amt_TB.getText().toString().trim().isEmpty()) {
                   if(Integer.parseInt(amt_TB.getText().toString().trim()) <= PlayerInfo.getWallet(ctx)){
                      if(!detail_TB.getText().toString().trim().isEmpty()){
                          new WithdrawCash(ctx,Integer.parseInt(amt_TB.getText().toString().trim())
                          ,detail_TB.getText().toString().trim());

                          TransactionInfo.addTransaction(ctx,"-",amt_TB.getText().toString().trim(),
                                  "Cash Withdrew!", "Cash withdrew worth of ₹" + Integer.parseInt(amt_TB.getText().toString().trim()));

                          callBottomSheet.closeBottomSheet();
                          new callBottomSheet(ctx).addMoneySuccess(ctx.getString(R.string.withdraw_policy), "Withdraw Successfull!");
                          MyWallet.refreshWallet(ctx);
                      }else{
                          Toast.makeText(ctx, "Enter Correct Details!", Toast.LENGTH_SHORT).show();
                      }
                   }else{
                       Toast.makeText(ctx, "Not enough funds!", Toast.LENGTH_SHORT).show();
                   }
               } else {
                   Toast.makeText(ctx, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
               }
           }
       });

       close_IBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(dialog.isShowing())
                   dialog.dismiss();
               if(signupSuccess_CL.getVisibility() == View.VISIBLE)
                   Register.slideScreen(0);
           }
       });

       amt1_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               moneyAmt = 19;
               addAmt_TB.setText("₹" + moneyAmt);
               amtAddDisplay_TV.setText("x 10 = " + moneyAmt*10);
               addCoin_TV.setText("ADD " + moneyAmt*10);
           }
       });

       amt2_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               moneyAmt = 30;
               addAmt_TB.setText("₹" + moneyAmt);
               amtAddDisplay_TV.setText("x 10 = " + moneyAmt*10);
               addCoin_TV.setText("ADD " + moneyAmt*10);
           }
       });

       amt3_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               moneyAmt = 65;
               addAmt_TB.setText("₹" + moneyAmt);
               amtAddDisplay_TV.setText("x 10 = " + moneyAmt*10);
               addCoin_TV.setText("ADD " + moneyAmt*10);
           }
       });

       amt4_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               moneyAmt = 80;
               addAmt_TB.setText("₹" + moneyAmt);
               amtAddDisplay_TV.setText("x 10 = " + moneyAmt*10);
               addCoin_TV.setText("ADD " + moneyAmt*10);
           }
       });

       addMoney_FBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!addAmt_TB.getText().toString().isEmpty()){
                   Home.home.finish();
                   Intent i = new Intent(ctx, BillingService.class);
                   i.putExtra("amt", moneyAmt);
                   ctx.startActivity(i);
               }
           }
       });

       paySuccess_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(payID_TV.getText().equals("")
                       | payID_TV.getText().equals(ctx.getString(R.string.withdraw_policy))
                        | payID_TV.getText().toString().contains("You've")){
                   if(dialog.isShowing()){
                       dialog.dismiss();
                       return;
                   }
               }

               BillingService.billing_service.finish();
               Intent intent = new Intent(ctx, Home.class);
               ctx.startActivity(intent);
           }
       });

       payFail_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(failReason_TV.getText().equals("")){
                   if(dialog.isShowing()){
                       dialog.dismiss();
                       MyDashboard.refreshDashboard();
                       return;
                   }
               }

               BillingService.billing_service.finish();
               Intent intent = new Intent(ctx, Home.class);
               ctx.startActivity(intent);
           }
       });

       joinContest_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(dialog.isShowing())
                   dialog.dismiss();
               new callBottomSheet(ctx).showRegistration();
           }
       });

       submitReg_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!userName_TB.getText().toString().trim().isEmpty()){
                  if(!charID_TB.getText().toString().trim().isEmpty()){

                      new joinContest(ctx, entryCoins, SpotsLeft,
                              userName_TB.getText().toString().trim(), charID_TB.getText().toString().trim()).updateSpots(docID, subtopic, "NULL");
                      if(dialog.isShowing())
                          dialog.dismiss();
                      new callBottomSheet(ctx).loginSuccess();

                   }else{
                      charID_TIL.setError("Field can't be empty!");
                      charID_TIL.setErrorIconDrawable(ctx.getDrawable(R.drawable.ic_close));
                   }
               }else{
                   userName_TIL.setError("Field can't be empty!");
                   userName_TIL.setErrorIconDrawable(ctx.getDrawable(R.drawable.ic_close));
               }
           }
       });

       addMoneyContest_BTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(dialog.isShowing())
                   dialog.dismiss();
               new callBottomSheet(ctx).addMoney();
           }
       });

       dialog.show();
       dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       dialog.getWindow().getAttributes().windowAnimations = R.style.bottomSheetAnimation;
       dialog.getWindow().setGravity(Gravity.BOTTOM);
       dialog.setCancelable(false);

   }

   public static void moreInfo(String Title, String Message, Drawable Image, String ButtonText, int Color){

       moreInfo_CL.setVisibility(View.VISIBLE);

       infoTitle.setText(Title);
       infoMessage.setText(Message);

       infoImage.setBackground(Image);
       infoImage.setBackgroundTintList(ColorStateList.valueOf(Color));

       infoButton.setText(ButtonText);
       infoButton.setBackgroundTintList(ColorStateList.valueOf(Color));
   }

   public static void withdrawReq(){
       withdrawReq_CL.setVisibility(View.VISIBLE);
       currentCash_TV.setText("₹" + PlayerInfo.getWallet(ctx));
   }

   public static void refer(){

       refer_CL.setVisibility(View.VISIBLE);

   }

   public static void addMoney(){

       coinBal_TV.setText(String.valueOf(PlayerInfo.getCoin(ctx)));
       addCoin_CL.setVisibility(View.VISIBLE);

   }

    public static void addMoneySuccess(String id, String title){

        payID_TV.setText(id);
        paySuccessTitle_TV.setText(title);
        addMoneySuccess_CL.setVisibility(View.VISIBLE);
        close_IBTN.setVisibility(View.GONE);

        new CountDownTimer(5000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                Animation anim = AnimationUtils.loadAnimation(ctx, R.anim.slide_in_bottom);
                paySuccess_BTN.startAnimation(anim);
                paySuccess_BTN.setVisibility(View.VISIBLE);
            }
        }.start();

    }

    public static void addMoneyFail(String reason){

        failReason_TV.setText(reason);
        addMoneyFail_CL.setVisibility(View.VISIBLE);
        close_IBTN.setVisibility(View.GONE);

        new CountDownTimer(5000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                //payFail_BTN.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(ctx, R.anim.slide_in_bottom);
                payFail_BTN.startAnimation(anim);
                payFail_BTN.setVisibility(View.VISIBLE);
            }
        }.start();

    }

    public static void joinContest(String msg, String entryFee, String doc, String topic, int spotsLeft){
       SpotsLeft = spotsLeft;
       entryCoins = entryFee;
        entryCoin_TV.setText(entryFee);
        currentCoin_TV.setText(String.valueOf(PlayerInfo.getCoin(ctx)));
        toPayCoin_TV.setText(entryFee);
        joinContest_BTN.setVisibility(View.GONE);
        addMoneyContest_BTN.setVisibility(View.GONE);
        if(msg.equals("insufficient")){
            addMoneyContest_BTN.setVisibility(View.VISIBLE);
            insufficientCoin_TV.setText("Insufficient coins");
            insufficientCoin_TV.setTextColor(ctx.getColor(R.color.fail));
        }
        else{
            joinContest_BTN.setVisibility(View.VISIBLE);
        }
        joinContest_CL.setVisibility(View.VISIBLE);
        docID = doc;
        subtopic = topic;
    }

    public static void  showRegistration(){
       registration_CL.setVisibility(View.VISIBLE);
    }

    public static void signupSuccess(){

        signupSuccess_CL.setVisibility(View.VISIBLE);

    }

    public static void loginSuccess(){
        close_IBTN.setVisibility(View.GONE);
        loginSuccess_CL.setVisibility(View.VISIBLE);
    }

    public static void closeLoginSuccess(){
        if(dialog.isShowing())
            dialog.dismiss();
    }

    public static void closeBottomSheet(){
        if(dialog.isShowing())
            dialog.dismiss();
    }

}

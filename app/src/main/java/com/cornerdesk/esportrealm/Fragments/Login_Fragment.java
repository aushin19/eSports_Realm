package com.cornerdesk.esportrealm.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.cornerdesk.esportrealm.Helper.addContest;
import com.cornerdesk.esportrealm.Helper.callBottomSheet;
import com.cornerdesk.esportrealm.Helper.PlayerInfo;
import com.cornerdesk.esportrealm.Home;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.Register;
import com.cornerdesk.esportrealm.notificationServices.subTopic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_Fragment extends Fragment {

    TextView signupSwitch_BTN;
    TextInputLayout loginUserName_TIL, loginPassword_TIL;
    EditText loginUserName_TB, loginPassword_TB;
    MaterialButton login_BTN;
    String userEnteredUsername;
    String RefCode, RefBy;
    String MailID;
    String participation;
    String last_check_in;
    int Coin;
    int Wallet;
    ValueEventListener valueEventListener;
    DatabaseReference reference;

    //declarations


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_Fragment newInstance(String param1, String param2) {
        Login_Fragment fragment = new Login_Fragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        signupSwitch_BTN = getView().findViewById(R.id.signupSwitch_BTN); //Slide to signUp form
        loginUserName_TB = getView().findViewById(R.id.charID_TB); //TextBox
        loginPassword_TB = getView().findViewById(R.id.loginPassword_TB); //TextBox

        loginUserName_TIL = getView().findViewById(R.id.userName_TIL); //Username setError Wale TIL
        loginPassword_TIL = getView().findViewById(R.id.loginPassword_TIL); //Password setError Wale TIL

        login_BTN = getView().findViewById(R.id.login_BTN);

        login_BTN.setOnClickListener(new View.OnClickListener() { //Actual Login Button
            @Override
            public void onClick(View v) {
                // button click activity from here
            loginUser(v);

            }
        });

        signupSwitch_BTN.setOnClickListener(new View.OnClickListener() { //Slide to Signup Form
            @Override
            public void onClick(View v) {
                Register.slideScreen(1);
            }
        });


        //write code from here

    }

    // functions from here
    private boolean ValidateUsername()
    {
        /*String val=loginUserName_TB.getText().toString();
        if(val.isEmpty())
        {
            loginUserName_TIL.setError("Enter Username");
            return false;
        }
        else
        {
            loginUserName_TIL.setError(null);
            loginUserName_TIL.setErrorEnabled(false);
            return true;
        }*/

        String val=loginUserName_TB.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty())
        {
            loginUserName_TIL.setError("Enter Username");
            return false;
        }else if(val.length()<=4)
        {
            loginUserName_TIL.setError("Username Too Short");
            return false;
        }else if(val.length()>=15)
        {
            loginUserName_TIL.setError("Username Too Long");
            return false;
        }else if(!val.matches(noWhiteSpace))
        {
            loginUserName_TIL.setError("No special Character are not allowed");
            return false;
        }
        else
        {
            loginUserName_TIL.setError(null);
            loginUserName_TIL.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean ValidatePassword() {
        String val = loginPassword_TB.getText().toString();
        if (val.isEmpty())
        {
            loginPassword_TIL.setError("Field cannot be empty");
            return false;
        }else
            {
            loginPassword_TIL.setError(null);
            loginPassword_TIL.setErrorEnabled(false);
            return true;
        }
    }
    public void loginUser(View view) {
        //Validate Login Info
        if (!ValidateUsername() | !ValidatePassword()) {
            return;
        } else{
            isUser();
        }
    }

    private void isUser()
    {
        userEnteredUsername = loginUserName_TB.getText().toString().trim();
        String userEnteredPassword = loginPassword_TB.getText().toString().trim();

        valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginUserName_TIL.setError(null);
                    loginUserName_TIL.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child("password").getValue().toString();
                    MailID = snapshot.child("email").getValue().toString();
                    RefCode = snapshot.child("refCode").getValue().toString();
                    RefBy = snapshot.child("refBy").getValue().toString();
                    Coin = Integer.parseInt(snapshot.child("wallet").child("coins").getValue().toString());
                    Wallet = Integer.parseInt(snapshot.child("wallet").child("cash").getValue().toString());
                    participation = snapshot.child("participation").getValue().toString();
                    last_check_in = snapshot.child("last_check_in").getValue().toString();

                    if (passwordFromDB.equals(userEnteredPassword)) {
                        loginUserName_TIL.setError(null);
                        loginUserName_TIL.setErrorEnabled(false);
                        new callBottomSheet(getContext()).loginSuccess();
                        storeTinyDB();

                    } else {
                        loginPassword_TIL.setError("Wrong Details Entered");
                        loginPassword_TIL.requestFocus();
                        loginUserName_TIL.setError("Wrong Details Entered");
                        loginUserName_TIL.requestFocus();
                    }
                } else {
                    loginUserName_TIL.setError("No such User exist");
                    loginUserName_TIL.requestFocus();
                }

                if(valueEventListener!=null){
                    reference.removeEventListener(valueEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Please try again after some time!", Toast.LENGTH_SHORT).show();

                if(valueEventListener!=null){
                    reference.removeEventListener(valueEventListener);
                }
            }
        };

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userEnteredUsername);
        reference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void storeTinyDB(){

        PlayerInfo.setName(getContext(),userEnteredUsername);
        PlayerInfo.setMailID(getContext(),MailID);

        PlayerInfo.setRefCode(getContext(),RefCode);
        PlayerInfo.setRefBy(getContext(),RefBy);
        PlayerInfo.setCoin(getContext(),Coin);
        PlayerInfo.setWallet(getContext(),Wallet);

        PlayerInfo.setScratchDate(getContext(), last_check_in);

        if (!participation.equals("NULL")) {
            String info = participation;
            String[] infoList = info.split(",");
            for(String i : infoList){
               new addContest(getContext()).setContest(i.trim());
               new subTopic(getContext(), i.trim(), "Login");
            }
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                new callBottomSheet(getContext()).closeLoginSuccess();
                Intent intent = new Intent(getContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Register.register.finish();
                startActivity(intent);
            }
        },5000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null){
            reference.removeEventListener(valueEventListener);
        }
    }
}
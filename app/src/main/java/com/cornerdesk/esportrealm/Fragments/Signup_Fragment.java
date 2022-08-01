package com.cornerdesk.esportrealm.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cornerdesk.esportrealm.Helper.UserHelperClass;
import com.cornerdesk.esportrealm.Helper.callBottomSheet;
import com.cornerdesk.esportrealm.R;
import com.cornerdesk.esportrealm.Register;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signup_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup_Fragment extends Fragment {

    TextView loginSwitch_BTN;
    TextInputLayout signUpUserName_TIL, signUpPassword_TIL, signUpEmail_TIL, signUpReferral_TIL;
    EditText signUpUserName_TB, signUpPassword_TB, signUpEmail_TB, signUpReferral_TB;
    MaterialButton signUp_BTN;
    int REFER_COIN = 50;
    ValueEventListener valueEventListener, valueEventListener2, valueEventListener3, valueEventListener4;

    //declarations
    DatabaseReference reference;
    DatabaseReference reference2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Signup_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Signup_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup_Fragment newInstance(String param1, String param2) {
        Signup_Fragment fragment = new Signup_Fragment();
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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        loginSwitch_BTN = getView().findViewById(R.id.loginSwitch_BTN);

        //actual signup BTN
        signUp_BTN = getView().findViewById(R.id.signUp_BTN);

        // textBoxes
        signUpUserName_TB = getView().findViewById(R.id.signUpUserName_TB);
        signUpPassword_TB = getView().findViewById(R.id.signUpPassword_TB);
        signUpEmail_TB = getView().findViewById(R.id.signUpEmail_TB);
        signUpReferral_TB = getView().findViewById(R.id.signUpReferral_TB);

        // set Error wale Text input Layout
        signUpUserName_TIL = getView().findViewById(R.id.signUpUserName_TIL);
        signUpPassword_TIL = getView().findViewById(R.id.signUpPassword_TIL);
        signUpEmail_TIL = getView().findViewById(R.id.signUpEmail_TIL);
        signUpReferral_TIL = getView().findViewById(R.id.signUpReferral_TIL);

        loginSwitch_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.slideScreen(0);
            }
        });


        signUp_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // button click activity from here
                /*rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Users");*/
                registerUser(v);
            }
        });

        //write code from here

    }

    // functions from here
    private boolean ValidateUsername()
    {
        String val=signUpUserName_TB.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty())
        {
            signUpUserName_TIL.setError("Enter Username");
            return false;
        }else if(val.length()<=4)
        {
            signUpUserName_TIL.setError("Username Too Short");
            return false;
        }else if(val.length()>=15)
        {
            signUpUserName_TIL.setError("Username Too Long");
            return false;
        }else if(!val.matches(noWhiteSpace))
        {
            signUpUserName_TIL.setError("No special Character are not allowed");
            return false;
        }
        else
        {
            signUpUserName_TIL.setError(null);
            signUpUserName_TIL.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = signUpPassword_TB.getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            signUpPassword_TIL.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            signUpPassword_TIL.setError("Password is too weak");
            return false;
        } else {
            signUpPassword_TIL.setError(null);
            signUpPassword_TIL.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = signUpEmail_TB.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            signUpEmail_TIL.setError("Email cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            signUpEmail_TIL.setError("Invalid email address");
            return false;
        } else {
            signUpEmail_TIL.setError(null);
            signUpEmail_TIL.setErrorEnabled(false);
            return true;
        }
    }
    public void registerUser(View view)
    {
        if(!ValidateUsername() |!validatePassword()  | !validateEmail() )
        {
            return;
        }

        String username=signUpUserName_TB.getText().toString().trim();
        String password=signUpPassword_TB.getText().toString().trim();
        String email=signUpEmail_TB.getText().toString().trim();
        String refCode=signUpReferral_TB.getText().toString().trim();

        isUserExists(username, password, email, refCode);
    }


    public void isUserExists(String username, String password, String email, String refCode){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    signUpUserName_TIL.setError("Username is already taken");
                }else{

                    String genRef;
                    genRef  = username + new Random().nextInt(9999);

                    if(username.length()>4){
                        genRef  = username.substring(0,4) + new Random().nextInt(9999);
                    }

                    if(refCode.isEmpty()){
                        UserHelperClass helperClass=new UserHelperClass(username,password,email,genRef.toUpperCase(),"NULL","NULL", "NULL");
                        reference.getParent().child(username).setValue(helperClass);

                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Referral");
                        reference2.child(genRef.toUpperCase()).setValue(username);

                        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Users")
                                .child(username).child("wallet").child("coins");
                        reference3.setValue(0);

                        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("Users")
                                .child(username).child("wallet").child("cash");
                        reference4.setValue(0);

                        clearTB();

                        new callBottomSheet(getContext()).signupSuccess();
                    }else{
                        isReferral(username,password,email,refCode);
                    }
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

        reference = FirebaseDatabase.getInstance().getReference("Users").child(username);
        reference.addListenerForSingleValueEvent(valueEventListener);

    }

    public void isReferral(String username, String password, String email, String refCode){

        reference2 = FirebaseDatabase.getInstance().getReference("Referral").child(refCode);

        valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String genRef;
                    genRef  = username + new Random().nextInt(9999);

                    if(username.length()>4){
                        genRef  = username.substring(0,4) + new Random().nextInt(9999);
                    }

                    String refBy = snapshot.getValue().toString();
                    UserHelperClass helperClass = new UserHelperClass(username,password,email,genRef.toUpperCase(),refBy, "NULL", "NULL");
                    reference.getParent().child(username).setValue(helperClass);

                    reference2.getParent().child(genRef.toUpperCase()).setValue(username);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                            .child(username).child("wallet").child("coins");
                    reference.setValue(REFER_COIN);

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users")
                            .child(username).child("wallet").child("cash");
                    reference1.setValue(0);

                    clearTB();

                    new callBottomSheet(getContext()).signupSuccess();
                }else{
                    signUpReferral_TIL.setError("Referral is not valid!");
                }

                if(valueEventListener2!=null){
                    reference2.removeEventListener(valueEventListener2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Please try again after some time!", Toast.LENGTH_SHORT).show();

                if(valueEventListener2!=null){
                    reference2.removeEventListener(valueEventListener2);
                }

            }
        };

        reference2.addListenerForSingleValueEvent(valueEventListener2);

    }

    public void clearTB(){

        signUpUserName_TB.setText("");
        signUpPassword_TB.setText("");
        signUpEmail_TB.setText("");
        signUpReferral_TB.setText("");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null){
            reference.removeEventListener(valueEventListener);
        }
        if(valueEventListener2!=null){
            reference2.removeEventListener(valueEventListener2);
        }
    }
}
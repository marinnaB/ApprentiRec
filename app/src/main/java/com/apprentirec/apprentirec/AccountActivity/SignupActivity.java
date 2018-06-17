package com.apprentirec.apprentirec.AccountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.apprentirec.apprentirec.MainActivity;
import com.apprentirec.apprentirec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputFirstName,inputName;
    private String firstName,name,email,password;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private RadioButton chosen,chosen2;
    private Boolean candidateOrNot,HROrNot;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseFirestore store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        store= FirebaseFirestore.getInstance();


        chosen= (RadioButton)findViewById(R.id.candidate);
        chosen2= (RadioButton)findViewById(R.id.hr);
        inputFirstName=(EditText) findViewById(R.id.firstName);
        inputName=(EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = inputFirstName.getText().toString().trim();
                name = inputName.getText().toString().trim();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                candidateOrNot = chosen.isChecked(); // check if candidate radiobutton is checked
                HROrNot = chosen2.isChecked(); // check if candidate radiobutton is checked

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(getApplicationContext(), "Enter your first name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (candidateOrNot==false && HROrNot==false){
                    Toast.makeText(getApplicationContext(), "Select candidate or HR", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                String nameTable,Email,fName,Name;
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {


                                    if (candidateOrNot==true){
                                        nameTable="Candidat";
                                        fName="firstNameC";
                                        Name="nameC";
                                    }
                                    else{
                                        nameTable="RH";
                                        fName="firstNameHR";
                                        Name="nameHR";

                                    }

                                    Map<String,String> userMap=new HashMap<>();
                                    userMap.put(fName,firstName);
                                    userMap.put(Name,name);

                                    //db.collection("cities").doc("LA").set({

                                    store.collection(nameTable).document(email).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressBar.setVisibility(View.GONE);

                                            Toast.makeText(SignupActivity.this, "User succesfully added.", Toast.LENGTH_SHORT).show();


                                            //rh or candidate login à completer
                                            if (candidateOrNot==true) {
                                                startActivity(new Intent(SignupActivity.this, CandidateProfileActivity.class));
                                                finish();
                                            }
                                            else{
                                                startActivity(new Intent(SignupActivity.this, HRProfileActivity.class));
                                                finish();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            String error=e.getMessage();
                                            Toast.makeText(SignupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            }
                        });


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
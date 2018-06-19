package com.apprentirec.apprentirec.AccountActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apprentirec.apprentirec.MainActivity;
import com.apprentirec.apprentirec.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseFirestore store;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    public static final String CLE = "CleEmailUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();


        //**********important, pour retenir la personne pour le login

        //if (auth.getCurrentUser() != null) {

            // si rh ou apprenti, à completer

        //    startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //    finish();
       // }


        // set the view now
        setContentView(R.layout.activity_login);


        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {


                                    DocumentReference docRef = store.collection("Candidat").document(email);
                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {

                                                    Intent intent = new Intent(LoginActivity.this, CandidateProfileActivity.class);
                                                    intent.putExtra(CLE, email);
                                                    startActivity(intent);
                                                    finish();

                                                } else {



                                                    DocumentReference docRef = store.collection("RH").document(email);
                                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot document = task.getResult();
                                                                if (document.exists()) {

                                                                    Intent intent = new Intent(LoginActivity.this, HRProfileActivity.class);
                                                                    startActivity(intent);
                                                                    finish();

                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Authentication failed: check your user name and password.", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Authentication failed.Please try again.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });



                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Authentication failed.Please try again", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                                }
                            }
                        });
            }
        });
    }
}
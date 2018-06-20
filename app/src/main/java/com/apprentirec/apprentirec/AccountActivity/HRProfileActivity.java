package com.apprentirec.apprentirec.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apprentirec.apprentirec.CV.ConsultCV;
import com.apprentirec.apprentirec.ListOfferHR;
import com.apprentirec.apprentirec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HRProfileActivity extends AppCompatActivity {

    public static String EmailUser;
    private FirebaseFirestore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrprofile);

        store = FirebaseFirestore.getInstance();

        final TextView nameHRTxtV = (TextView)findViewById(R.id.nameHR);

        Intent i = getIntent();
        EmailUser = i.getStringExtra(LoginActivity.CLE);

        final DocumentReference docRef = store.collection("RH").document(EmailUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    String firstNameHR = document.get("firstNameHR").toString();
                    String nameHR = document.get("nameHR").toString();
                    nameHRTxtV.setText(firstNameHR + " " + nameHR);
                }
            }
        });

        Button seeJobsHR = (Button) findViewById(R.id.seeJobsHR);

        seeJobsHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HRProfileActivity.this, ListOfferHR.class));
                onPause();
            }
        });

        Button ButLogout = (Button) findViewById(R.id.logoutHR);

        ButLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}

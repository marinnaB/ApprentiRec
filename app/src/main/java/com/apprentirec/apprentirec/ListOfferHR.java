package com.apprentirec.apprentirec;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apprentirec.apprentirec.AccountActivity.CandidateProfileActivity;
import com.apprentirec.apprentirec.AccountActivity.HRProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ListOfferHR extends AppCompatActivity {
    private FirebaseFirestore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offer_hr);

        store=FirebaseFirestore.getInstance();
        store.collection("Offre")
                .whereEqualTo(HRProfileActivity.EmailUser, true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        LinearLayout linearLayout = findViewById(R.id.Linear);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("OK" + task.toString(), document.getId() + " => " + document.getData());
                                Button test2 = new Button(ListOfferHR.super.getApplication());
                                //test2.setText(document.get("Titre").toString());
                                test2.setText("HAMDOULILAH");
                                test2.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
                                ((LinearLayout)linearLayout).addView(test2);
                            }
                        } else {
                            Log.d("KO", "Error getting documents: ", task.getException());
                        }
                    }
                });

        LinearLayout linearLayout = findViewById(R.id.Linear);
        Button test = new Button(this);
        test.setText("wallah je teste");
       //test.setId(Jean);
        test.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
       ((LinearLayout)linearLayout).addView(test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(HRProfileActivity.this, ListOfferHR.class));
                onPause();
            }
        });
    }
}

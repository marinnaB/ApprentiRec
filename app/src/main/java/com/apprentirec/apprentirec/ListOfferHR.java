package com.apprentirec.apprentirec;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

//import com.apprentirec.apprentirec.AccountActivity.CandidateProfileActivity;
import com.apprentirec.apprentirec.AccountActivity.HRProfileActivity;
import com.apprentirec.apprentirec.OfferHR_RecyclerView.Offer;
import com.apprentirec.apprentirec.OfferHR_RecyclerView.OfferAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListOfferHR extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore store;
    private List<Offer> offres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offer_hr);
        offres = new ArrayList<Offer>();

        store=FirebaseFirestore.getInstance();
        store.collection("Offre")
                .whereEqualTo(HRProfileActivity.EmailUser, true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //LinearLayout linearLayout = findViewById(R.id.Linear);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                offres.add(new Offer(document.getId(),document.get("Date").toString(),document.get("DuréeContrat").toString(),document.get("Entreprise").toString(),document.get("Lieu").toString(),document.get("ProfilDemande").toString(),document.get("Status").toString(),document.get("Titre").toString(),document.get("TypeContrat").toString()));
                                Log.d("OK" + task.toString(), document.getId() + " => " + document.getData());
                                //Button test2 = new Button(ListOfferHR.super.getApplication());
                                //test2.setText(document.get("Titre").toString());
                                //test2.setText("HAMDOULILAH");
                                //test2.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
                                //((LinearLayout)linearLayout).addView(test2);
                            }
                        } else {
                            Log.d("KO", "Error getting documents: ", task.getException());
                        }
                    }
                });

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OfferAdapter(offres));
    }
}

package com.apprentirec.apprentirec.Offer_p;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.apprentirec.apprentirec.R;

public class createOffer extends AppCompatActivity {

    //public static final int PICK_IMAGE = 1;
    private static final String TAG = "createOffer_class";
    private FirebaseFirestore store;
    // Trouver une fonction qui fait du texte aléatoire pour une clé unique
    //private String idOffer;
    private EditText e_date, e_duree, e_entreprise, e_lieu, e_profil, e_status, e_titre, e_typeContrat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = FirebaseFirestore.getInstance();
        //idOffer = ??;
        setContentView(R.layout.activity_create_offer);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_createOffer);

//        final DocumentReference docRef = store.collection("Offre").document(idOffer);

        e_date = (EditText) findViewById(R.id.et_date);
        e_duree = (EditText) findViewById(R.id.et_duree);
        e_entreprise = (EditText) findViewById(R.id.et_entreprise);
        e_lieu = (EditText) findViewById(R.id.et_lieu);

        e_profil = (EditText) findViewById(R.id.et_profil);
        e_status = (EditText) findViewById(R.id.et_status);
        e_titre = (EditText) findViewById(R.id.et_titre);
        e_typeContrat = (EditText) findViewById(R.id.et_typeC);

 /*       docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    e_date.setText(document.get("Date").toString());
                    e_duree.setText(document.get("DuréeContrat").toString());
                    e_entreprise.setText(document.get("Entreprise").toString());
                    e_lieu.setText(document.get("Tel").toString());

                    e_profil.setText(document.get("ProfilDemande").toString());
                    e_status.setText(document.get("Status").toString());
                    e_titre.setText(document.get("Titre").toString());
                    e_typeContrat.setText(document.get("TypeContrat").toString());
                }
            }
        });*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = e_date.getText().toString();
                String durée = e_duree.getText().toString();
                String entreprise = e_entreprise.getText().toString();
                String lieu = e_lieu.getText().toString();

                String profil= e_profil.getText().toString();
                String statut= e_status.getText().toString();
                String titre = e_titre.getText().toString();
                String typeContrat = e_typeContrat.getText().toString();

                if (date.isEmpty() || durée.isEmpty() || entreprise.isEmpty() || lieu.isEmpty()
                        || profil.isEmpty() || statut.isEmpty() || titre.isEmpty() || typeContrat.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Some Text fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //if (phone.length() < 10) {
                //    Toast.makeText(getApplicationContext(), "Phone number's format is Wrong!", Toast.LENGTH_SHORT).show();
                //    return;
                //}

                Map<String, Object> dataSave = new HashMap<>();

                dataSave.put("Date", date);
                dataSave.put("Duree", durée);
                dataSave.put("Entreprise", entreprise);
                dataSave.put("Lieu", lieu);

                dataSave.put("Profil", profil);
                dataSave.put("Statut", statut);
                dataSave.put("Titre", titre);
                dataSave.put("typeContrat", typeContrat);


                store.collection("Offre").document(idOffer).update(dataSave)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Offer Successfully Created!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(), "Error Creating an offer!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
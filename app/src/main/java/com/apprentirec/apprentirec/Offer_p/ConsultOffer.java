package com.apprentirec.apprentirec.Offer_p;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apprentirec.apprentirec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConsultOffer extends AppCompatActivity {

    private static final String TAG = "Consult_Offer_java";
    private FirebaseFirestore store;
    private TextView t_date,t_duree,t_entreprise, t_lieu, t_profil, t_statut, t_titre, t_typeContrat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_offer);

        t_date = (TextView)findViewById(R.id.tv_date);
        t_duree = (TextView) findViewById(R.id.tv_duree);
        t_entreprise = (TextView) findViewById(R.id.tv_entreprise);
        t_lieu = (TextView) findViewById(R.id.tv_lieu);
        t_profil = (TextView) findViewById(R.id.tv_lieu);
        t_statut = (TextView) findViewById(R.id.tv_statut);
        t_typeContrat = (TextView) findViewById(R.id.tv_typeC);
        t_titre = (TextView) findViewById(R.id.tv_titre);
        FloatingActionButton but_modifyOffer = (FloatingActionButton) findViewById(R.id.btn_modifyOffer);

        // Accès à cette activité à partir de la liste des offres, puis passer le id offre dans l'intent de ListOfferHR à ConsultOffer
        /*/final DocumentReference docRef = store.collection("Offre").document(id_offer);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    t_date.setText(document.get("Date").toString());
                    t_duree.setText(document.get("DuréeContrat").toString());
                    t_entreprise.setText(document.get("Entreprise").toString());
                    t_lieu.setText(document.get("Lieu").toString());

                    t_profil.setText(document.get("ProfilDemande").toString());
                    t_statut.setText(document.get("Status").toString());
                    t_titre.setText(document.get("Titre").toString());
                    t_typeContrat.setText(document.get("TypeContrat").toString());
                }
                else{
                    Toast.makeText(ConsultOffer.this, "DOCUMENT NON EXISTANT", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        but_modifyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intModify = new Intent(ConsultOffer.this, createOffer.class);
                    startActivity(intModify);
                    finish();
                } catch(Exception e){
                    Log.v(TAG,e.getMessage());
                }
            }
        });
    }
}

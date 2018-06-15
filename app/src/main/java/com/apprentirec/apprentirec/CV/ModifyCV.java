package com.apprentirec.apprentirec.CV;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import com.apprentirec.apprentirec.R;

public class ModifyCV extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private static final String TAG = "Firebase Candidat";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageButton pdpCand;
    //private CollectionReference db = FirebaseFirestore.getInstance().collection("Candidat");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_cv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.SaveCV);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Floating button pressed");
                Map<String, Object> dataSave = new HashMap<>();

                EditText nomC = (EditText) findViewById(R.id.text_nomCandidat);
                EditText prenomC = (EditText) findViewById(R.id.text_prenomCandidat);
                EditText mailC = (EditText) findViewById(R.id.text_adrMailCandidat);
                String nom = nomC.getText().toString();
                String prenom = prenomC.getText().toString();
                final String mail = mailC.getText().toString();

                if(nom.isEmpty() || prenom.isEmpty() || mail.isEmpty()){return;}

                dataSave.put("Nom", nom);
                dataSave.put("Prenom", prenom);
                db.collection("Candidat")
                        .document(mail).collection("CV").document().update(dataSave)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Document " + mail +" successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });

        pdpCand = (ImageButton) findViewById(R.id.btnImg_ajoutPdpCandidat);

        pdpCand.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                if(chooserIntent == null)
                    return;

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        } );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri targetUri = data.getData();
            Bitmap bitmap;

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                pdpCand.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}

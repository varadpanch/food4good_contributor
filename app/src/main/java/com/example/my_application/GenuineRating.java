package com.example.my_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GenuineRating extends AppCompatActivity {

    RatingBar ratingGenuine;
    Button btnSend;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reqRef = db.collection("Requester");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genuine_rating);

        ratingGenuine = findViewById(R.id.ratingGenuine);
        btnSend = findViewById(R.id.btnSend);

        String requesterId = getIntent().getStringExtra("requesterId");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double rate = ratingGenuine.getRating();

                //update requester's rating
                reqRef.document(requesterId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            Requester r = documentSnapshot.toObject(Requester.class);

                            //calculate rating
                            double new_rating = r.getRating()*r.getTotalOrders()+rate;
                            r.setTotalOrders(r.getTotalOrders()+1);

                            new_rating = new_rating/r.getTotalOrders();
                            r.setRating(new_rating);

                            reqRef.document(r.getPhoneNumber()).set(r).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(GenuineRating.this, "Thanks for rating Requester", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(GenuineRating.this, ContributorOrders.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(GenuineRating.this, "Rating updation failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });

            }
        });
    }
}
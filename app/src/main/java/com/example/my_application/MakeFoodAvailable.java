package com.example.my_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MakeFoodAvailable extends AppCompatActivity {

    TextInputEditText etNonVeg,etVeg;
    ExtendedFloatingActionButton btnMakeAvailable;

    //Firebase database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference afRef = db.collection("AvailabilityFood");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_food_available);

        String id = getIntent().getStringExtra("id");

        etNonVeg = findViewById(R.id.etNonVeg);
        etVeg = findViewById(R.id.etVeg);
        btnMakeAvailable = findViewById(R.id.btnMakeAvailable);

        btnMakeAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strveg = etVeg.getText().toString();
                int veg;
                if("".equals(strveg))
                    veg = 0;
                else
                    veg = Integer.parseInt(strveg);

                String strnonveg = etNonVeg.getText().toString();
                int non_veg;
                if("".equals(strnonveg))
                    non_veg = 0;
                else
                    non_veg = Integer.parseInt(strnonveg);


                if(veg==0 && non_veg==0)
                {
                    Toast.makeText(MakeFoodAvailable.this, "Both of the entries cannot be zero", Toast.LENGTH_SHORT).show();
                    return;
                }

                afRef.whereEqualTo("cid",id)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                AvailableFood af = new AvailableFood();
                                af.setCid(id);
                                if (queryDocumentSnapshots.size() > 0) {

                                    Log.d("MakeFoodAvailable", "documents already exists");

                                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                                    {
                                        af = documentSnapshot.toObject(AvailableFood.class);
                                    }


                                }
                                af.setNonVegMeals(af.getNonVegMeals()+non_veg);
                                af.setVegMeals(af.getVegMeals()+veg);

                                afRef.document(af.getCid()).set(af)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(MakeFoodAvailable.this, "Thanks for helping! Food is available.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MakeFoodAvailable.this,ContributorDashboard.class);
                                                intent.putExtra("id",id);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MakeFoodAvailable.this, "Food availability failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MakeFoodAvailable.this, "Data fetching on higher level is failed!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}



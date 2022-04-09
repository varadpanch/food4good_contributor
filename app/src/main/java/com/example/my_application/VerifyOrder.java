package com.example.my_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VerifyOrder extends AppCompatActivity {

    OtpEditText et_opt;
    Button btnVerify;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference orRef = db.collection("Order");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_order);

        Order order = (Order) getIntent().getSerializableExtra("order_obj");

        et_opt=  findViewById(R.id.et_otp);
        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = et_opt.getText().toString();

                if(order.getOtp().equals(otp))
                {
                    //update order as closed
                    order.setStatus("closed");
                    orRef.document(order.getO_id()).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(VerifyOrder.this, "Request Fullfilled Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerifyOrder.this,GenuineRating.class);
                            intent.putExtra("requesterId",order.getR_id());
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VerifyOrder.this, "Request completion Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(VerifyOrder.this, "Enter Valid Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
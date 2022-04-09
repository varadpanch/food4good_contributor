package com.example.my_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContributorDashboard extends AppCompatActivity {

    Button rewards;
    Button orders;
    Button makeFoodAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_dashboard);

        rewards = findViewById(R.id.bRewards);
        orders = findViewById(R.id.bOrders);
        makeFoodAvailable = findViewById(R.id.bMakeFoodAvailable);

        String cid = getIntent().getStringExtra("id");
        makeFoodAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContributorDashboard.this, AvailableFood.class);
                intent.putExtra("id",cid);
                startActivity(intent);
                finish();
            }
        });

        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
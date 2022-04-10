package com.example.my_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.TreeSet;

public class RewardDetails extends AppCompatActivity {
    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //Firebase database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference ordRef = db.collection("Order");

    int rewardPoints=0;
    int peopleServed=0;
    double moneyEarned = 0.0;

    TextView tvRewardPoints, tvPeopleServed, tvRewardMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_details);

        Log.d("RewardDetails","Inside");

        tvRewardPoints = findViewById(R.id.tvRewardPoints);
        tvPeopleServed = findViewById(R.id.tvPeopleServed);
        tvRewardMoney = findViewById(R.id.tvRewardMoney);

        ArrayList<Order> lOrders = new ArrayList<>();
        TreeSet<String> unique_rid = new TreeSet<>();


        ordRef.whereEqualTo("status","closed").whereEqualTo("c_id",id).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot qds : value){
                    Order o = qds.toObject(Order.class);
                    lOrders.add(o);
                    unique_rid.add(o.r_id);
                }
                Log.d("Rewards", String.valueOf(lOrders.size()));
                rewardPoints = lOrders.size()*100;
                peopleServed = unique_rid.size();
                moneyEarned = rewardPoints/1000.0;
                Toast.makeText(RewardDetails.this, "Reward Points:"+rewardPoints+"\nsPeople Served: "+peopleServed, Toast.LENGTH_SHORT).show();
                tvRewardPoints.setText(""+rewardPoints);
                tvPeopleServed.setText(""+peopleServed);
                tvRewardMoney.setText("Your Reward: $"+Double.toString(moneyEarned));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
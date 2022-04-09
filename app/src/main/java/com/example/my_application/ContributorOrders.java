package com.example.my_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ContributorOrders extends AppCompatActivity {

    RecyclerView rvContributorOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_orders);

        rvContributorOrders = findViewById(R.id.rvContributorOrders);

//        String o_id;
//        String r_id;
//        String c_id;
//        String v_qty;
//        String nv_qty;
//        String otp;
//        String status;
//        String longitude;
//        String latitude;
//        String requesterName;
        Order o1 = new Order("123","456","789",12,7,"1179","open","36.5","-118.20","Habibi");
        Order o2 = new Order("231","456","789",12,7,"1179","open","36.5","-118.20","Habibi");
        Order o3 = new Order("321","456","789",12,7,"1179","open","36.5","-118.20","Habibi");

        ArrayList<Order> alOrders = new ArrayList<>();
        alOrders.add(o1);
        alOrders.add(o2);
        alOrders.add(o3);

        OrdersAdapter ordersAdapter = new OrdersAdapter(alOrders);
        rvContributorOrders.setAdapter(ordersAdapter);
        rvContributorOrders.setLayoutManager(new LinearLayoutManager(this));

    }
}
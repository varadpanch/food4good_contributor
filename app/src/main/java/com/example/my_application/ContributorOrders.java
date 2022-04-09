package com.example.my_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ContributorOrders extends AppCompatActivity {

    RecyclerView rvContributorOrders;
    //Firebase database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference conRef = db.collection("Order");

    ArrayList<Order> alOrders;

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

       alOrders = new ArrayList<>();
//      alOrders.add(o1);
//      alOrders.add(o2);
//       alOrders.add(o3);

//        conRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot qds : queryDocumentSnapshots){
//                    Order o = qds.toObject(Order.class);
//                    alOrders.add(o);
//                    Log.d("Orders",o.getC_id());
//                }
//                Log.d("Orders", "Size of the array:"+String.valueOf(alOrders.size()));
//                OrdersAdapter ordersAdapter = new OrdersAdapter(alOrders);
//                rvContributorOrders.setAdapter(ordersAdapter);
//                rvContributorOrders.setLayoutManager(new LinearLayoutManager(ContributorOrders.this));
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ContributorOrders.this, "Order fetch failed!!", Toast.LENGTH_SHORT).show();
//            }
//        });

        Log.d("Orders","Before rendering:"+alOrders.size());


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Orders","Inside onStart");
        conRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(ContributorOrders.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
                for(QueryDocumentSnapshot qds : value){
                    Order o = qds.toObject(Order.class);
                    if(alOrders.contains(o)){
                        alOrders.remove(o);
                    }
                    alOrders.add(o);
                }
                Log.d("Start", "Inside onStart size"+String.valueOf(alOrders.size()));
                OrdersAdapter ordersAdapter = new OrdersAdapter(alOrders);
               rvContributorOrders.setAdapter(ordersAdapter);
               rvContributorOrders.setLayoutManager(new LinearLayoutManager(ContributorOrders.this));

            }
        });


    }
}
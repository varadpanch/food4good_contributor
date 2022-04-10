package com.example.my_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ContributerDetails extends AppCompatActivity {

    private Button signUp;
    private TextInputEditText  etName, etEmail, etAddress;
    private AutoCompleteTextView spnTypes;
    private FloatingActionButton upload;
    private ImageView imageView;

    LocationManager locationManager;
    String id = "";
    String latitude="";
    String longitude="";
    String type="";
    String name="";
    String address="";
    String email="";
    String imageUrl="";
    int rewards=0;
    private static final int REQUEST_LOCATION = 1;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;


    private Uri filePath;

    //Firebase storage
    FirebaseStorage storage;
    StorageReference storageReference;

    //Firebase database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference conRef = db.collection("Contributor");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributer_details);

        //Init elements
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        spnTypes = findViewById(R.id.spnTypes);
        upload = findViewById(R.id.btnUpload);
        signUp = findViewById(R.id.btnSignUp);
        imageView = findViewById(R.id.imageView);

        //signUp.setVisibility(View.GONE);
        signUp.setEnabled(false);


        //Getting this value from intent
        email = getIntent().getStringExtra("email");
        longitude = getIntent().getStringExtra("longitude");
        latitude = getIntent().getStringExtra("latitude");
        id=getIntent().getStringExtra("id");



        etEmail.setText(email);

        ArrayList<String> conTypes = new ArrayList<>();
        conTypes.add("Individual");
        conTypes.add("NGO");
        conTypes.add("Restaurant");

        ArrayAdapter<String> aaTypes = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,conTypes);
        spnTypes.setAdapter(aaTypes);
        spnTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(ContributerDetails.this, type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //Firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Firestorage url","just clicked button before select");
                selectImage();


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                address = etAddress.getText().toString();

                Log.d("Contributor",name);
                Log.d("Contributor",email);
                Log.d("Contributor",longitude);
                Log.d("Contributor",latitude);
                Log.d("Contributor",address);
                Log.d("Contributor",imageUrl);
                Log.d("Contributor",type);

                if("".equals(name) || "".equals(email) || "".equals(longitude) || "".equals(latitude) || "".equals(address) || "".equals(imageUrl) || "".equals(type)){
                    Toast.makeText(ContributerDetails.this, "Please enter valid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Contributor c = new Contributor(id,name,longitude,latitude,imageUrl, email, type, address, rewards);
                conRef.document(c.getId()).set(c).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ContributerDetails.this, "Your account has been created successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ContributerDetails.this, ContributorDashboard.class);
                        intent.putExtra("id",getIntent().getStringExtra("id"));
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ContributorDetails",e.toString());
                        Toast.makeText(ContributerDetails.this, "Account creation failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        ArrayList<Contributor> contributors = new ArrayList<>();
//        Contributor c1 = new Contributor("122","Chamya","412141","4234141","afafad","rhyr","grhsfa","gaffa");
//        Contributor c2 = new Contributor("123","Chamya2","412123","4234431","afaqwdad","rdasdyr","gradasfa","gghjgffa");
//        Contributor c3 = new Contributor("124","Chamya3","41765741","42414154141","afafafsdgbd","rhyvjtrthr","grfafdashsfa","gafdafsffa");
//
//        contributors.add(c1);
//        contributors.add(c2);
//        contributors.add(c3);
//
//        for(Contributor c: contributors){
//            conRef.document(c.getId()).set(c).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    Toast.makeText(ContributerDetails.this, c.getId(), Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ContributerDetails.this, "Failed!!!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


        conRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Contributor> clist = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Contributor c = documentSnapshot.toObject(Contributor.class);
                    clist.add(c);
                    Log.d("Varad",c.getId());
                }
                Log.d("Varad", String.valueOf(clist.size()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ContributerDetails.this, "Failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("varad","Inside on start");

        conRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                if(error!=null){
                    Toast.makeText(ContributerDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Contributor> clist = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    Contributor c = documentSnapshot.toObject(Contributor.class);
                    clist.add(c);
                    Log.d("VaradOnStart", c.getId());
                }
                Log.d("VaradOnStart", String.valueOf(clist.size()));

            }
        });
    }

    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog


                                    taskSnapshot.getStorage().getDownloadUrl()
                                            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    String fileLink = task.getResult().toString();

                                                    Log.d("Firestorage url",fileLink);
                                                    signUp.setEnabled(true);
                                                    imageUrl = fileLink;
                                                    progressDialog.dismiss();

                                                    Toast
                                                            .makeText(ContributerDetails.this,
                                                                    "Image Uploaded!!",
                                                                    Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            });




                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(ContributerDetails.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

    // Select Image method
    private void selectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);

                Log.d("Firestorage url","just clicked button after select");
                uploadImage();
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}
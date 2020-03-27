package com.pacman.MentAlly.ui.emergencyCall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

import java.util.ArrayList;
import java.util.Map;


public class emergencyCallActivity extends MainActivity {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();;
    private FirebaseUser user;
    //ArrayList<Long> phoneNumber = new ArrayList<>();
    private StorageReference phone;
    private Button btnSms;
    private TextView test;
    //final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_emergency_call, contentFrameLayout);

        btnSms = findViewById(R.id.EmergencyCall);
        test = findViewById(R.id.test);
        user = FirebaseAuth.getInstance().getCurrentUser();
        phone = FirebaseStorage.getInstance().getReference(user.getPhoneNumber());

        //this is the code to get your phone numbers. the function loops through the contacts and retrieves the numbers.
        db.collection("users").document(user.getUid()).collection("contactLog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                Map<String, Object> taskItem = document.getData();
                                String phonenumber = (String)taskItem.get("phoneNumber");

                            }
                        }
                    }
                });


//        database.collection("users").document(user).collection("contactlog")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        for (DocumentSnapshot snapshot : queryDocumentSnapshots){
//                            if (e != null) {
//                                Log.w("Listen failed.", e);
//                                return;
//                            }
//
//                            if (snapshot != null && snapshot.exists()){
//                                phoneNumber.add(snapshot.getLong("phoneNumber"));
//                            }
//                        }
//                    }
//                });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < phoneNumber.size(); i++) {
//                    test.setText(phoneNumber.get(i).toString());
//                    call(phoneNumber.get(i).toString());
//
//                }
//                test.setText(phone.toString());
//                call(phone.toString());
                call("+14169480667");

            }
        });

//        btnSms.setEnabled(false);
//        if (checkPermission(Manifest.permission.SEND_SMS)){
//            btnSms.setEnabled(true);
//        } else {
//            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
//        }
//
//        btnSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkPermission(Manifest.permission.SEND_SMS)) {
//                    SmsManager smgr = SmsManager.getDefault();
//                    for (int i = 0; i < phoneNumber.size(); i++){
//                        System.out.println(phoneNumber.get(i));
//                        smgr.sendTextMessage(phoneNumber.get(i).toString(), null, "hi", null, null);
//                    }
//
//                    Toast.makeText(emergencyCallActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(emergencyCallActivity.this, "SMS Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//
//
//    }
//
//    public boolean checkPermission(String permission){
//        int check = ContextCompat.checkSelfPermission(this,permission);
//        return (check == PackageManager.PERMISSION_GRANTED);
//    }
}

    public void call(String phone) {
        if (ContextCompat.checkSelfPermission(emergencyCallActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(emergencyCallActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }

        else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(phone));
            startActivity(callIntent);
        }
    }
}
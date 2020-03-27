package com.pacman.MentAlly.ui.emergencyCall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

import java.util.ArrayList;
import java.util.Map;


public class emergencyCallActivity extends MainActivity {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();;
    private FirebaseUser user;
    ArrayList<String> phoneNumber = new ArrayList<>();
    String phoneNum;
    private Button btnSms;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_emergency_call, contentFrameLayout);

        btnSms = findViewById(R.id.EmergencyCall);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //this is the code to get your phone numbers. the function loops through the contacts and retrieves the numbers.
        db.collection("users").document(user.getUid()).collection("contactLog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                Map<String, Object> taskItem = document.getData();
                                String phonenumber = taskItem.get("phoneNumber").toString();
                                phoneNumber.add(phonenumber);

                            }
                        }
                    }
                });

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < phoneNumber.size(); i++){
                    sendSMSMessage("tel:" + phoneNumber.get(i));
                    call("tel:" + phoneNumber.get(i));
                }
            }
        });
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

    public void sendSMSMessage(String phone){

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
            }

        else {
            Intent sendMessage = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + phone));
            sendMessage.putExtra("sms_body","Hi");
            Intent shareMessage = Intent.createChooser(sendMessage,null);
            startActivity(shareMessage);
            Toast.makeText(getApplicationContext(),"SMS sent successfully",Toast.LENGTH_LONG).show();
        }
    }
}
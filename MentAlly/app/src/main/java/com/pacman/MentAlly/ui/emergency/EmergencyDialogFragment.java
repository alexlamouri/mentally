package com.pacman.MentAlly.ui.emergency;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

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

public class EmergencyDialogFragment extends DialogFragment {

    private static final int SELECTED_PIC = 1;
    private static final int REQUEST_CALL = 1;
    protected DrawerLayout draw;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();;
    private FirebaseUser user;
    ArrayList<String> phoneNumber = new ArrayList<>();
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 0;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.emergency_title)
                .setMessage(R.string.emergency_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        user = FirebaseAuth.getInstance().getCurrentUser();
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

                        for (int i = 0; i < phoneNumber.size(); i++){
                            text("tel:" + phoneNumber.get(i));
                        }

                        call("tel://911");
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    public void call(String phone) {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }

        else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse(phone));
            startActivity(callIntent);
        }
    }

    public void text(String phone) {

        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        else {
            Intent sendMessage = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + phone));
            sendMessage.putExtra("sms_body","I am currently experiencing an emergency and have notified emergency services. This message was sent by the Mentally app");
            Intent shareMessage = Intent.createChooser(sendMessage,null);
            startActivity(shareMessage);
            Toast.makeText(getActivity().getApplicationContext(),"SMS sent successfully",Toast.LENGTH_LONG).show();
        }
    }
}

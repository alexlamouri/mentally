package com.pacman.MentAlly.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;
import androidx.appcompat.app.AlertDialog;

public class ProfileActivity extends MainActivity {
    private Button edit_btn;

    private TextView first_name_txt;
    private TextView country_txt;
    private TextView dob_txt;
    private FirebaseUser user;

    public FirebaseFirestore myDatabase;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_profile, contentFrameLayout);

        first_name_txt = findViewById(R.id.firstname_textview);
        first_name_txt.getBackground().setAlpha(75);
        country_txt = findViewById(R.id.country_textview);
        country_txt.getBackground().setAlpha(75);
        dob_txt = findViewById(R.id.dob_textview);
        dob_txt.getBackground().setAlpha(75);

        edit_btn = findViewById(R.id.editButton);

        myDatabase = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        this.updateLabels();


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editProfileDialogView = getLayoutInflater().inflate(R.layout.edit_profile_dialog, null);
                final EditText name = editProfileDialogView.findViewById(R.id.name);
                final EditText dob = editProfileDialogView.findViewById(R.id.dob);
                final EditText country = editProfileDialogView.findViewById(R.id.country);
                final String uid = user.getUid();

                AlertDialog dialog = new AlertDialog.Builder(ProfileActivity.this)
                        .setView(editProfileDialogView)
                        .setPositiveButton(null, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!name.getText().toString().isEmpty()) {
                                    myDatabase.collection("users").document(uid).update("Name", name.getText().toString());
                                }

                                if (!dob.getText().toString().isEmpty()) {
                                    myDatabase.collection("users").document(uid).update("DOB", dob.getText().toString());
                                }

                                if (!country.getText().toString().isEmpty()) {
                                    myDatabase.collection("users").document(uid).update("Country", country.getText().toString());
                                }
                                updateLabels();
                            }
                        })
                        .setPositiveButtonIcon(AppCompatResources.getDrawable(ProfileActivity.this, R.drawable.complete_task))
                        .create();
                dialog.show();


            }
        });
    }

    public void updateLabels() {
        if (user != null) {
            String uid = user.getUid();
            myDatabase.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot docsnap = task.getResult();
                        String name = docsnap.getString("Name");
                        String country = docsnap.getString("Country");
                        String dob = docsnap.getString("DOB");

                        first_name_txt.setText(name);
                        country_txt.setText(country);
                        dob_txt.setText(dob);
                    }
                }
            });
        }
    }

}

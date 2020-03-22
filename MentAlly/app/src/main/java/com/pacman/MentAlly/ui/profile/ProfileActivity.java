package com.pacman.MentAlly.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ProfileActivity extends MainActivity {
    private Button profile_btn;
    private ImageButton first_name_btn;
    private ImageButton dob_btn;
    private ImageButton country_btn;

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

//        first_name_btn = root.findViewById(R.id.edit_first_name);
//        first_name_btn.getBackground().setAlpha(65);
//        dob_btn = root.findViewById(R.id.edit_dob);
//        dob_btn.getBackground().setAlpha(65);
//        country_btn = root.findViewById(R.id.edit_country);
//        country_btn.getBackground().setAlpha(65);

        //set all textview transparency level
        first_name_txt = findViewById(R.id.firstname_textview);
        first_name_txt.getBackground().setAlpha(75);
        country_txt = findViewById(R.id.country_textview);
        country_txt.getBackground().setAlpha(75);
        dob_txt = findViewById(R.id.dob_textview);
        dob_txt.getBackground().setAlpha(75);

        myDatabase = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
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

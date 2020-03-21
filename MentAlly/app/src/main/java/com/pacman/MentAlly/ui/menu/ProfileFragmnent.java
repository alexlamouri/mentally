package com.pacman.MentAlly.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pacman.MentAlly.R;

public class ProfileFragmnent extends Fragment {
    private Button profile_btn;
    private ImageButton first_name_btn;
    private ImageButton last_name_btn;
    private ImageButton email_btn;
    private ImageButton dob_btn;
    private ImageButton country_btn;
    private ImageButton password_btn;

    private TextView first_name_txt;
    private TextView last_name_txt;
    private TextView email_txt;
    private TextView country_txt;
    private TextView dob_txt;
    private TextView password_txt;
    private FirebaseUser user;

    public FirebaseFirestore myDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_profile, container, false);


//        first_name_btn = root.findViewById(R.id.edit_first_name);
//        first_name_btn.getBackground().setAlpha(65);
//        dob_btn = root.findViewById(R.id.edit_dob);
//        dob_btn.getBackground().setAlpha(65);
//        country_btn = root.findViewById(R.id.edit_country);
//        country_btn.getBackground().setAlpha(65);

        //set all textview transparency level
        first_name_txt = root.findViewById(R.id.firstname_textview);
        first_name_txt.getBackground().setAlpha(75);
        country_txt = root.findViewById(R.id.country_textview);
        country_txt.getBackground().setAlpha(75);
        dob_txt = root.findViewById(R.id.dob_textview);
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
        return root;
    }
}

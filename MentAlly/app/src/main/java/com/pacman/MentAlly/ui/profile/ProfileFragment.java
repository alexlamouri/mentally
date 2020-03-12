package com.pacman.MentAlly.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pacman.MentAlly.R;

public class ProfileFragment extends Fragment {
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
        View root = inflater.inflate(R.layout.activity_profile, container, false);

        //set all button transparency levels
        profile_btn = root.findViewById(R.id.change_profile_pic);
        profile_btn.getBackground().setAlpha(65);
        first_name_btn = root.findViewById(R.id.edit_first_name);
        first_name_btn.getBackground().setAlpha(65);
        last_name_btn = root.findViewById(R.id.edit_last_name);
        last_name_btn.getBackground().setAlpha(65);
        email_btn = root.findViewById(R.id.edit_email);
        email_btn.getBackground().setAlpha(65);
        dob_btn = root.findViewById(R.id.edit_dob);
        dob_btn.getBackground().setAlpha(65);
        country_btn = root.findViewById(R.id.edit_country);
        country_btn.getBackground().setAlpha(65);
        password_btn = root.findViewById(R.id.edit_password);
        password_btn.getBackground().setAlpha(65);

        //set all textview transparency level
        first_name_txt = root.findViewById(R.id.firstname_textview);
        first_name_txt.getBackground().setAlpha(75);
        last_name_txt = root.findViewById(R.id.lastname_textview);
        last_name_txt.getBackground().setAlpha(75);
        email_txt = root.findViewById(R.id.email_textview);
        email_txt.getBackground().setAlpha(75);
        country_txt = root.findViewById(R.id.country_textview);
        country_txt.getBackground().setAlpha(75);
        dob_txt = root.findViewById(R.id.dob_textview);
        dob_txt.getBackground().setAlpha(75);
        password_txt = root.findViewById(R.id.password_textview);
        password_txt.getBackground().setAlpha(75);

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
                        //String email = docsnap.getString("email");

                        first_name_txt.setText(name);
                        //email_txt.setText(email);
                        country_txt.setText(country);
                        dob_txt.setText(dob);
                    }
                }
            });
        }

        return root;
    }

}

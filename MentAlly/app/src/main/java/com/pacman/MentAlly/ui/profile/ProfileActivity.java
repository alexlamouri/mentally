package com.pacman.MentAlly.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;
import androidx.appcompat.app.AlertDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.util.Calendar;

import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.DatabaseReference;
import com.pacman.MentAlly.ui.login.LoginActivity;

public class ProfileActivity extends MainActivity {
    private Button edit_btn;

    private TextView first_name_txt;
    private TextView country_txt;
    private TextView dob_txt;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private FirebaseUser user;
    private TextView gender_txt;
    private ImageView profilepic;
    private Button logout;
    Uri imageUri;

    public FirebaseFirestore myDatabase;
    private static final int PICK_IMAGE = 1;
    private StorageReference mStorageRef;
    private StorageTask<UploadTask.TaskSnapshot> mUploadTask;


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
        gender_txt = findViewById(R.id.gender_textview);
        gender_txt.getBackground().setAlpha(75);
        edit_btn = findViewById(R.id.editButton);
        profilepic= findViewById(R.id.imagetoupload);
        logout = findViewById(R.id.logout);

        myDatabase = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference(user.getUid());

        this.updateLabels();
        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(profilepic);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder b = new AlertDialog.Builder(ProfileActivity.this);
                b.setMessage("Are you sure you want to sign out?");
                b.setCancelable(true);
                b.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);

                    }
                });
                b.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog a = b.create();
                a.show();
            }
        });

        profilepic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View editProfileDialogView = getLayoutInflater().inflate(R.layout.edit_profile_dialog, null);
                final EditText name = editProfileDialogView.findViewById(R.id.name);
                final EditText dob = editProfileDialogView.findViewById(R.id.dob);
                final EditText country = editProfileDialogView.findViewById(R.id.country);
               // final EditText gender = editProfileDialogView.findViewById(R.id.genderlabel);
                final String uid = user.getUid();

                dob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog d = new DatePickerDialog(editProfileDialogView.getContext(),
                                dateSetListener,
                                year, month, day);
                        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        d.show();

                    }
                });

                dateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        dob.setText(dayOfMonth + "/" + month + "/" + year);
                        Log.d("task added", "date:" + dayOfMonth + "/" + month + "/" + year);
                    }
                };

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
                    //            if (!gender.getText().toString().isEmpty()) {
                     //               myDatabase.collection("users").document(uid).update("Gender", gender.getText().toString());
                       //         }

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
                        String gender = docsnap.getString("Gender");

                        first_name_txt.setText(name);
                        country_txt.setText(country);
                        dob_txt.setText(dob);
                        gender_txt.setText(gender);
                    }
                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilepic.setImageBitmap(bitmap);
                uploadFile(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                        Toast.makeText(ProfileActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrl(uri);
                    }
                });
    }
    private void setUserProfileUrl(final Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }





}

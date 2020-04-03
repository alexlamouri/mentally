package com.pacman.MentAlly.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.pacman.MentAlly.R;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pacman.MentAlly.ui.home.HomeActivity;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterViewModel registerViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText nameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText DOBEditText;
    private EditText countryEditText;
    private Spinner gender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory()).get(RegisterViewModel.class);

        //initialize cloud firestore database and authentication
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Setup dropdown list for gender
        gender = findViewById(R.id.gender);
        String[] genderList = new String[]{"Male", "Female", "Other", "Prefer not to specify"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList);
        gender.setAdapter(adapter);

        // Get access to all user input components on UI
        nameEditText = findViewById(R.id.name);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        DOBEditText = findViewById(R.id.DOB);
        countryEditText = findViewById(R.id.country);
        final Button registerButton = findViewById(R.id.register);

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());

                if (registerFormState.getNameError() != null) {
                    nameEditText.setError(getString(registerFormState.getNameError()));
                }
                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getDOBError() != null) {
                    DOBEditText.setError(getString(registerFormState.getDOBError()));
                }
                if (registerFormState.getCountryError() != null) {
                    countryEditText.setError(getString(registerFormState.getCountryError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), nameEditText.getText().toString(),
                        DOBEditText.getText().toString(), gender.getSelectedItem().toString(),
                        countryEditText.getText().toString());
            }
        };

        // Setup listeners for input field changes
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        DOBEditText.addTextChangedListener(afterTextChangedListener);
        registerButton.setOnClickListener(this);

        gender.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                registerViewModel.registerDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), nameEditText.getText().toString(),
                        DOBEditText.getText().toString(), gender.getSelectedItem().toString(),
                        countryEditText.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                registerViewModel.registerDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), nameEditText.getText().toString(),
                        DOBEditText.getText().toString(), gender.getSelectedItem().toString(),
                        countryEditText.getText().toString());
            }

        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.register) {
            createAccount(usernameEditText.getText().toString(), passwordEditText.getText().toString());
        }
    }

    public void addUserData(String UID, String name, String dob, String country, String gender) {
        Map<String,String> user = new HashMap<>();
        user.put("Name", name);
        user.put("DOB", dob);
        user.put("Country", country);
        user.put("Gender", gender);

        db.collection("users").document(UID).set(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.w("SUCCESS","DocumentSnapshot added with ID:");
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Registration Failed", "Error adding document", e);
            }
        });
    }

    public void createAccount(String email, String password) {
        // START create_user_with_email
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    addUserData(user.getUid(), nameEditText.getText().toString(), DOBEditText.getText().toString(),
                            countryEditText.getText().toString(), gender.getSelectedItem().toString());
                    updateUIWithUser(user);
                } else {
                    updateUIWithUser(null);
                }
            }
        });
        // END create_user_with_email
    }

    private void updateUIWithUser(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(RegisterActivity.this,"Registration Failed", Toast.LENGTH_SHORT).show();
            return;
        }
        String welcome = "Welcome " + user.getEmail() + "!";
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

}


package com.pacman.MentAlly.ui.register;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.pacman.MentAlly.R;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.tags.Tag;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterViewModel registerViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText nameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText DOBEditText;
    private EditText countryEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory()).get(RegisterViewModel.class);

        //initialize cloud firestore database and authentication
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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
                        DOBEditText.getText().toString(), countryEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        registerButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUIWithUser(currentUser);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.register) {
            createAccount(usernameEditText.getText().toString(), passwordEditText.getText().toString());
        }
        if (mAuth.getCurrentUser() != null) {
            addUserData(mAuth.getCurrentUser().getUid(), nameEditText.getText().toString(), DOBEditText.getText().toString(), countryEditText.getText().toString());
        }
    }

    public void addUserData(String UID, String name, String dob, String country) {
        Map<String,String> user = new HashMap<>();
        user.put("Name", name);
        user.put("DOB", dob);
        user.put("Country", country);

        db.collection("users").document(UID).set(user);
    }

    public void createAccount(String email, String password) {
        // START create_user_with_email
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
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
        String welcome = getString(R.string.welcome) + user.getEmail();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

}


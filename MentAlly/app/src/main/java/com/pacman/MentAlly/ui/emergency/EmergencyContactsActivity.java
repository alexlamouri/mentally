package com.pacman.MentAlly.ui.emergency;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EmergencyContactsActivity extends MainActivity {

    private static final String TAG = "EmergencyContactsActivity";

    private Button addButton;
    private Button deleteListButton;
    private ListView myListView;
    private EmergencyContactsActivity.MyListAdapter mylistadapter;

    private FirebaseFirestore db;
    private String uid;

    private final List<EmergencyContact> contactList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_emergency_contacts, contentFrameLayout);

        this.addButton = findViewById(R.id.addButton);
        this.deleteListButton = findViewById(R.id.delete_list_btn);

        this.db = FirebaseFirestore.getInstance();
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        this.mylistadapter = new EmergencyContactsActivity.MyListAdapter();

        this.getAllContactsFromDB();

        this.myListView = findViewById(R.id.myList);
        this.myListView.setAdapter(this.mylistadapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                final View infoTaskDialogView = getLayoutInflater().inflate(R.layout.info_contact_dialog, null);
                final TextView nameLabel = infoTaskDialogView.findViewById(R.id.nameLabel);
                final TextView phoneNumberLabel = infoTaskDialogView.findViewById(R.id.phoneNumberLabel);
                final TextView emailLabel = infoTaskDialogView.findViewById(R.id.emailLabel);

                final EmergencyContact contact = contactList.get(pos);
                nameLabel.setText(contact.getContactName());
                phoneNumberLabel.setText(String.valueOf(contact.getPhoneNumber()));
                emailLabel.setText(contact.getEmail());

                AlertDialog infoDialog = new AlertDialog.Builder(EmergencyContactsActivity.this)
                        .setView(infoTaskDialogView)
                        .setNegativeButton("Delete Contact", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeContactFromDb(contact.getContactId());
                                contactList.remove(contact);
                                mylistadapter.setData(contactList);
                            }
                        })
                        .create();
                infoDialog.show();

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View addTaskDialogView = getLayoutInflater().inflate(R.layout.add_new_contact_dialog, null);
                final EditText contactName = addTaskDialogView.findViewById(R.id.contact_name);
                final EditText phoneNumber = addTaskDialogView.findViewById(R.id.phone_number);
                final EditText email = addTaskDialogView.findViewById(R.id.email);

                AlertDialog dialog = new AlertDialog.Builder(EmergencyContactsActivity.this)
                        .setView(addTaskDialogView)
                        .setPositiveButton(null, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (contactName.getText().toString().isEmpty()) {
                                    Toast.makeText(EmergencyContactsActivity.this, "ERROR: Please specify contact name", Toast.LENGTH_SHORT).show();
                                }
                                if (phoneNumber.getText().toString().isEmpty() || (! isPhoneNumberValid(phoneNumber.getText().toString()))) {
                                    Toast.makeText(EmergencyContactsActivity.this, "ERROR: Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                                }
                                if (email.getText().toString().isEmpty() || (! isEmailValid(email.getText().toString()))) {
                                    Toast.makeText(EmergencyContactsActivity.this, "ERROR: Please enter a valid email", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(EmergencyContactsActivity.this, "Successfully Added emergency contact", Toast.LENGTH_SHORT).show();
                                    EmergencyContact newContact;
                                    try {
                                        newContact = new EmergencyContact(contactName.getText().toString(), Long.parseLong(phoneNumber.getText().toString()), email.getText().toString());
                                        contactList.add(newContact);
                                        //add to database
                                        addContactToDatabase(newContact.getContactId(), contactName.getText().toString(), Long.parseLong(phoneNumber.getText().toString()), email.getText().toString());
                                        mylistadapter.setData(contactList);
                                    } catch (NumberFormatException e) {
                                        Toast.makeText(EmergencyContactsActivity.this, "ERROR: "+"Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        })
                        .setPositiveButtonIcon(AppCompatResources.getDrawable(EmergencyContactsActivity.this, R.drawable.complete_task))
                        .create();
                dialog.show();
            }
        });

        deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (EmergencyContact c : contactList) {
                    removeContactFromDb(c.getContactId());
                }
                contactList.clear();
                mylistadapter.setData(contactList);
            }
        });
    }

    class MyListAdapter extends BaseAdapter {
        List<EmergencyContact> contactList = new ArrayList<>();

        public void setData(List<EmergencyContact> mList) {
            contactList.clear();
            contactList.addAll(mList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public Object getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflateLayout = (LayoutInflater) EmergencyContactsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View contactRow = inflateLayout.inflate(R.layout.contact, parent, false);
            TextView taskObject = contactRow.findViewById(R.id.contactItem);
            taskObject.setText(contactList.get(position).getContactName());
            return contactRow;
        }
    }

    public void addContactToDatabase(String contactId, String contactName, long phoneNumber, String email) {
        Map<String, Object> newContactForUser = new HashMap<>();

        newContactForUser.put("name", contactName);
        newContactForUser.put("phoneNumber", phoneNumber);
        newContactForUser.put("email", email);

        db.collection("users").document(uid).collection("contactLog").document(contactId).set(newContactForUser);
    }

    public void getAllContactsFromDB() {

        db.collection("users").document(uid).collection("contactLog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> contact) {
                        if (contact.isSuccessful()) {
                            for (QueryDocumentSnapshot document : contact.getResult()) {
                                Map<String, Object> contactItem = document.getData();
                                String contactName = (String) contactItem.get("name");
                                long phoneNumber = Long.parseLong(String.valueOf(contactItem.get("phoneNumber")));
                                String email = (String)  contactItem.get("email");

                                EmergencyContact c = new EmergencyContact();
                                c.setContactName(contactName);
                                c.setPhoneNumber(phoneNumber);
                                c.setEmail(email);
                                c.setContactId(document.getId());

                                contactList.add(c);
                            }
                            mylistadapter.setData(contactList);
                        }
                    }
                });
    }


    public void removeContactFromDb(String contactID) {
        db.collection("users").document(this.uid).collection("contactLog").document(contactID).delete();
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("^(?=(?:[8-9]){1}(?=[0-9]{8}).*)");
    }
}

package com.pacman.MentAlly.ui.home;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.Mood.MoodActivity;
import com.pacman.MentAlly.ui.ToDoList.ToDoListActivity;
import com.pacman.MentAlly.ui.breathing.BreathingActivity;
import com.pacman.MentAlly.ui.emergency.EmergencyContactsActivity;
import com.pacman.MentAlly.ui.habit.HabitTrackerActivity;
import com.pacman.MentAlly.ui.profile.ProfileActivity;
import com.pacman.MentAlly.ui.quiz.QuizActivity;
import com.pacman.MentAlly.ui.menu.WallpaperFragment;
import com.pacman.MentAlly.ui.helpline.helplineActivity;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SELECTED_PIC = 1;
    private static final int REQUEST_CALL = 1;
    protected DrawerLayout draw;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();;
    private FirebaseUser user;
    ArrayList<String> phoneNumber = new ArrayList<>();
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 0;

    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        draw = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        //implement navigation bar icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draw, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.nav_home:
                Intent i_home = new Intent(this, HomeActivity.class);
                startActivity(i_home);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_profile:
                Log.d("Hi", "hello");
                Intent i_profile = new Intent(this, ProfileActivity.class);
                startActivity(i_profile);
                draw.closeDrawer(GravityCompat.START);
                Log.d("Hi", "hello");
                break;

            case R.id.nav_habit:
                Intent i_habit = new Intent(this, HabitTrackerActivity.class);
                startActivity(i_habit);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_todo:
                Intent i_todo = new Intent(this, ToDoListActivity.class);
                startActivity(i_todo);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_mood:
                Intent i_mood = new Intent(this, MoodActivity.class);
                startActivity(i_mood);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_breathe:
                Intent i_breathe = new Intent(this, BreathingActivity.class);
                startActivity(i_breathe);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_quiz:
                Intent i_quiz = new Intent(this, QuizActivity.class);
                startActivity(i_quiz);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_wallpaper:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new WallpaperFragment()).commit();
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_contacts:
                Intent i_contacts = new Intent(this, EmergencyContactsActivity.class);
                startActivity(i_contacts);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_helpline:
                Intent i_help = new Intent(this, helplineActivity .class);
                startActivity(i_help);
                draw.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_emergency:

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                }
                else {
                    for (int i = 0; i < phoneNumber.size(); i++){
                        sendSMSMessage("tel:" + phoneNumber.get(i));
                    }
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel://911"));
                    startActivity(callIntent);
                }

                draw.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int result, Intent data){
        super.onActivityResult(requestCode,result,data);
        switch(requestCode){
            case SELECTED_PIC:
                if(requestCode == RESULT_OK){
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null );
                    cursor.moveToFirst();
                    int columnindex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnindex);
                    cursor.close();

                    Bitmap b = BitmapFactory.decodeFile(filepath);
                    Drawable d = new BitmapDrawable(b);
                    imageView.setBackground(d);
                }
        }
    }
    @Override
    public void onBackPressed() {
        //close navigation bar before closing app
        if(draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    public void sendSMSMessage(String phone){

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        else {
            Intent sendMessage = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + phone));
            sendMessage.putExtra("sms_body","I am currently experiencing an emergency and have notified emergency services. This message was sent by the Mentally app");
            Intent shareMessage = Intent.createChooser(sendMessage,null);
            startActivity(shareMessage);
            Toast.makeText(getApplicationContext(),"SMS sent successfully",Toast.LENGTH_LONG).show();
        }
    }


}

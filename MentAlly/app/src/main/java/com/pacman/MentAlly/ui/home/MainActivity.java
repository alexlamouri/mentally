package com.pacman.MentAlly.ui.home;

import android.content.Intent;

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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.navigation.NavigationView;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.Mood.MoodActivity;
import com.pacman.MentAlly.ui.ToDoList.ToDoListActivity;
import com.pacman.MentAlly.ui.ambient.AmbientActivity;
import com.pacman.MentAlly.ui.breathing.BreathingActivity;
import com.pacman.MentAlly.ui.emergency.EmergencyContactsActivity;
import com.pacman.MentAlly.ui.emergency.EmergencyDialogFragment;
import com.pacman.MentAlly.ui.habit.HabitTrackerActivity;
import com.pacman.MentAlly.ui.profile.ProfileActivity;
import com.pacman.MentAlly.ui.quiz.QuizActivity;
import com.pacman.MentAlly.ui.menu.WallpaperFragment;
import com.pacman.MentAlly.ui.helpline.helplineActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int SELECTED_PIC = 1;
    protected DrawerLayout draw;

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

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // 🛠️ FIX: Replaced switch statement with if/else if chain 
        // to resolve 'constant expression required' error in modern JDKs.
        int itemId = item.getItemId(); 

        if (itemId == R.id.nav_home) {
            Intent i_home = new Intent(this, HomeActivity.class);
            startActivity(i_home);
        } else if (itemId == R.id.nav_profile) {
            Log.d("Hi", "hello");
            Intent i_profile = new Intent(this, ProfileActivity.class);
            startActivity(i_profile);
            Log.d("Hi", "hello");
        } else if (itemId == R.id.nav_habit) {
            Intent i_habit = new Intent(this, HabitTrackerActivity.class);
            startActivity(i_habit);
        } else if (itemId == R.id.nav_todo) {
            Intent i_todo = new Intent(this, ToDoListActivity.class);
            startActivity(i_todo);
        } else if (itemId == R.id.nav_mood) {
            Intent i_mood = new Intent(this, MoodActivity.class);
            startActivity(i_mood);
        } else if (itemId == R.id.nav_breathe) {
            Intent i_breathe = new Intent(this, BreathingActivity.class);
            startActivity(i_breathe);
        } else if (itemId == R.id.nav_quiz) {
            Intent i_quiz = new Intent(this, QuizActivity.class);
            startActivity(i_quiz);
        } else if (itemId == R.id.nav_ambient) {
            Intent i_ambient = new Intent(this, AmbientActivity.class);
            startActivity(i_ambient);
        } else if (itemId == R.id.nav_wallpaper) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new WallpaperFragment()).commit();
        } else if (itemId == R.id.nav_contacts) {
            Intent i_contacts = new Intent(this, EmergencyContactsActivity.class);
            startActivity(i_contacts);
        } else if (itemId == R.id.nav_helpline) {
            Intent i_help = new Intent(this, helplineActivity.class);
            startActivity(i_help);
        } else if (itemId == R.id.nav_emergency) {
            DialogFragment emergencyDialog = new EmergencyDialogFragment();
            emergencyDialog.show(getSupportFragmentManager(), "emergency");
        }
        
        // This closes the drawer for ALL selections (original logic)
        draw.closeDrawer(GravityCompat.START);
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
}

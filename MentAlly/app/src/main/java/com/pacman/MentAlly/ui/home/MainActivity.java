package com.pacman.MentAlly.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.Mood.MoodActivity;
import com.pacman.MentAlly.ui.ToDoList.ToDoListActivity;
import com.pacman.MentAlly.ui.breathing.BreathingActivity;
import com.pacman.MentAlly.ui.habit.HabitTrackerActivity;
import com.pacman.MentAlly.ui.profile.ProfileActivity;
import com.pacman.MentAlly.ui.profile.ProfileFragment;
import com.pacman.MentAlly.ui.quiz.QuizActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout draw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

/*        //start app on home fragment if app is not already running
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();
            //show 'Home' as selected in menu
            navView.setCheckedItem(R.id.nav_home);
        }*/
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
                Intent i_profile = new Intent(this, ProfileActivity.class);
                startActivity(i_profile);
                draw.closeDrawer(GravityCompat.START);
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
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //close navigation bar before closing app
        if(draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}

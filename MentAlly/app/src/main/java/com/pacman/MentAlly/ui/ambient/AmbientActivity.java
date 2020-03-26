package com.pacman.MentAlly.ui.ambient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import com.pacman.MentAlly.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class AmbientActivity extends AppCompatActivity implements Observer {

    private static final String TAG = "Ambient Activity";
    private ArrayList<AudioFile> audioList = new ArrayList<>();
    private MediaPlaybackService audioService;
    private boolean serviceBound = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlaybackService.LocalBinder binder = (MediaPlaybackService.LocalBinder) service;
            audioService = binder.getService();
            serviceBound = true;
            Toast.makeText(AmbientActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient);
        loadAudio();
    }

    private void loadAudio() {
        audioList.add(new AudioFile("moonlight_sonata", "Moonlight Sonata", "5:21"));
        audioList.add(new AudioFile("bird_ambience", "Bird Song", "1:55"));
        audioList.add(new AudioFile("busy_city_street", "City Streets", "2:19"));
        audioList.add(new AudioFile("campfire", "Campfire", "1:54"));
        audioList.add(new AudioFile("car_interior", "Car Interior", "1:48"));
        audioList.add(new AudioFile("coffee_shop", "Coffee Shop", "1:49"));
        audioList.add(new AudioFile("electric_hum", "Electric Hum", "1:07"));
        audioList.add(new AudioFile("forest1", "Forest1", "2:40"));
        audioList.add(new AudioFile("forest2", "Forest2", "2:21"));
        audioList.add(new AudioFile("helicopter", "Helicopter", "2:03"));
        audioList.add(new AudioFile("ocean_waves", "Ocean Waves", "2:30"));
        audioList.add(new AudioFile("plane", "Plane", "2:06"));
        audioList.add(new AudioFile("rumble", "Rumble", "00:32"));
        audioList.add(new AudioFile("street_traffic", "Street Traffic", "2:00"));
        audioList.add(new AudioFile("thunderstorm", "Thunderstorm", "2:44"));
        audioList.add(new AudioFile("windy_desert", "Windy Desert", "2:00"));


        for (int i=0; i<audioList.size(); i++) {
            audioList.get(i).addObserver(this);
            Log.d(TAG, "loadAudio: "+audioList.get(i).getTitle());
        }
        initRecyclerView();
//        ContentResolver contentResolver = getContentResolver();
//        Uri uri = new Uri.Builder()
//                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
//                .authority(getPackageName())
//                .path("/raw")
//                .build();
//        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
//        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
//        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);
//
//        if (cursor != null && cursor.getCount() > 0) {
//            audioList = new ArrayList<>();
//            while (cursor.moveToNext()) {
//                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
//                audioList.add(new AudioFile(data, title, duration));
//            }
//            cursor.close();
//        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "Initialize recycler view");
        RecyclerView audioListView = findViewById(R.id.audioList);
        AudioAdapter adapter = new AudioAdapter(this, audioList);
        audioListView.setAdapter(adapter);
        audioListView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    private void playAudio(String media) {
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MediaPlaybackService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            // Service is active
            // Send media with BroadcastReciever
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            audioService.stopSelf();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String audioFile = arg.toString();
        playAudio(audioFile);
    }
}

package com.pacman.MentAlly.ui.ambient;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContentResolverCompat;
import androidx.media.MediaBrowserServiceCompat;

import com.pacman.MentAlly.R;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class MediaPlaybackService extends MediaBrowserServiceCompat implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener {

    private static final String TAG = "MediaPlaybackService";
    private final IBinder iBinder = new LocalBinder();
    private String audioFile;
    private MediaPlayer player = null;
    private AudioManager audioManager;
    private int resumePosition;
    private AudioFocusRequest focusRequest;
    private AudioAttributes playbackAttributes;

    // Handle incoming phone calls
    private boolean ongoingCall = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;

    // Handle broadcasted intents
    private BroadcastReceiver playNewAudio  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get new audio file to play from intent
            audioFile = intent.getExtras().getString("media");

            // A PlAY_NEW_AUDIO action received
            // reset media player to play new audio
            stopMedia();
            player.reset();
            initMediaPlayer();
        }
    };

    // Handle broadcasted intents
    private BroadcastReceiver pauseAudio  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // A PAUSE_AUDIO action received
            // pause media player and save spot
            pauseMedia();
        }
    };

    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // pause audio on ACTION_AUDIO_BECOMING_NOISY
            pauseMedia();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //Build attributes and focus request
        playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setOnAudioFocusChangeListener(this)
                .build();

        // Manage incoming phone calls during playback
        callStateListener();
        // ACTION_AUDIO_BECOMING_NOISY -- change in audio outputs -- BroadcastReceiver
        registerBecomingNoisyReceiver();
        // Listen for new audio to play -- BroadcastReceiver
        register_playNewAudio();
        // Listen for new audio to play -- BroadcastReceiver
        register_pauseAudio();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            stopMedia();
            player.release();
        }
        if(audioManager != null) {
            removeAudioFocus();
        }
        // Disable phone state listener
        if (phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        // unregister broadcast recievers
        unregisterReceiver(becomingNoisyReceiver);
        unregisterReceiver(playNewAudio);
        unregisterReceiver(pauseAudio);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            audioFile = intent.getExtras().getString("media");
        } catch (NullPointerException e) {
            stopSelf();
        }
        if (requestAudioFocus() == false) {
            stopSelf();
        }
        if (audioFile != null && audioFile != "") {
            initMediaPlayer();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initMediaPlayer() {
        player = new MediaPlayer();
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.reset();

        player.setAudioAttributes(playbackAttributes);
        try {
            Uri u = new Uri.Builder()
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(getPackageName())
                    .path("/raw/"+audioFile)
                    .build();
            player.setDataSource(getApplicationContext(), u);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        player.prepareAsync();
    }

    private void registerBecomingNoisyReceiver() {
        // register after getting audio focus
        IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(becomingNoisyReceiver, intentFilter);
    }

    private void register_playNewAudio() {
        // register play new audio receiver
        IntentFilter intentFilter = new IntentFilter(AmbientActivity.Broadcast_PLAY_NEW_AUDIO);
        registerReceiver(playNewAudio, intentFilter);
    }

    private void register_pauseAudio() {
        // register pause audio receiver
        IntentFilter intentFilter = new IntentFilter(AmbientActivity.Broadcast_PAUSE_AUDIO);
        registerReceiver(pauseAudio, intentFilter);
    }

    private void callStateListener() {
        // Get the telephony manager
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // Start listening for phone state changes
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    // if at least one call exists or the phone is ringing, pause the MediaPlayer
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (player != null) {
                            pauseMedia();
                            ongoingCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Phone idle, start playing
                        if (player != null) {
                            if (ongoingCall) {
                                ongoingCall = false;
                                resumeMedia();
                            }
                        }
                        break;
                }
            }
        };

        // Register the listener with the telephony manager
        // Listen for changes to device call state
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void playMedia() {
        if (!player.isPlaying()) {
            player.start();
        }
    }

    private void stopMedia() {
        if (player == null) return;
        if (player.isPlaying()) {
            player.stop();
        }
    }

    private void pauseMedia() {
        if (player.isPlaying()) {
            player.pause();
            resumePosition = player.getCurrentPosition();
        }
    }

    private void resumeMedia() {
        if (!player.isPlaying()) {
            player.seekTo(resumePosition);
            player.start();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot("", null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (player == null) {
                    initMediaPlayer();
                } else if (! player.isPlaying()) {
                    player.start();
                }
                player.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if (player.isPlaying()) {
                    player.stop();
                }
                player.release();
                player = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (player.isPlaying()) {
                    player.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (player.isPlaying()) {
                    player.setVolume(0.1f, 0.1f);
                }
                break;
        }
    }

    private boolean requestAudioFocus() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(focusRequest);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private boolean removeAudioFocus() {
        if(audioManager != null) {
            return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.abandonAudioFocusRequest(focusRequest);
        }
        else{
            return false;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopMedia();
        stopSelf();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.d(TAG, "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK");
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.d(TAG, "MEDIA ERROR SERVER DIED" + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.d(TAG, "MEDIA ERROR UNKNOWN" + extra);
                break;
        }
        return false;
    }

    public class LocalBinder extends Binder {
        public MediaPlaybackService getService () {
            return MediaPlaybackService.this;
        }
    }
}

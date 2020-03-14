package com.pacman.MentAlly.ui.ambient;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private MediaPlayer player = null;
    private AudioManager audioManager;
    private int resumePosition;
    private AudioFocusRequest focusRequest;
    private AudioAttributes playbackAttributes;

    @Override
    public void onCreate() {
        super.onCreate();

        //Create a Media Session and Media Player
        mediaSession = new MediaSessionCompat(this, TAG);

        //Enable Callbacks from Media Buttons
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        //Set an initial playback state
        stateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());

        //Setup callback from controller
        mediaSession.setCallback(new MediaSessionCallBack());

        //Set the session token
        setSessionToken(mediaSession.getSessionToken());

        //Build attributes and focus request
        playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setOnAudioFocusChangeListener(this)
                .build();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            stopMedia();
            player.release();
        }
        removeAudioFocus();
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

    private void playMedia() {
        if (!player.isPlaying()) {
            player.start();
        }
    }

    private void stopMedia() {
        if (player.isPlaying()) {
            player.pause();
            resumePosition = player.getCurrentPosition();
        }
    }

    private void setResumePosition() {
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
        return result == AudioManager.AUDIOFOCUS_GAIN;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.abandonAudioFocusRequest(focusRequest);
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

    private class MediaSessionCallBack extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onStop() {
            super.onStop();
        }
    };

    public class LocalBinder extends Binder {
        public MediaPlaybackService getService () {
            return MediaPlaybackService.this;
        }
    }
}

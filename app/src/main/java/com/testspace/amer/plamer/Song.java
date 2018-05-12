package com.testspace.amer.plamer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Song {
    private String songName;
    private Uri songPath;
    private MediaPlayer mediaPlayer;
    private static Song nowPlaying;

    Song(String songName, String packageName, int songId) {
        this.songName = songName;
        songPath = Uri.parse("android.resource://" + packageName + "/" + songId);
    }

    public static Song getNowPlaying() {
        return nowPlaying;
    }

    public String getSongName() {
        return songName;
    }

    public void startPlayer(Context context) {
        mediaPlayer = MediaPlayer.create(context, songPath);
        mediaPlayer.start();
        nowPlaying = this;
    }

    public void stopPlayer() {
        mediaPlayer.stop();
    }

    public void pausePlayer() {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public String getDuration() {
        return String.format(Locale.US, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration()),
                TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration())));
    }

    public String getCurrentTime() {
        return String.format(Locale.US, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()),
                TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition())));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void play() {
        mediaPlayer.start();
        nowPlaying = this;
    }

    public int getIntDuration() {
        return mediaPlayer.getDuration();
    }

    public int getIntCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void goTo(int progress) {
        mediaPlayer.seekTo(progress);
    }
}
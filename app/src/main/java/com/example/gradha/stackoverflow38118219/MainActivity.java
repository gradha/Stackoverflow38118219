package com.example.gradha.stackoverflow38118219;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import static junit.framework.Assert.assertTrue;

public class MainActivity
    extends AppCompatActivity
    implements MediaPlayer.OnPreparedListener, MediaPlayer
    .OnCompletionListener, MediaPlayer.OnErrorListener, SurfaceHolder.Callback
{
    private static final String TAG = "MainActivity";
    MediaPlayer mMediaPlayer;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Starting…");
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        final String path = "android.resource://com.example.gradha.stackoverflow38118219/" + R.raw.yes;
        Log.d(TAG, "Loading " + path);
        mMediaPlayer = MediaPlayer.create(this, Uri.parse(path));

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setFixedSize(20, 10);
    }

    @Override public void onPrepared(MediaPlayer mp)
    {
        Log.d(TAG, "On prepared");
        mMediaPlayer.start();
    }

    @Override public void onCompletion(MediaPlayer mp)
    {
        Log.d(TAG, "On completion looping");
        mMediaPlayer.seekTo(10);
        mMediaPlayer.start();
    }

    @Override public boolean onError(MediaPlayer mp,
        int what,
        int extra)
    {
        Log.e(TAG, "error " + what + " extra " + extra);
        Toast.makeText(MainActivity.this, "Error " + what, Toast
            .LENGTH_SHORT).show();
        return true;
    }

    @Override public void surfaceCreated(SurfaceHolder holder)
    {
        Log.d(TAG, "SurfaceCreated");
        assertTrue(holder == mSurfaceHolder);
        mSurfaceHolder.setFixedSize(20, 10);
        mMediaPlayer.setSurface(mSurfaceHolder.getSurface());
    }

    @Override public void surfaceChanged(SurfaceHolder holder,
        int format,
        int width,
        int height)
    {
        Log.d(TAG, "SurfaceChanged to " +
            format + " width " + width + " height " + height);
    }

    @Override public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.d(TAG, "SurfaceDestroyed");
    }
}

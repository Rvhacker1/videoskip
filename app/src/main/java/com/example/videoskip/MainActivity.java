package com.example.videoskip;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private Handler handler = new Handler();
    private ArrayList<int[]> skipTimes = new ArrayList<>();
    private int currentSkipIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);

        skipTimes.add(new int[]{10000, 20000});
        skipTimes.add(new int[]{45000, 60000});
        skipTimes.add(new int[]{90000, 100000});

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample);
        videoView.setVideoURI(uri);
        videoView.start();

        monitorVideo();
    }

    private void monitorVideo() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoView != null && videoView.isPlaying() && currentSkipIndex < skipTimes.size()) {
                    int currentPosition = videoView.getCurrentPosition();
                    int[] skip = skipTimes.get(currentSkipIndex);
                    if (currentPosition >= skip[0] && currentPosition < skip[1]) {
                        videoView.seekTo(skip[1]);
                        currentSkipIndex++;
                    }
                }
                handler.postDelayed(this, 500);
            }
        }, 500);
    }
}

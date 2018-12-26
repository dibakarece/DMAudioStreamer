/*
 * This is the source code of DMAudioStreaming for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry(dibakar.ece@gmail.com), 2017.
 */
package dm.audiostreamerdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import dm.audiostreamerdemo.R;

public class SplashActivity extends AppCompatActivity {

    private static final long delayTime = 1000;
    Handler handler = new Handler();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.context = SplashActivity.this;
        handler.postDelayed(postTask, delayTime);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(postTask);
        super.onDestroy();
    }

    Runnable postTask = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(context, MusicActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
    };

}

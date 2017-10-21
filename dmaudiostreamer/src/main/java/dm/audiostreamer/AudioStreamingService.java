/*
 * This is the source code of DMAudioStreaming for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry(dibakar.ece@gmail.com), 2017.
 */
package dm.audiostreamer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class AudioStreamingService extends Service {
    private static final String TAG = Logger.makeLogTag(AudioStreamingService.class);

    public static final String EXTRA_CONNECTED_CAST = "dm.audiostreaming.CAST_NAME";
    public static final String ACTION_CMD = "dm.audiostreaming.ACTION_CMD";
    public static final String CMD_NAME = "CMD_NAME";
    public static final String CMD_PAUSE = "CMD_PAUSE";
    public static final String CMD_STOP_CASTING = "CMD_STOP_CASTING";
    private static final int STOP_DELAY = 30000;

    private AudioStreamingManager audioStreamingManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        audioStreamingManager = AudioStreamingManager.getInstance(AudioStreamingService.this);
    }

    @Override
    public int onStartCommand(Intent startIntent, int flags, int startId) {
        if (startIntent != null) {
            String action = startIntent.getAction();
            String command = startIntent.getStringExtra(CMD_NAME);
            if (ACTION_CMD.equals(action)) {
                if (CMD_PAUSE.equals(command)) {
                    audioStreamingManager.handlePauseRequest();
                } else if (CMD_STOP_CASTING.equals(command)) {
                    //TODO FOR EXTERNAL DEVICE
                }
            }
        }
        return START_NOT_STICKY;
    }
}

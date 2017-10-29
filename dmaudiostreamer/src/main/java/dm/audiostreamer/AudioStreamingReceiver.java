/*
 * This is the source code of DMAudioStreaming for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry(dibakar.ece@gmail.com), 2017.
 */
package dm.audiostreamer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

public class AudioStreamingReceiver extends BroadcastReceiver {

    private AudioStreamingManager audioStreamingManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.audioStreamingManager = AudioStreamingManager.getInstance(context);
        if(this.audioStreamingManager ==null){
            return;
        }
        if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            if (intent.getExtras() == null) {
                return;
            }
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent == null) {
                return;
            }
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    if (this.audioStreamingManager .isPlaying()) {
                        this.audioStreamingManager .onPause();
                    } else {
                        this.audioStreamingManager .onPlay(this.audioStreamingManager .getCurrentAudio());
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                    this.audioStreamingManager .onPlay(this.audioStreamingManager .getCurrentAudio());
                    break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    this.audioStreamingManager .onPause();
                    break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    this.audioStreamingManager .onSkipToNext();
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    this.audioStreamingManager .onSkipToPrevious();
                    break;
            }
        } else {
            this.audioStreamingManager = AudioStreamingManager.getInstance(context);
            if (intent.getAction().equals(AudioStreamingService.NOTIFY_PLAY)) {
                this.audioStreamingManager.onPlay(this.audioStreamingManager.getCurrentAudio());
            } else if (intent.getAction().equals(AudioStreamingService.NOTIFY_PAUSE)
                    || intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                this.audioStreamingManager.onPause();
            } else if (intent.getAction().equals(AudioStreamingService.NOTIFY_NEXT)) {
                this.audioStreamingManager.onSkipToNext();
            } else if (intent.getAction().equals(AudioStreamingService.NOTIFY_CLOSE)) {
                this.audioStreamingManager.cleanupPlayer(context, true, true);
            } else if (intent.getAction().equals(AudioStreamingService.NOTIFY_PREVIOUS)) {
                this.audioStreamingManager.onSkipToPrevious();
            }
        }
    }
}

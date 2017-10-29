/*
 * This is the source code of DMAudioStreaming for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry(dibakar.ece@gmail.com), 2017.
 */
package dm.audiostreamer;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AudioStreamingManager extends StreamingManager {
    private static final String TAG = Logger.makeLogTag(AudioStreamingManager.class);

    private AudioPlaybackListener audioPlayback;
    private CurrentSessionCallback currentSessionCallback;
    private static volatile AudioStreamingManager instance = null;
    private Context context;
    private int index = 0;
    private boolean playMultiple = false;
    private boolean showPlayerNotification = false;
    public PendingIntent pendingIntent;
    private MediaMetaData currentAudio;
    private List<MediaMetaData> mediaList = new ArrayList<>();
    public static volatile Handler applicationHandler = null;


    public static AudioStreamingManager getInstance(Context context) {
        if (instance == null) {
            synchronized (AudioStreamingManager.class) {
                instance = new AudioStreamingManager();
                instance.context = context;
                instance.audioPlayback = new AudioPlaybackListener(context);
                instance.audioPlayback.setCallback(new MyStatusCallback());
                applicationHandler = new Handler(context.getMainLooper());
            }
        }
        return instance;
    }

    public void subscribesCallBack(CurrentSessionCallback callback) {
        this.currentSessionCallback = callback;
    }

    public int getCurrentIndex() {
        return this.index;
    }

    public void unSubscribeCallBack() {
        this.currentSessionCallback = null;
    }

    public MediaMetaData getCurrentAudio() {
        return currentAudio;
    }

    public String getCurrentAudioId() {
        return currentAudio != null ? currentAudio.getMediaId() : "";
    }

    public boolean isPlayMultiple() {
        return playMultiple;
    }

    public void setPlayMultiple(boolean playMultiple) {
        this.playMultiple = playMultiple;
    }

    public boolean isPlaying() {
        return instance.audioPlayback.isPlaying();
    }

    public void setPendingIntentAct(PendingIntent mPendingIntent) {
        this.pendingIntent = mPendingIntent;
    }

    public void setShowPlayerNotification(boolean showPlayerNotification) {
        this.showPlayerNotification = showPlayerNotification;
    }

    public void setMediaList(List<MediaMetaData> currentAudioList) {
        if (this.mediaList != null) {
            this.mediaList.clear();
            this.mediaList.addAll(currentAudioList);
        }
    }

    public void clearList() {
        if (this.mediaList != null && mediaList.size() > 0) {
            this.mediaList.clear();
            this.index = 0;
            this.onPause();
        }
    }

    @Override
    public void onPlay(MediaMetaData infoData) {
        if (infoData == null) {
            return;
        }
        if (playMultiple && !isMediaListEmpty()) {
            index = mediaList.indexOf(infoData);
        }
        if (this.currentAudio != null && this.currentAudio.getMediaId().equalsIgnoreCase(infoData.getMediaId()) && instance.audioPlayback != null && instance.audioPlayback.isPlaying()) {
            onPause();
        } else {
            this.currentAudio = infoData;
            handlePlayRequest();
            if (currentSessionCallback != null)
                currentSessionCallback.playCurrent(index, currentAudio);
        }
    }

    @Override
    public void onPause() {
        handlePauseRequest();
    }

    @Override
    public void onStop() {
        handleStopRequest(null);
    }

    @Override
    public void onSeekTo(long position) {
        audioPlayback.seekTo((int) position);
    }

    @Override
    public int lastSeekPosition() {
        return (audioPlayback == null) ? 0 : (int) audioPlayback.getCurrentStreamPosition();
    }

    @Override
    public void onSkipToNext() {
        int nextIndex = index + 1;
        if (isValidIndex(true, nextIndex)) {
            MediaMetaData metaData = mediaList.get(nextIndex);
            onPlay(metaData);
            if (instance.currentSessionCallback != null) {
                currentSessionCallback.playNext(nextIndex, metaData);
            }
        }
    }


    @Override
    public void onSkipToPrevious() {
        int prvIndex = index - 1;
        if (isValidIndex(false, prvIndex)) {
            MediaMetaData metaData = mediaList.get(prvIndex);
            onPlay(metaData);
            if (instance.currentSessionCallback != null) {
                currentSessionCallback.playPrevious(prvIndex, metaData);
            }
        }
    }

    /**
     * @return
     */
    public boolean isMediaListEmpty() {
        return (mediaList == null || mediaList.size() == 0);
    }

    /**
     * @param isIncremental
     * @return
     */
    private boolean isValidIndex(boolean isIncremental, int index) {
        if (isIncremental) {
            return (playMultiple && !isMediaListEmpty() && mediaList.size() > index);
        } else {
            return (playMultiple && !isMediaListEmpty() && index >= 0);
        }
    }

    public void handlePlayRequest() {
        Logger.d(TAG, "handlePlayRequest: mState=" + audioPlayback.getState());
        if (audioPlayback != null && currentAudio != null) {
            audioPlayback.play(currentAudio);
            if (showPlayerNotification) {
                if (context != null) {
                    Intent intent = new Intent(context, AudioStreamingService.class);
                    context.startService(intent);
                } else {
                    Intent intent = new Intent(context, AudioStreamingService.class);
                    context.stopService(intent);
                }

                NotificationManager.getInstance().postNotificationName(NotificationManager.audioDidStarted, currentAudio);
                NotificationManager.getInstance().postNotificationName(NotificationManager.audioPlayStateChanged, getCurrentAudio().getMediaId());
                setPendingIntent();
            }
        }
    }

    private void setPendingIntent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pendingIntent != null) {
                    NotificationManager.getInstance().postNotificationName(NotificationManager.setAnyPendingIntent, pendingIntent);
                }
            }
        },400);
    }

    public void handlePauseRequest() {
        Logger.d(TAG, "handlePauseRequest: mState=" + audioPlayback.getState());
        if (audioPlayback != null && audioPlayback.isPlaying()) {
            audioPlayback.pause();
            if (showPlayerNotification) {
                NotificationManager.getInstance().postNotificationName(NotificationManager.audioPlayStateChanged, getCurrentAudio().getMediaId());
            }
        }
    }

    public void handleStopRequest(String withError) {
        Logger.d(TAG, "handleStopRequest: mState=" + audioPlayback.getState() + " error=", withError);
        audioPlayback.stop(true);
    }

    static class MyStatusCallback implements PlaybackListener.Callback {
        @Override
        public void onCompletion() {
            if (instance.playMultiple && !instance.isMediaListEmpty()) {
                if (instance.currentSessionCallback != null) {
                    instance.currentSessionCallback.playSongComplete();
                }
                instance.onSkipToNext();
            } else {
                instance.handleStopRequest(null);
            }
        }

        @Override
        public void onPlaybackStatusChanged(int state) {
            try {
                if (state == PlaybackStateCompat.STATE_PLAYING) {
                    instance.scheduleSeekBarUpdate();
                } else {
                    instance.stopSeekBarUpdate();
                }
                if (instance.currentSessionCallback != null) {
                    instance.currentSessionCallback.updatePlaybackState(state);
                }

                instance.mLastPlaybackState = state;
                if(instance.currentAudio!=null){
                    instance.currentAudio.setPlayState(state);
                }
                if (instance.showPlayerNotification) {
                    NotificationManager.getInstance().postNotificationName(NotificationManager.audioPlayStateChanged, instance.getCurrentAudio().getMediaId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(String error) {
            //TODO FOR ERROR
        }

        @Override
        public void setCurrentMediaId(String mediaId) {

        }

    }


    public void cleanupPlayer(Context context, boolean notify, boolean stopService) {
        cleanupPlayer(notify, stopService);
    }

    public void cleanupPlayer(boolean notify, boolean stopService) {
        handlePauseRequest();
        audioPlayback.stop(true);
        if (stopService) {
            Intent intent = new Intent(context, AudioStreamingService.class);
            context.stopService(intent);
        }
    }

    private ScheduledFuture<?> mScheduleFuture;
    public int mLastPlaybackState;
    private long currentPosition = 0;
    private final Handler mHandler = new Handler();
    private static final long PROGRESS_UPDATE_INTERNAL = 1000;
    private static final long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;
    private final ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    public void scheduleSeekBarUpdate() {
        stopSeekBarUpdate();
        if (!mExecutorService.isShutdown()) {
            mScheduleFuture = mExecutorService.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(mUpdateProgressTask);
                        }
                    }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS);
        }
    }

    public void stopSeekBarUpdate() {
        if (mScheduleFuture != null) {
            mScheduleFuture.cancel(false);
        }
    }

    private void updateProgress() {
        if (instance.mLastPlaybackState == 0 || instance.mLastPlaybackState < 0) {
            return;
        }
        if (instance.mLastPlaybackState != PlaybackStateCompat.STATE_PAUSED && instance.currentSessionCallback != null) {
            instance.currentSessionCallback.currentSeekBarPosition((int) audioPlayback.getCurrentStreamPosition());
        }
    }

}

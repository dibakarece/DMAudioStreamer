DMAudioStreamer
==================

[![License](https://img.shields.io/github/license/blipinsk/StaggeredAnimationGroup.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Release](https://jitpack.io/v/dibakarece/DMAudioStreamer.svg?style=flat-square)](https://jitpack.io/#dibakarece/DMAudioStreamer)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-DMAudioStreamer-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6383)

`DMAudioStreamer` library help you to integrate audio streaming in your application.

<a href="https://play.google.com/store/apps/details?id=dm.audiostreamerdemo">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

App Screens
=====
![scr2](https://user-images.githubusercontent.com/10453203/31852066-f53dcf92-b68e-11e7-821c-683c5d540b23.png)
![scr3](https://user-images.githubusercontent.com/10453203/31852069-0256ecd6-b68f-11e7-84fe-38e725a4b6f5.png)
![scr4](https://user-images.githubusercontent.com/10453203/31852070-04bdf492-b68f-11e7-8c92-1ec26b2042db.png)
![scr5](https://user-images.githubusercontent.com/10453203/31852072-081aadb0-b68f-11e7-84da-372ab3dca009.png)
![scr6](https://user-images.githubusercontent.com/10453203/31852075-0bf357fc-b68f-11e7-93f0-7cba38d4974b.png)
![scr7](https://user-images.githubusercontent.com/10453203/32148071-15dac65e-bd17-11e7-9175-d9f798d026fd.png)

Usage
=====
*For a working implementation of this library `clone/download` this repository.*

*How to add Library in your project*:
```xml
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
```xml
dependencies {
    implementation 'com.github.dibakarece:DMAudioStreamer:v1.0.5'
}
```

1. Add below line code for your application to implement audio streaming :

```java
public class MusicActivity extends AppCompatActivity implements CurrentSessionCallback{
       @Override
           protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);

               this.context = MusicActivity.this;
               streamingManager = AudioStreamingManager.getInstance(context);
           }

       @Override
       public void onStart() {
           super.onStart();
           if (streamingManager != null) {
               streamingManager.subscribesCallBack(this);
           }
       }

       @Override
       public void onStop() {
           super.onStop();
           if (streamingManager != null) {
               streamingManager.unSubscribeCallBack();
           }
       }

       @Override
           public void updatePlaybackState(int state) {
               switch (state) {
                   case PlaybackStateCompat.STATE_PLAYING:
                       break;
                   case PlaybackStateCompat.STATE_PAUSED:
                       break;
                   case PlaybackStateCompat.STATE_NONE:
                       break;
                   case PlaybackStateCompat.STATE_STOPPED:
                       break;
                   case PlaybackStateCompat.STATE_BUFFERING:
                       break;
               }
           }

           @Override
           public void playSongComplete() {
           }

           @Override
           public void currentSeekBarPosition(int progress) {
           }

           @Override
           public void playCurrent(int indexP, MediaMetaData currentAudio) {
           }

           @Override
           public void playNext(int indexP, MediaMetaData CurrentAudio) {
           }

           @Override
           public void playPrevious(int indexP, MediaMetaData currentAudio) {
           }

}
```

2. You can play audio in `Single/Multiple` mode based on your code:

```java
        streamingManager.setMediaList(`Your music list`);
        streamingManager.setPlayMultiple(`True/False`);
```

3. For play music:

```java
         MediaMetaData obj = new MediaMetaData();
         infoData.setMediaId(`id`); *Media Id*
         infoData.setMediaUrl(`source`); *Media source `https://yourmusicsource/talkies.mp3`*
         infoData.setMediaTitle(`title`);
         infoData.setMediaArtist(`artist`);
         infoData.setMediaAlbum(`album`);
         infoData.setMediaComposer(`composer`);
         infoData.setMediaDuration(`duration`); *Media Duration Sec.*
         infoData.setMediaArt(`image`); *Media Art*

         streamingManager.onPlay(`Your Music MetaData`);
```
4. For notification controller(For more details please check my demo app):

```java
         streamingManager.setShowPlayerNotification(true);
         streamingManager.setPendingIntentAct(`Create Your Pending Intent And Set Here`);
```

```xml
<service
     android:name="dm.audiostreamer.AudioStreamingService"
     android:enabled="true"
     android:exported="true" />
<receiver android:name="dm.audiostreamer.AudioStreamingReceiver">
      <intent-filter>
           <action android:name="dm.audiostreamer.close" />
           <action android:name="dm.audiostreamer.pause" />
           <action android:name="dm.audiostreamer.next" />
           <action android:name="dm.audiostreamer.play" />
           <action android:name="dm.audiostreamer.previous" />
           <action android:name="android.intent.action.MEDIA_BUTTON" />
           <action android:name="android.media.AUDIO_BECOMING_NOISY" />
      </intent-filter>
</receiver>
```

License
=======

    Copyright 2017 Dibakar Mistry

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

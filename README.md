DMAudioStreamer
==================

[![License](https://img.shields.io/github/license/blipinsk/StaggeredAnimationGroup.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

`DMAudioStreamer` library help you to integrate audio streaming in your application.

App Screens
=====
![scr2](https://user-images.githubusercontent.com/10453203/31852066-f53dcf92-b68e-11e7-821c-683c5d540b23.png)
![scr3](https://user-images.githubusercontent.com/10453203/31852069-0256ecd6-b68f-11e7-84fe-38e725a4b6f5.png)
![scr4](https://user-images.githubusercontent.com/10453203/31852070-04bdf492-b68f-11e7-8c92-1ec26b2042db.png)
![scr5](https://user-images.githubusercontent.com/10453203/31852072-081aadb0-b68f-11e7-84da-372ab3dca009.png)
![scr6](https://user-images.githubusercontent.com/10453203/31852075-0bf357fc-b68f-11e7-93f0-7cba38d4974b.png)
![scr1](https://user-images.githubusercontent.com/10453203/31852076-0ff03636-b68f-11e7-8881-d0cd4d540966.png)

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
		compile 'com.github.dibakarece:dmaudiostreamer:v1.0.0'
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
        streamingManager.setMediaList(listMusic);
        streamingManager.setPlayMultiple(true);
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

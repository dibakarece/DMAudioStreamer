DMAudioStreamer
==================

[![License](https://img.shields.io/github/license/blipinsk/StaggeredAnimationGroup.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

`DMAudioStreamer` library help you to integrate audio streaming in your application.


Usage
=====
*For a working implementation of this library `clone/download` this repository.*

1. Add below line code for yor application to implement audio streaming :
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
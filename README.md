## Sample App Download
https://www.dabkick.com/api/resources/videosdk/sample-app.apk



# Contents

- [Introduction](#introduction)
- [Quick Start](#quick-start)
- [Full Code Snippet](#full-code-snippet)
- [Developer Tokens](#developer-tokens)



# Introduction

DabKick Video SDK is an Android Library product from DabKick Inc. that brings the *Watch Together Live* experience to any Android video application. The SDK can be deployed quickly and easily. This capability is described in detail below.



## What is “Watch Together Live?”

*Watch Together Live* is a feature of DabKick Video SDK that allows users to watch videos together on separate devices in perfect sync. Like watching TV with friends or family in the same room, *Watch Together Live* provides a similar experience, granting users the ability to view the same content at the same time, share media control, and interact with one another. The SDK comes equipped with video chatting capabilites and it's own messaging system, so users feel connected while watching. In short, the *Watch Together Live* experience transforms video-watching into a social interaction, which is far more enjoyable than watching alone.

Along with the library, a sample application is provided for developers in order to demonstrate how DabKick Video SDK can be integrated into existing video applications quickly and easily.



## How does DabKick Video SDK work?

Video-watching virtual "rooms" are created dynamically for users to watch videos together and interact via text and video chat. Users can initiate sessions by selecting videos to watch through the app interface, then clicking on a "Watch Together Live" button. Users can then invite friends by using a preferred messaging system such as text, email, or Google Hangouts. The SDK library will then automatically provide a link to be sent for the invitation. Recipients of the invitation then click on the incoming notification, which launches the relevant app and allows them to join the session automatically.

All entering users are automatically synchronized with the content playing on the stage at the time of entry. Any user in the room can pause, play, or seek the video, the effects of which will instantly be reflected in all other users’ screens. Users will be able to view other users in the room in a scrollable list of avatars, and can send messages and emojis in a group chat. There is also a video chat option which allows for one or more users to livestream video and audio from their own devices to the virtual room. Users can exit and re-enter sessions (by clicking the original invitation link) any number of times without loss of video synchronization, and with no need for additional invitations to the same session. The interface of the SDK is simple and intuitive, designed to be easy for all users to use and understand.



## Use Cases

There are numerous use cases for the *Watch Together Live* feature. Firstly, *Watch Together Live* is an entertaining experience for any users who watch videos of common interest. Watching videos together can also enhance CRM engagement, whereby customers can be retained and supported via educational videos or other media in relation to a post-sale engagement. Any application platform in which customers congregate to discuss sales (i.e. e-commerce), function coordination (i.e. event logistics), or information (i.e. museum art) can benefit from live video sharing. In each of these cases, *Watch Together Live* can significantly increase user engagement. A customer support function may also allow for a host to be present in the room at all times to moderate interactions, and address common questions or issues via live video chat. In all of these cases, a quick integration of the application with the DabKick Video SDK library will provide the means to create and deploy that experience without need for server set up, steep learning curves, or difficult installation.

*Watch Together Live* is an innovative feature that is easy to integrate and deploy, enhances customer engagement, and increases customer acquisition and retention.




# Quick Start

*DabKick Video SDK is currently only available for Android.*

This guide will instruct you how to implement the DabKick Video SDK into your mobile application. To download a sample app with this SDK, click [here](https://www.dabkick.com/api/resources/videosdk/sample-app.apk).



## Prerequisites

- DabKick Video SDK requires a minimum Android API level of 17 (Jelly Bean).
- All major standard video formats are supported. For a full list, click [here](https://exoplayer.dev/supported-formats.html).



## Installation

**Step 1:** In your project-level `build.gradle`:
- Add both maven blocks into the repositories block.
- Add the project.ext block and replace the key and id with your own.

```
allprojects {
    project.ext {
        dabKickDeveloperId="your_dabkick_developer_id"
        dabKickDeveloperKey="your_dabkick_developer_key"
    }
    
    repositories {
        maven {
            url "https://jitpack.io/"
        }
        maven {
            url "http://dabkick.bitnamiapp.com/artifactory/android-dev-videosdk-local/"
        }
    }
}
```


**Step 2:** In your app-level `build.gradle`, add the following compile dependency:

```
dependencies {
    compile ('com.dabkick.videosdk:videosdk:1.0.1') {
        transitive = true
    }
}
```


**Step 3:** In `AndroidManifest.xml`, Add the following permissions:
 ```
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```


**Step 4:** If the application class is not already extended, then include the `android:name` attribute for a custom application class in `AndroidManifest.xml`.

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.package.yourapp">
    <application
        android:name=".MyApplication" 
    </application>
</manifest>
```


**Step 5:** Extend the `SdkApp` class from the class you created or already have in Step 4. Then call `DabKick.setVideoProvider()`.

```
public class MyApplication extends SdkApp {
    @Override
    public void onCreate() {
        super.onCreate();
        DabKick.setVideoProvider(/* your video provider instance */);
    }
}
```


You're ready to *Watch Together Live*! You can call `DabKick.watchWithFriends(context)` at any point to start watching videos.



## Configuration


**Step 1:** Add Proguard rules to `proguard-rules.pro`. This prevents DabKick from being removed by ProGuard.

```
-dontwarn com.squareup.okhttp.**
-dontwarn com.google.errorprone.annotations.*
-dontwarn retrofit2.Platform$Java8
-dontwarn okio.**
-keepattributes *Annotation*
-keepclassmembers class ** { @org.greenrobot.eventbus.Subscribe ; }
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepattributes Signature
-keepclassmembers class com.dabkick.videosdk.livesession.models.** { *; }
-keep public class com.dabkick.videosdk.livesession.models.** { *; }
-keep class org.webrtc.** { *; }
-keep class com.twilio.video.** { *; }
-keepattributes InnerClasses
```

**Step 2:** Implement a `createDabKickVideoProvider()` interface to set up videos to play.

- `provideVideos()` returns videos which are to be added to the specified category, from given video offset.
- `provideCategories()` returns categories of videos which can be scrolled by the user.
- `startDabKickWithVideos()` supplies optional video(s) to start a session with.

```
private DabKickVideoProvider createDabKickVideoProvider() {
    DabKickVideoProvider dabKickVideoProvider = new DabKickVideoProvider() {
        @Override
        public ArrayList< DabKickVideoInfo> provideVideos(String category, int offset) {
            return new ArrayList< >(/* your list of DabKickVideoInfo items */);
        }
        
        @Override
        public ArrayList< String> provideCategories(int offset) {
            return new ArrayList< >(/* your list of category Strings */);
        }
        
        @Override
        public ArrayList< DabKickVideoInfo> startDabKickWithVideos() {
            return new ArrayList< >(/* your list of optional videos */);
        }
    };
    return dabKickVideoProvider;
}
```




## Using DabKick Video SDK

To use the SDK, you will need two pieces of information to identify your project uniquely (Developer ID and Developer Key), both of which are generated and displayed for your use in the section below titled "Developer Tokens."



## Creating the Button
 
DabKick supplies a View called `DabKickVideoButton`. This button allows the developer to use DabKick Video SDK’s UI. No functionality is supplied; an `onClickListener` for this button (`setDabKickClickListener()`) must used to call `DabKick.watchWithFriends()`. See example below:

```
// button which, upon click, starts DabKick session
DabKickVideoButton button = findViewById(R.id.dabkick_video_button);
button.setDabKickClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Optionally add any videos which are to be shown to the user
        // inside DabKickVideoProvider.startDabKickWithVideos()
        DabKick.watchWithFriends(MainActivity.this);
    }
});
```



## Starting a Live Session

Once the SDK is installed and configured, we are now ready to start a Live Session. Live Sessions can be started with or without initializing media. If a set of videos are initialized at the start, the media will be immediately staged and made ready to be watched together, upon entering the Live Session. Alternatively, we can start a session with no media, letting users rely on acquiring and selecting media from a media "shelf" from inside the live session. These methods of starting a live session are described in detail in the section below.

```DabKick.watchWithFriends(context);```



## Add Videos to a Live Session

You can start the session with videos of your choice. See code below for a simple implementation of a code snippet. Providing a video at the start is optional — if this method returns an empty `ArrayList`, then the live session will start without any videos. All videos provided will be listed in the library upon initiating the live session. Videos can also be selected to play by the user by manually browsing for videos upon starting the session.

```
private DabKickVideoProvider createDabKickVideoProvider() {
    return new DabKickVideoProvider() {
        
        // ...other implemented methods
        
        @Override
        public ArrayList<DabKickVideoInfo> startDabKickWithVideos() {
            ArrayList<DabKickVideoInfo> list = new ArrayList<>();
            if (userSelectedVideoInfo != null) {
                list.add(userSelectedVideoInfo);
            }
            return list;
        }
    };
}
```


 
# Full Code Snippet

```
import com.dabkick.videosdk.publicsettings.DabKick;
import com.dabkick.videosdk.publicsettings.DabKickVideoButton;
import com.dabkick.videosdk.publicsettings.DabKickVideoInfo;
import com.dabkick.videosdk.publicsettings.DabKickVideoProvider;

public class PartnerApplication extends SdkApp {
    @Override
    public void onCreate() {
        super.onCreate();
        DabKickVideoProvider dabkickVideoProvider = createDabKickVideoProvider();
        DabKick.setVideoProvider(dabkickVideoProvider);
    }
    
    private DabKickVideoProvider createDabKickVideoProvider() {
        DabKickVideoProvider dabKickVideoProvider = new DabKickVideoProvider() {
            @Override
            public ArrayList<DabKickVideoInfo> provideVideos(String category, int offset) {
                return new ArrayList<>(/* your list of DabKickVideoInfo items */);
            }
            
            @Override
            public ArrayList<String> provideCategories(int offset) {
                return new ArrayList<>(/* your list of category Strings */);
            }
            
            @Override
            public ArrayList<DabKickVideoInfo> startDabKickWithVideos() {
                return new ArrayList<>(/* your list of optional videos */);
            }
        };
        return dabKickVideoProvider;
    }
}
```



# Developer Tokens

Click [here](https://www.dabkick.com/sdk/documentation/#dev-tokens-section) to receive your Developer Tokens.

[return to top](#contents)

# Sample App Download
https://www.dabkick.com/api/resources/videosdk/sample-app.apk



# Introduction
 
DabKick Video SDK is an Android Library product from DabKick, Inc. that brings the Watch-Together-Live experience to any Android video application. The SDK can be deployed quickly and easily in minutes. This capability is described in detail below.



## What is “Watch Together Live?”

*Watch Together Live* is the ability to invite friends and families to watch videos together in perfect sync. Think of your last super bowl TV watching party - except in this case the party is virtual - that is, everyone need not be physically in the same room. Anyone can initiate a session to watch a video, and issue an invitation to a friend. The invited user gets notified on their Android mobile in the usual manner that they are used to, perhaps a text message in their hangout application. She joins the session by simply clicking on the link in the notification message. Upon joining, the video application is launched and opens to show the same video as was being watched. The video syncs up rapidly, so everyone in the room is now watching the same exact scene in the same video, live, as everyone else in the virtual room.
 
The user experience is designed to make it simple and easy to use. Once a user joins a live session, she can in turn invite others to join this video watching party. Anyone in the session can control the video, too, or change it as needed to reflect their preference and the dynamics of the room. She will be able to see who else has joined - the newcomers are automatically shown in a scrollable list of avatars - and she can exchange chat messages while watching the video. Like families gathered virtually in the same room in front of the same TV, everyone is watching the same video on the same virtual stage, and anyone can control (within limits) the videos on the stage. As families are wont to do, one or more folks in the room might want to video chat - and this is easy as clicking on a video streaming button on the top of the screen. Video stream can be turned off and only audio chat enabled if so desired, of course, by any one in the room. Users can leave the room anytime and, by clicking on the original invitation link, can rejoin the virtual room as long as the session is going on, without having to be reinvited again. 
 
From our own experiences watching TV with families and friends in our living room, we can easily relate to this *Watch Together Live* experience: a social video watch party that transforms and enhances a video watching experience in any video application, when compared with watching videos alone.
Along with the library, a sample application is provided to developers in order to demonstrate how the Video SDK can be integrated into your existing video application easily and with minimal effort.



## How does DabKick Video SDK work?

Video Watching "virtual rooms" are created dynamically for users to congregate and watch videos together, and to send and receive chat messages. Users can initiate a session by selecting a video to watch, and clicking on the Watch-Together_Live button. They can then invite friends by using the messaging system that they are most comfortable with in their Android devices - such as hangouts or emails. The library will automatically provide a link to be sent for the invitation. The receiving user simply clicks on the incoming notification to launch the relevant app and enter the session automatically. The entering user is automatically synced with what’s playing on the stage. Any user can control the video using video controls to pause/play/seek - which will be instantly reflected in all users’ screens. Users in the session can send each other chat messages and emojis. There is also a video chat option which allows for one or more users to livestream their own video and audio to the virtual room. It is a simple and intuitive user experience made possible by a user interface designed to be easy for all users to grasp the concept of watching-together-live & start enjoying the feature immediately with no need for training. Users can exit and reenter the same session (by clicking the original invitation link) any number of times without loss of synced video experience, and with no need for additional invitations to the same session.



## Use Cases
 
There are numerous use cases for the *Watch Together Live* capability where groups of people get together to watch videos together, and chat about it via video or text. Any video of common interest - be it a sports event, entertainment or cooking lessons, watching together live has a joyful dynamic of its own. Watching a video together also can enhance CRM engagement where customers can be retained and supported via educational or other value-added videos in relation to a post-sale engagement. Any application platform where customers may get together to chat about a sales or support function (for instance, e-commerce) or an organizing or coordination function (for instance, event logistics) or informational reasons (museum art) can benefit by adding videos to the engagement. In each of these cases, Watching-Together-Live capability can significantly increase user engagement by adding a video and video chat to the mix. A customer support function may also allow for a host to be present in the room at all times to moderate interactions, and address common questions or issues via live video chat. In all these cases, a quick integration of the application with the Video SDK library will provide the means to create and deploy that experience without worries about server set up, steep learning curves or long installation time for developers.
This is an innovative *Watch Together Live* capability from DabKick that Android developers have come to love - because it is easy to integrate and deploy - and product managers want to deploy - because it enhances customer engagement as well as improves customer acquisition and retention.




# Quick Start
 
*DabKick SDK is currently only available for Android.*
 
This guide will instruct you how to implement the DabKick SDK into your mobile application. To download a sample app with this SDK, click [here](https://www.dabkick.com/api/resources/videosdk/sample-app.apk).



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


You're ready to Watch with Friends! You can call `DabKick.watchWithFriends(context)` at any point to start watching videos.
 
 
 
## Configuration
 
**Step 1:** In `AndroidManifest.xml`, add the following permissions:

```
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```


If the application class is not already extended, then include the `android:name` attribute for a custom application class in `AndroidManifest.xml`.

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.package.yourapp">
    <application
        android:name=".MyApplication"
    </application>
</manifest>
```
 
 
**Step 2:** Have your new or existing application class extend `SdkApp`.

```
public class MyApplication extends SdkApp {
    @Override
    public void onCreate() {
        super.onCreate();
        DabKick.setVideoProvider(/* your video provider instance */);
    }
}
```
 
 
**Step 3:** Add Proguard rules to `proguard-rules.pro`. This prevents DabKick from being removed by ProGuard.

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
 
 
`provideVideos()` returns videos which are to be added to the specified category, from given video offset.
`provideCategories()` returns categories of videos which can be scrolled by the user.
`startDabKickWithVideos()` supplies optional video(s) to start a session with.

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
 

 
 
## Using the SDK
 
To get started, please install and configure the SDK by following the steps below. You will need two pieces of information to identify your project uniquely (developer ID and developer Key), both of which are generated and displayed for your use when you click the section titled "Developer Keys" below.



## Creating the Button
 
DabKick supplies a View called `DabKickVideoButton`. This button allows the developer to use DabKick’s UI. No functionality is supplied; an `onClickListener` for this button (`setDabKickClickListener()`) must used to call `DabKick.watchWithFriends()`. See example below:
 
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
 
Once the SDK is installed and configured, we are now ready to start what we call a Live Session. Live Sessions can be started with or without initializing media. If a set of videos or photos are initialized at the start, the media will be immediately staged and made ready to be watched together, upon entering the Live Session. Alternatively, we can start a session with no media, letting users rely on acquiring and selecting media from a media "shelf" from inside the live session. These methods of starting a live session are described in detail in the section below.
 
```DabKick.watchWithFriends(context);```
 

 
## Add Videos to Live Media Session
 
You can start the session with videos of your choice. See code below for implementation. This method is optional — if this method returns an empty `ArrayList`, then the live session will start without any videos. Videos can instead be added by the user by manually browsing for videos once in the session.

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
 

 
## Create Custom Categories in Live Session
 
When users browse for media to browse, they can do so by opening the media drawing shown in the screenshot above. They can browse media organized by categories, as shown in item 1 in the screenshot. Clicking on a category shows photos of that category in item 2. The following method provides the media drawer with the category names.
Category names are loaded asynchronously and can be paginated. When the user has scrolled past all the category names that have been set, the live session will request more category names from the delegate with offset equal to the number of category names already loaded. Sending back an empty `ArrayList` signifies that there are no more categories to load.

```
private DabKickVideoProvider createDabKickVideoProvider() {
    return new DabKickVideoProvider() {
           
        // ...other implemented methods
 
        @Override
        public ArrayList provideCategories(int offset) {
            if (offset == videoCategories.size()) {
                // cannot provide any more video categories
                return new ArrayList<>();
            }
 
            int endIndex = Math.min(videoCategories.size(), offset + 5);
            ArrayList categoryList = new ArrayList<>(videoCategories.subList(offset, endIndex)); 
            return categoryList;
        }
    }
}
```


This method provides the images to show in item 2 of the screenshot. When the user opens a category in the media shelf, the live session will request content asynchronously and with an offset for pagination for a content type, similar to how category names are loaded. Sending back an empty `ArrayList` signifies that there is no more content to load for the specified category.

```
private DabKickVideoProvider createDabKickVideoProvider() {
    return new DabKickVideoProvider() {
 
        @Override
        public ArrayList provideVideos(String category, int offset) {
            int totalSize = videoHolder.get(category).size();
            int endIndex = Math.min(totalSize, offset + 3);
            ArrayList list = new ArrayList<>(videoHolder.get(category).subList(offset, endIndex));
            return list;
        }
 
        // ...other implemented methods
 
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
 
Click [here](https://www.dabkick.com/sdk/documentation/#dev-tokens) to receive your Developer Tokens.

package sample.sdk.dabkick.sampleappdkvp;


import android.app.Activity;

import com.dabkick.videosdk.publicsettings.DabKickVideoInfo;
import com.dabkick.videosdk.publicsettings.DabKickVideoProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {

    private static ArrayList<String> fullStreamUrlList = new ArrayList<>();
    private static ArrayList<String> urlList = new ArrayList<>();
    public static Activity utilActivity;

    public static DabKickVideoProvider createDabKickVideoProvider(final DabKickVideoInfo selectedVideoInfo) {

        final ArrayList<String> videoCategories = new ArrayList<>(Arrays.asList(
                "TiVo", "Comedy", "Action", "Animation", "Travel", "Arts", "SciFi"));
        final Map<String, ArrayList<DabKickVideoInfo>> videoHolder = new LinkedHashMap<>();
        ArrayList<DabKickVideoInfo> videos = Util.getDailyMailVideos();

        videoHolder.put(videoCategories.get(0), videos);
        videoHolder.put(videoCategories.get(1), copyList(1, 5));
        videoHolder.put(videoCategories.get(2), copyList(2, 6));
        videoHolder.put(videoCategories.get(3), copyList(3, 5));
        videoHolder.put(videoCategories.get(4), copyList(4, 6));
        videoHolder.put(videoCategories.get(5), copyList(0, 4));
        videoHolder.put(videoCategories.get(6), copyList(1, 5));

        return new DabKickVideoProvider() {
            @Override
            public ArrayList<DabKickVideoInfo> provideVideos(String category, int offset) {
                int totalSize = videoHolder.get(category).size();
                int endIndex = Math.min(totalSize, offset + 3);
                ArrayList<DabKickVideoInfo> list = new ArrayList<>(videoHolder.get(category).subList(offset, endIndex));
                return list;
            }

            @Override
            public ArrayList<String> provideCategories(int offset) {
                if (offset == videoCategories.size()) {
                    // cannot provide any more video categories
                    return new ArrayList<>();
                }
                int endIndex = Math.min(videoCategories.size(), offset + 5);
                ArrayList<String> categoryList = new ArrayList<>(videoCategories.subList(offset, endIndex));
                return categoryList;
            }

            @Override
            public ArrayList<DabKickVideoInfo> startDabKickWithVideos() {
                ArrayList<DabKickVideoInfo> smallerList = new ArrayList<>();
                if (selectedVideoInfo != null) {
                    smallerList.add(selectedVideoInfo);
                }
                return smallerList;
            }
        };
    }

    private static ArrayList<DabKickVideoInfo> copyList(int start, int end) {
        ArrayList<DabKickVideoInfo> result = new ArrayList<>(), list = getDailyMailVideos();

        for (int i = start; i < end; i++) {
            result.add(list.get(i));
        }

        return result;
    }

    public static ArrayList<DabKickVideoInfo> getDailyMailVideos() {

        urlList.add("https://www.youtube.com/watch?v=adzYW5DZoWs");
        urlList.add("https://www.youtube.com/watch?v=198gzllaumo");
        urlList.add("https://www.youtube.com/watch?v=Uw5JOtvFd-k");
        urlList.add("https://www.youtube.com/watch?v=bo_efYhYU2A");
        urlList.add("https://www.youtube.com/watch?v=6hnRJ0j0Mqo");
        urlList.add("https://www.youtube.com/watch?v=r43LhSUUGTQ");
        urlList.add("https://www.youtube.com/watch?v=IyXn4chXcqY");
        urlList.add("https://www.youtube.com/watch?v=IOk9SVzqHsk");
        urlList.add("https://www.youtube.com/watch?v=sNKtuwSyMLs");
        urlList.add("https://www.youtube.com/watch?v=FbDNVDXG5q8");
        urlList.add("https://www.youtube.com/watch?v=iol8n3m88SA");
        urlList.add("https://www.youtube.com/watch?v=DQh-1N_inMc");

         //getStreamUrls();



//        DabKickVideoInfo detaila = new DabKickVideoInfo("Sylvester", "Chiffon Cardigan", "40000", "http://qvc.scene7.com/is/image/QVC/pic/hp/tsv_20180216.jpg?qlt=95,1&$aemtsvimage$ ",
//                "http://qvc0.content.video.llnw.net/smedia/4826bb17d50c481b98a9e427e1c29c79/Y-/AwwQn_RUR5K0hivvTyecrE_Av5AHFfD0YaJ-RbrYU/a301149-18021616.mp4");
//        DabKickVideoInfo detail0 = new DabKickVideoInfo("Sylvester", "Vitamix 16-in-1", "40000","https://www.superfood.nl/media/catalog/product/cache/2/image/9df78eab33525d08d6e5fb8d27136e95/v/i/vitamix_5200_blender-high_speed-red.jpg",
//                "http://qvc0.content.video.llnw.net/smedia/4826bb17d50c481b98a9e427e1c29c79/hp/uLr2CRCk2kuQ_5zlQmWeYspJYmmwWRgZLO71eEVdI/ppe-k46761-vitamix-explorian-blender.mp4");
        DabKickVideoInfo detail1 = new DabKickVideoInfo("Sylvester", "Mission Impossible", "40000", "https://urlzs.com/tvzv",
                "https://r5---sn-ci5gup-cags.googlevideo.com/videoplayback?id=o-ANkQtWCS1ZD4mC7rfkY5xOl15afH1BwdzqMwuSbn5MSk&itag=22&source=youtube&requiressl=yes&mm=31%2C29&mn=sn-ci5gup-cags%2Csn-ci5gup-h556&ms=au%2Crdu&mv=m&pl=20&ei=txTRXOGSNoW7oAOEqZeICw&initcwndbps=908750&mime=video%2Fmp4&ratebypass=yes&dur=701.521&lmt=1550993636389035&mt=1557206053&fvip=5&c=WEB&txp=5535432&ip=122.171.193.17&ipbits=0&expire=1557227800&sparams=ip%2Cipbits%2Cexpire%2Cid%2Citag%2Csource%2Crequiressl%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cei%2Cinitcwndbps%2Cmime%2Cratebypass%2Cdur%2Clmt&signature=381F8CFEB8D9BD1D56C515E3316E87E8D5A3892C.831153CBEF03FC85E6B0BFE7780037B96370E282&key=yt8");
        DabKickVideoInfo detail11 = new DabKickVideoInfo("Sylvester", "Mission Impossible", "40000", "https://urlzs.com/tvzv",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4");
        DabKickVideoInfo detail2 = new DabKickVideoInfo("Sylvester", "Elephants Dream", "40000", "https://www.transformingthechurch.org/elephants_dream_title_658w.jpg",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        DabKickVideoInfo detail3 = new DabKickVideoInfo("Sylvester", "Bull Run", "40000", "https://s.hdnux.com/photos/26/01/16/5772198/6/920x920.jpg",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4");
        DabKickVideoInfo detail4 = new DabKickVideoInfo("Sylvester", "Smoking Tire", "40000", "https://i.ytimg.com/vi/KyQAub_iLP4/maxresdefault.jpg",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4");
//        DabKickVideoInfo detail5 = new DabKickVideoInfo("Sylvester", "Mission Impos. Trailer", "40000", "https://vignette.wikia.nocookie.net/missionimpossible/images/d/da/Slider-TV.jpg/revision/latest/scale-to-width-down/590?cb=20110713140930",
//                "http://video.dailymail.co.uk/video/1418450360/2014/10/1418450360_3860569858001_Mission-Impossible-1-Trailer.mp4");
//        DabKickVideoInfo detail6 = new DabKickVideoInfo("Sylvester", "Soccer Highlights", "40000", "https://www.mercurynews.com/wp-content/uploads/2017/04/sjm-quakes-0307-002.jpg?w=452",
//                "https://manifest.prod.boltdns.net/manifest/v1/hls/v5/clear/5530036774001/134ce722-22c4-44af-a8a7-111300e4867c/10s/master.m3u8?fastly_token=NWM1ZGQ3YzlfNDA2ODU0NmY1NjIyZTkyNGQxNjk3NThjYjNmYmJmMDY4OTk5ZjRmMzc5OTM2NDlmZDM2OWE0NGY1MzczYzQxNQ%3D%3D");
        DabKickVideoInfo detail7 = new DabKickVideoInfo("Sylvester", "Football Highlights", "40000", "http://img.bleacherreport.net/img/images/photos/003/052/463/df9e69c46443349f3ccc6905b2053094_crop_north.jpg?1410055466&w=630&h=420",
                "https://media.gannett-cdn.com/35553589001/35553589001_5642705023001_5642552128001.mp4");
        DabKickVideoInfo detail8 = new DabKickVideoInfo("Sylvester", "Elon @ TED", "40000", "https://pi.tedcdn.com/r/pl.tedcdn.com/social/ted-logo-fb.png?v=wAff13s",
                "https://download.ted.com/talks/ElonMusk_2017-600k.mp4");

        return new ArrayList<>(Arrays.asList(detail1, detail2, detail3, detail4, detail7, detail8));

//        DabKickVideoInfo detail1 = new DabKickVideoInfo("Sylvester", "Stars wars trailer", "40000", "http://i3.ytimg.com/vi/adzYW5DZoWs/hqdefault.jpg",
//                fullStreamUrlList.get(0));
//        DabKickVideoInfo detail2 = new DabKickVideoInfo("Sylvester", "Pokemon teaser trailer", "40000", "http://i3.ytimg.com/vi/198gzllaumo/hqdefault.jpg",
//                fullStreamUrlList.get(1));
//
//        DabKickVideoInfo detail3 = new DabKickVideoInfo("Sylvester", "Animated animal video", "40000", "http://i3.ytimg.com/vi/Uw5JOtvFd-k/hqdefault.jpg",
//                fullStreamUrlList.get(2));
//
//        DabKickVideoInfo detail4 = new DabKickVideoInfo("Sylvester", "Bradley Cooper and Lady Gaga performance", "40000", "http://i3.ytimg.com/vi/bo_efYhYU2A/hqdefault.jpg",
//                "");
//
//        DabKickVideoInfo detail5 = new DabKickVideoInfo("Sylvester", "2 minutes of video game", "40000", "http://i3.ytimg.com/vi/6hnRJ0j0Mqo/hqdefault.jpg",
//                fullStreamUrlList.get(3));
//
//        DabKickVideoInfo detail6 = new DabKickVideoInfo("Sylvester", "Video explaining block chain in 2 minutes", "40000", "http://i3.ytimg.com/vi/r43LhSUUGTQ/hqdefault.jpg",
//                fullStreamUrlList.get(4));
//
//        DabKickVideoInfo detail7 = new DabKickVideoInfo("Sylvester", "Time Lapse video of polar bear growth", "40000", "http://i3.ytimg.com/vi/IyXn4chXcqY/hqdefault.jpg",
//                fullStreamUrlList.get(5));
//
//        DabKickVideoInfo detail8 = new DabKickVideoInfo("Sylvester", "Quick curling (sport) guide", "40000", "http://i3.ytimg.com/vi/IOk9SVzqHsk/hqdefault.jpg",
//                fullStreamUrlList.get(6));
//
//        DabKickVideoInfo detail9 = new DabKickVideoInfo("Sylvester", "2 minute long exposure photo tip", "40000", "http://i3.ytimg.com/vi/sNKtuwSyMLs/hqdefault.jpg",
//                fullStreamUrlList.get(7));
//
//        DabKickVideoInfo detail10 = new DabKickVideoInfo("Sylvester", "Quick motivational video", "40000", "http://i3.ytimg.com/vi/FbDNVDXG5q8/hqdefault.jpg",
//                fullStreamUrlList.get(8));
//
//        DabKickVideoInfo detail11 = new DabKickVideoInfo("Sylvester", "Iphone trailer", "40000", "http://i3.ytimg.com/vi/iol8n3m88SA/hqdefault.jpg",
//                fullStreamUrlList.get(9));
//
//        DabKickVideoInfo detail12 = new DabKickVideoInfo("Sylvester", "History of Apple in 2 minutes", "40000", "http://i3.ytimg.com/vi/DQh-1N_inMc/hqdefault.jpg",
//                fullStreamUrlList.get(10));
//
//        return new ArrayList<>(Arrays.asList(detail1, detail2, detail3, detail4, detail5, detail6, detail7, detail8, detail9, detail10, detail11, detail12));
    }

}

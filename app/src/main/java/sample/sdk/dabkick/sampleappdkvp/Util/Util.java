package sample.sdk.dabkick.sampleappdkvp.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

/**
 * Created by iFocus on 11-06-2018.
 */

public class Util {

    private static Util instance;
    public ArrayList<String> categories;
    public Map<String, ArrayList<VideoItemDetail>> categoryMap;

    public static Util getInstance(){

        if (instance == null)
            instance = new Util();
        return instance;
    }

    public Util(){

        //Initialize category
        categories = new ArrayList<>();
        addCategories();

        //Initialize Map
        categoryMap = new ConcurrentHashMap<String, ArrayList<VideoItemDetail>>();
        initializeCategoryMap();

    }

    void addCategories(){

        //Add static values to category
        categories.add("Cartoon");
        categories.add("Sports");
        categories.add("Trailers");
        categories.add("Travel");
        categories.add("Technology");

    }

    void initializeCategoryMap(){

        categoryMap.put(categories.get(0), addCartoonVideoDetails());
        categoryMap.put(categories.get(1), addSportsVideoDetails());
        categoryMap.put(categories.get(2), addTrailersVideoDetails());
        categoryMap.put(categories.get(3), addTravelVideoDetails());
        categoryMap.put(categories.get(4), addTechnologyVideoDetails());
    }


    ArrayList<VideoItemDetail> addCartoonVideoDetails(){

        VideoItemDetail mrBean = new VideoItemDetail("https://www.youtube.com/watch?v=aepV91_3t2c", "https://img.youtube.com/vi/aepV91_3t2c/0.jpg",
                "Mr Bean Cartoon", "30:16",
                "Mr. Bean is a British sitcom created by Rowan Atkinson and Richard Curtis, produced by Tiger Aspect Productions, and starring Atkinson as the title character. The sitcom consisted of 15 episodes that were co-written by Atkinson, alongside Curtis and Robin Driscoll; for the pilot, it was co-written by Ben Elton. 14 of the episodes were broadcast on ITV, beginning with the pilot on 1 January 1990,[1] until \"The Best Bits of Mr. Bean\", a compilation episode, on 15 December 1995. The fourteenth episode, \"Hair by Mr. Bean of London\", was not broadcast on television, until 25 August 2006 on Nickelodeon\nCourtesy: Wiki");

        VideoItemDetail mrBean1 = new VideoItemDetail("https://www.youtube.com/watch?v=jRKV1OMkza8", "https://img.youtube.com/vi/jRKV1OMkza8/0.jpg",
                "Mr Bean Cartoon Full episodes", "22:13",
                "The character of Mr. Bean was developed while Rowan Atkinson was studying for his master's degree in electrical engineering at The Queen's College, Oxford. A sketch featuring Bean was performed at the Edinburgh Fringe in the early 1980s.[3] A similar character called Robert Box, played by Atkinson himself, appeared in the one-off 1979 ITV sitcom Canned Laughter, which also featured routines used in the feature film in 1997.\nCourtesy: Wiki");

        VideoItemDetail mrBean2 = new VideoItemDetail("https://www.youtube.com/watch?v=nC13_OLAlJ8", "https://img.youtube.com/vi/nC13_OLAlJ8/0.jpg",
                "Mr Bean Cartoon Season 2", "43:09",
                "Mr. Bean often seems unaware of basic aspects of the way the world works, and the programme usually features his attempts at what would normally be considered simple activities, such as going swimming, using a television set, interior decorating, or going to church. The humour largely comes from his original (and often absurd) solutions to problems and his total disregard for others when solving them, his pettiness, and occasional malevolence.\nCourtesy: Wiki");

        VideoItemDetail tomAndJerry = new VideoItemDetail("https://www.youtube.com/watch?v=o5rEqjOeKp0", "https://img.youtube.com/vi/o5rEqjOeKp0/0.jpg",
                "Solid Serenade", "07:29",
                "The series features comic fights between an iconic set of adversaries, a house cat (Tom) and a mouse (Jerry). The plots of each short usually center on Tom's numerous attempts to capture Jerry and the mayhem and destruction that follows. Tom rarely succeeds in catching Jerry, mainly because of Jerry's cleverness, cunning abilities, and luck. However, there are also several instances within the cartoons where they display genuine friendship and concern for each other's well-being. At other times, the pair set aside their rivalry in order to pursue a common goal, such as when a baby escaped the watch of a negligent babysitter, causing Tom and Jerry to pursue the baby and keep it away from danger.\nCourtesy: Wiki");

        VideoItemDetail tomAndJerry1 = new VideoItemDetail("https://www.youtube.com/watch?v=7mNu1DaTVDY", "https://img.youtube.com/vi/7mNu1DaTVDY/0.jpg",
                "Framed Cat", "07:12",
                "The series features comic fights between an iconic set of adversaries, a house cat (Tom) and a mouse (Jerry). The plots of each short usually center on Tom's numerous attempts to capture Jerry and the mayhem and destruction that follows. Tom rarely succeeds in catching Jerry, mainly because of Jerry's cleverness, cunning abilities, and luck. However, there are also several instances within the cartoons where they display genuine friendship and concern for each other's well-being. At other times, the pair set aside their rivalry in order to pursue a common goal, such as when a baby escaped the watch of a negligent babysitter, causing Tom and Jerry to pursue the baby and keep it away from danger.\nCourtesy: Wiki");

        VideoItemDetail tomAndJerry2 = new VideoItemDetail("https://www.youtube.com/watch?v=AGBjI0x9VbM", "https://img.youtube.com/vi/AGBjI0x9VbM/0.jpg",
                "Little Quacker", "25:59",
                "The series features comic fights between an iconic set of adversaries, a house cat (Tom) and a mouse (Jerry). The plots of each short usually center on Tom's numerous attempts to capture Jerry and the mayhem and destruction that follows. Tom rarely succeeds in catching Jerry, mainly because of Jerry's cleverness, cunning abilities, and luck. However, there are also several instances within the cartoons where they display genuine friendship and concern for each other's well-being. At other times, the pair set aside their rivalry in order to pursue a common goal, such as when a baby escaped the watch of a negligent babysitter, causing Tom and Jerry to pursue the baby and keep it away from danger.\nCourtesy: Wiki");

        VideoItemDetail flinstones = new VideoItemDetail("https://www.youtube.com/watch?v=3s-MnvKIqb8", "https://img.youtube.com/vi/3s-MnvKIqb8/0.jpg",
                "Wacky Inventions", "05:43",
                "The show's premise is that it is set in a comical, satirical version of the \"Stone Age\" which, in spite of using primitive technology, resembles mid-20th century suburban America. The plots deliberately resemble the sitcoms of the era, with the caveman Flintstone and Rubble families getting into minor conflicts characteristic of modern life. The show is set in the Stone Age town of Bedrock (pop. 2500). In this fantasy version of the past, dinosaurs and other long-extinct animals co-exist with cavemen, saber-toothed cats, and woolly mammoths. Like their mid-20th century counterparts, these cavemen listen to records, live in split-level homes, and eat at restaurants, yet their technology is made entirely from preindustrial materials and powered primarily through the use of animals. For example, the cars are made out of stone, wood, and animal skins, and powered by the passengers' feet.\nCourtesy: Wiki");

        VideoItemDetail flinstones1 = new VideoItemDetail("https://www.youtube.com/watch?v=mpwdCIxoc8Y", "https://img.youtube.com/vi/mpwdCIxoc8Y/0.jpg",
                "The Monsters", "04:00",
                "The show's premise is that it is set in a comical, satirical version of the \"Stone Age\" which, in spite of using primitive technology, resembles mid-20th century suburban America. The plots deliberately resemble the sitcoms of the era, with the caveman Flintstone and Rubble families getting into minor conflicts characteristic of modern life. The show is set in the Stone Age town of Bedrock (pop. 2500). In this fantasy version of the past, dinosaurs and other long-extinct animals co-exist with cavemen, saber-toothed cats, and woolly mammoths. Like their mid-20th century counterparts, these cavemen listen to records, live in split-level homes, and eat at restaurants, yet their technology is made entirely from preindustrial materials and powered primarily through the use of animals. For example, the cars are made out of stone, wood, and animal skins, and powered by the passengers' feet.\nCourtesy: Wiki");

        VideoItemDetail flinstones2 = new VideoItemDetail("https://www.youtube.com/watch?v=2s13X66BFd8", "https://img.youtube.com/vi/2s13X66BFd8/0.jpg",
                "Theme Song", "00:35",
                "The show's premise is that it is set in a comical, satirical version of the \"Stone Age\" which, in spite of using primitive technology, resembles mid-20th century suburban America. The plots deliberately resemble the sitcoms of the era, with the caveman Flintstone and Rubble families getting into minor conflicts characteristic of modern life. The show is set in the Stone Age town of Bedrock (pop. 2500). In this fantasy version of the past, dinosaurs and other long-extinct animals co-exist with cavemen, saber-toothed cats, and woolly mammoths. Like their mid-20th century counterparts, these cavemen listen to records, live in split-level homes, and eat at restaurants, yet their technology is made entirely from preindustrial materials and powered primarily through the use of animals. For example, the cars are made out of stone, wood, and animal skins, and powered by the passengers' feet.\nCourtesy: Wiki");

        VideoItemDetail flinstones3 = new VideoItemDetail("https://www.youtube.com/watch?v=MMC-EkI_LHc", "https://img.youtube.com/vi/MMC-EkI_LHc/0.jpg",
                "Hubby Responsibilities", "02:31",
                "The show's premise is that it is set in a comical, satirical version of the \"Stone Age\" which, in spite of using primitive technology, resembles mid-20th century suburban America. The plots deliberately resemble the sitcoms of the era, with the caveman Flintstone and Rubble families getting into minor conflicts characteristic of modern life. The show is set in the Stone Age town of Bedrock (pop. 2500). In this fantasy version of the past, dinosaurs and other long-extinct animals co-exist with cavemen, saber-toothed cats, and woolly mammoths. Like their mid-20th century counterparts, these cavemen listen to records, live in split-level homes, and eat at restaurants, yet their technology is made entirely from preindustrial materials and powered primarily through the use of animals. For example, the cars are made out of stone, wood, and animal skins, and powered by the passengers' feet.\nCourtesy: Wiki");

        return new ArrayList<>(Arrays.asList(mrBean, mrBean1, mrBean2, tomAndJerry, tomAndJerry1, tomAndJerry2, flinstones,
                flinstones1, flinstones2, flinstones3));

    }

    ArrayList<VideoItemDetail> addSportsVideoDetails(){

        VideoItemDetail squash = new VideoItemDetail("https://www.youtube.com/watch?v=Lbdp-HCjWw8", "https://img.youtube.com/vi/Lbdp-HCjWw8/0.jpg",
                "Learn Squash", "01:13",
                "Squash was invented in Harrow School out of the older game rackets around 1830 before the game spread to other schools, eventually becoming an international sport. The first courts built at this school were rather dangerous because they were near water pipes, buttresses, chimneys, and ledges. The school soon built four outside courts. Natural rubber was the material of choice for the ball. Students modified their rackets to have a smaller reach to play in these cramped conditions.\nCourtesy: Wiki");

        VideoItemDetail squash1 = new VideoItemDetail("https://www.youtube.com/watch?v=55qPnBRc2t0", "https://img.youtube.com/vi/55qPnBRc2t0/0.jpg",
                "Squash Guide", "08:40",
                "Squash was invented in Harrow School out of the older game rackets around 1830 before the game spread to other schools, eventually becoming an international sport. The first courts built at this school were rather dangerous because they were near water pipes, buttresses, chimneys, and ledges. The school soon built four outside courts. Natural rubber was the material of choice for the ball. Students modified their rackets to have a smaller reach to play in these cramped conditions.\nCourtesy: Wiki");

        VideoItemDetail squash2 = new VideoItemDetail("https://www.youtube.com/watch?v=LgoGcCeyTFo", "https://img.youtube.com/vi/LgoGcCeyTFo/0.jpg",
                "Basics of Squash", "02:23",
                "Squash was invented in Harrow School out of the older game rackets around 1830 before the game spread to other schools, eventually becoming an international sport. The first courts built at this school were rather dangerous because they were near water pipes, buttresses, chimneys, and ledges. The school soon built four outside courts. Natural rubber was the material of choice for the ball. Students modified their rackets to have a smaller reach to play in these cramped conditions.\nCourtesy: Wiki");

        VideoItemDetail tennis = new VideoItemDetail("https://www.youtube.com/watch?v=1P_Das6OjAk", "https://img.youtube.com/vi/1P_Das6OjAk/0.jpg",
                "Rules of tennis", "04:04",
                "Tennis is a racket sport that can be played individually against a single opponent (singles) or between two teams of two players each (doubles). Each player uses a tennis racket that is strung with cord to strike a hollow rubber ball covered with felt over or around a net and into the opponent's court. The object of the game is to maneuver the ball in such a way that the opponent is not able to play a valid return. The player who is unable to return the ball will not gain a point, while the opposite player will.\nCourtesy: Wiki");

        VideoItemDetail tennis1 = new VideoItemDetail("https://www.youtube.com/watch?v=WRbsVyxp09Y", "https://img.youtube.com/vi/WRbsVyxp09Y/0.jpg",
                "Forehand and Backhand", "13:08",
                "Tennis is a racket sport that can be played individually against a single opponent (singles) or between two teams of two players each (doubles). Each player uses a tennis racket that is strung with cord to strike a hollow rubber ball covered with felt over or around a net and into the opponent's court. The object of the game is to maneuver the ball in such a way that the opponent is not able to play a valid return. The player who is unable to return the ball will not gain a point, while the opposite player will.\nCourtesy: Wiki");

        VideoItemDetail tennis2 = new VideoItemDetail("https://www.youtube.com/watch?v=CXgfNBnetzQ", "https://img.youtube.com/vi/CXgfNBnetzQ/0.jpg",
                "Serve in Tennis", "01:13",
                "Tennis is a racket sport that can be played individually against a single opponent (singles) or between two teams of two players each (doubles). Each player uses a tennis racket that is strung with cord to strike a hollow rubber ball covered with felt over or around a net and into the opponent's court. The object of the game is to maneuver the ball in such a way that the opponent is not able to play a valid return. The player who is unable to return the ball will not gain a point, while the opposite player will.\nCourtesy: Wiki");

        VideoItemDetail squashAndTennis = new VideoItemDetail("https://www.youtube.com/watch?v=cykPF6D6iwk", "https://img.youtube.com/vi/cykPF6D6iwk/0.jpg",
                "Difference between Squash and Tennis", "15:24",
                "While tennis and squash share certain similarities, both sports also have a number of differences. The biggest difference between the two sports are the courts. Squash courts feature walls that players hit the ball towards, whereas tennis courts are typically a rectangular, flat surface that is not enclosed.\nCourtesy: Google");

        VideoItemDetail baseball = new VideoItemDetail("https://www.youtube.com/watch?v=I8VGW0C_GO4", "https://img.youtube.com/vi/I8VGW0C_GO4/0.jpg",
                "Baseball Explained", "01:05",
                "Baseball is a bat-and-ball game played between two opposing teams who take turns batting and fielding. The game proceeds when a player on the fielding team, called the pitcher, throws a ball which a player on the batting team tries to hit with a bat. The objectives of the offensive team (batting team) are to hit the ball into the field of play, and to run the bases—having its runners advance counter-clockwise around four bases to score what are called \"runs\". The objective of the defensive team (fielding team) is to prevent batters from becoming runners, and to prevent runners' advance around the bases.[1] A run is scored when a runner legally advances around the bases in order and touches home plate (the place where the player started as a batter). The team that scores the most runs by the end of the game is the winner.\nCourtesy: Wiki");

        VideoItemDetail baseball1 = new VideoItemDetail("https://www.youtube.com/watch?v=A2QuQAHpElk", "https://img.youtube.com/vi/A2QuQAHpElk/0.jpg",
                "Baseball Basics", "06:57",
                "Baseball is a bat-and-ball game played between two opposing teams who take turns batting and fielding. The game proceeds when a player on the fielding team, called the pitcher, throws a ball which a player on the batting team tries to hit with a bat. The objectives of the offensive team (batting team) are to hit the ball into the field of play, and to run the bases—having its runners advance counter-clockwise around four bases to score what are called \"runs\". The objective of the defensive team (fielding team) is to prevent batters from becoming runners, and to prevent runners' advance around the bases.[1] A run is scored when a runner legally advances around the bases in order and touches home plate (the place where the player started as a batter). The team that scores the most runs by the end of the game is the winner.\nCourtesy: Wiki");

        VideoItemDetail baseball2 = new VideoItemDetail("https://www.youtube.com/watch?v=skOsApsF0jQ", "https://img.youtube.com/vi/skOsApsF0jQ/0.jpg",
                "Baseball Rules", "03:21",
                "Baseball is a bat-and-ball game played between two opposing teams who take turns batting and fielding. The game proceeds when a player on the fielding team, called the pitcher, throws a ball which a player on the batting team tries to hit with a bat. The objectives of the offensive team (batting team) are to hit the ball into the field of play, and to run the bases—having its runners advance counter-clockwise around four bases to score what are called \"runs\". The objective of the defensive team (fielding team) is to prevent batters from becoming runners, and to prevent runners' advance around the bases.[1] A run is scored when a runner legally advances around the bases in order and touches home plate (the place where the player started as a batter). The team that scores the most runs by the end of the game is the winner.\nCourtesy: Wiki");

        return new ArrayList<>(Arrays.asList(squash, squash1, squash2, tennis, tennis1, tennis2, squashAndTennis,
                baseball, baseball1, baseball2));

    }

    ArrayList<VideoItemDetail> addTrailersVideoDetails(){

        VideoItemDetail bumbleBee = new VideoItemDetail("https://www.youtube.com/watch?v=fAIX12F6958", "https://img.youtube.com/vi/fAIX12F6958/0.jpg",
                "Bumble bee", "02:22",
                "Bumblebee is an upcoming American science fiction drama film adventure film centered around the Transformers' character of the same name. It is the sixth installment of the live-action Transformers film series and a prequel to 2007's Transformers. Directed by Travis Knight and written by Christina Hodson, the film stars Hailee Steinfeld, John Cena, Jorge Lendeborg Jr., Jason Drucker, Kenneth Choi, Gracie Dzienny, Rachel Crow, and Pamela Adlon. It is also the first Transformers film to not be directed by Michael Bay, though he will still act as producer.\nCourtesy: Wiki");

        VideoItemDetail raazi = new VideoItemDetail("https://www.youtube.com/watch?v=YjMSttRJrhA", "https://img.youtube.com/vi/YjMSttRJrhA/0.jpg",
                "Raazi", "02:21",
                "Raazi (lit. Agree, Hindi pronunciation: [ɾaːziː]) is a 2018 Indian spy thriller film directed by Meghna Gulzar[4] and produced by Vineet Jain, Karan Johar, Hiroo Yash Johar and Apoorva Mehta under the banners of Dharma Productions and Junglee Pictures. It stars Alia Bhatt and features Vicky Kaushal, Rajit Kapur, Shishir Sharma, and Jaideep Ahlawat in supporting roles.[5][6] The film is an adaptation of Harinder Sikka’s novel Calling Sehmat which is inspired by real events.[7][8] It is about an Indian spy (Bhatt) married to a Pakistani military officer (Kaushal) prior to the Indo-Pakistani War of 1971 on the order of her father (Kapur).\nCourtesy: Wiki");

        VideoItemDetail wedding = new VideoItemDetail("https://www.youtube.com/watch?v=IZODr96ZRCc", "https://img.youtube.com/vi/IZODr96ZRCc/0.jpg",
                "Veere Di Wedding", "02:49",
                "Veere Di Wedding (English: Friend's Wedding) is a 2018 Indian Hindi-language female buddy comedy film, directed by Shashanka Ghosh, co-produced by Rhea Kapoor, Ekta Kapoor and Nikhil Dwivedi. It stars Kareena Kapoor Khan, Sonam Kapoor, Swara Bhaskar and Shikha Talsania in lead roles, as four friends attending a wedding.[2] The film released theatrically on 1 June 2018 and has earned over ₹118 crore (US$18 million) worldwide.[1] The film received mixed reviews from critics, with praise towards the chemistry and performances of the cast.\nCourtesy: Wiki");

        VideoItemDetail jurassic = new VideoItemDetail("https://www.youtube.com/watch?v=vn9mMeWcgoM", "https://img.youtube.com/vi/vn9mMeWcgoM/0.jpg",
                "Jurassic World", "02:26",
                "Jurassic World: Fallen Kingdom is a 2018 American science fiction adventure film directed by J. A. Bayona. The film is the sequel to Jurassic World (2015) and is the fifth installment of the Jurassic Park film series, as well as the second installment of a planned Jurassic World trilogy. The film features Derek Connolly and Jurassic World director Colin Trevorrow both returning as writers, with Trevorrow and original Jurassic Park director Steven Spielberg acting as executive producers. Set on the fictional island of Isla Nublar, off Central America's pacific coast, the plot follows Owen Grady and Claire Dearing as they rescue the remaining dinosaurs on the island before a volcanic eruption destroys it. Chris Pratt, Bryce Dallas Howard, B. D. Wong, and Jeff Goldblum reprise their roles from previous films in the series, with Rafe Spall, Justice Smith, Daniella Pineda, James Cromwell, Toby Jones, Ted Levine, Isabella Sermon, and Geraldine Chaplin joining the cast.\nCourtesy: Wiki");

        VideoItemDetail robinhood = new VideoItemDetail("https://www.youtube.com/watch?v=tJfDBSWYqU8", "https://img.youtube.com/vi/tJfDBSWYqU8/0.jpg",
                "Robin Hood", "02:21",
                "Robin of Loxley (Taron Egerton) a war-hardened Crusader and his Moorish commander (Jamie Foxx) mount an audacious revolt against the corrupt English crown in a thrilling action-adventure packed with gritty battlefield exploits, mind-blowing fight choreography, and a timeless romance.\nCourtesy: Youtube");

        VideoItemDetail race3 = new VideoItemDetail("https://www.youtube.com/watch?v=cFsgpZbMrcQ", "https://img.youtube.com/vi/cFsgpZbMrcQ/0.jpg",
                "Race 3", "03:31",
                "Race 3 is an upcoming Indian Hindi-language action thriller film directed by Remo D'Souza and produced under Tips Films and Salman Khan Films. The film features Anil Kapoor, Salman Khan, Bobby Deol, Jacqueline Fernandez, Daisy Shah, Saqib Saleem and Freddy Daruwala. It is the third installment of Race film series. The film is set to release on 15 June 2018 coinciding with Eid.\nCourtesy: Wiki");

        VideoItemDetail spiderman = new VideoItemDetail("https://www.youtube.com/watch?v=g4Hbz2jLxvQ", "https://img.youtube.com/vi/g4Hbz2jLxvQ/0.jpg",
                "Spiderman", "02:40",
                "Phil Lord and Christopher Miller, the creative minds behind The Lego Movie and 21 Jump Street, bring their unique talents to a fresh vision of a different Spider-Man Universe, with a groundbreaking visual style that’s the first of its kind.  Spider-Man™: Into the Spider-Verse introduces Brooklyn teen Miles Morales, and the limitless possibilities of the Spider-Verse, where more than one can wear the mask.\nCourtesy: Youtube");

        VideoItemDetail kaala = new VideoItemDetail("https://www.youtube.com/watch?v=mMCEvr3VWqQ", "https://img.youtube.com/vi/mMCEvr3VWqQ/0.jpg",
                "kaala", "01:30",
                "Kaala is a 2018 Indian Tamil-language action drama film [3][4] written and directed by Pa. Ranjith and produced by Dhanush under his banner Wunderbar Films. Starring Rajinikanth in the lead role,[5][6] the film was announced in 2016. The film was earlier scheduled to be released on 27 April 2018 but was postponed to June due to the standoff between Nadigar Sangam and Digital Service Providers on the increase of virtual print fee charges along with the 2018 Tamil Nadu protests for Kaveri water sharing issue which also led to the delay in release of other Tamil films.[7][8] Kaala premiered in Malaysia on 6 June 2018,[1] followed by a release in 1,800 theatres in India on 7 June 2018.\nCourtesy: Wiki");

        VideoItemDetail bigLegend = new VideoItemDetail("https://www.youtube.com/watch?v=FeAuKeB70mM", "https://img.youtube.com/vi/FeAuKeB70mM/0.jpg",
                "Big Legend", "02:22",
                "An ex-soldier ventures into the Pacific Northwest to uncover the truth behind his fiance's disappearance.\nCourtesy: Youtube");

        VideoItemDetail sanju = new VideoItemDetail("https://www.youtube.com/watch?v=1J76wN0TPI4", "https://img.youtube.com/vi/1J76wN0TPI4/0.jpg",
                "Sanju", "03:04",
                "Sanju is an upcoming Indian biographical film based on the life of Indian actor Sanjay Dutt, directed and written by Rajkumar Hirani and produced by Vidhu Vinod Chopra.[1][2] The film stars an ensemble cast with Ranbir Kapoor playing the role of Sanjay Dutt in various stages of his life.[3] The film is set to be released on 29 June 2018.\nCourtesy: Wiki");

        return new ArrayList<>(Arrays.asList(bumbleBee, raazi, wedding, jurassic, robinhood, race3, spiderman,
                kaala, bigLegend, sanju));

    }

    ArrayList<VideoItemDetail> addTravelVideoDetails(){

        VideoItemDetail days = new VideoItemDetail("https://www.youtube.com/watch?v=RcmrbNRK-jY", "https://img.youtube.com/vi/RcmrbNRK-jY/0.jpg",
                "200 days", "23:24",
                "Inspired by Jules Verne since his childhood, Muammer dreamed of completing a world tour. In 2014, with Milan Bihlmann, he founded the Optimistic Traveler association.[6] The association's mission was to prove that generosity and human goodness are universal. The founders pledged to complete a world tour in 80 days without access to money and other common resources. Muammer and Milan traveled more than 47,000 kilometers through 19 different countries,[2] including France, Germany, Austria, Hungary, Romania, Bulgaria, Turkey, Iran, Pakistan, India, Thailand, Malaysia, Singapore, the United States, Morocco, and Spain. According to Muammer, traveling without money is \"the best way to meet people\".[7] The two friends had to depend on the help of people they met to eat, sleep, and travel. According to Muammer and Milan, this approach removes fear of the unknown and demonstrates that people can count on others, regardless of their nationality, religion, or social status. The story of this journey, Around the World in 80 Days Without a Cent (Le tour du monde en 80 jours, sans argent) was published in 2015 by editions Michel Lafon. The author's proceeds from the book are being used for humanitarian support in Haiti, with help from the Haïti Care association.\nCourtesy: Wiki");

        VideoItemDetail noMoney = new VideoItemDetail("https://www.youtube.com/watch?v=R7vmHGAshi8", "https://img.youtube.com/vi/R7vmHGAshi8/0.jpg",
                "Tavel world with no money", "18:18",
                "Inspired by Jules Verne since his childhood, Muammer dreamed of completing a world tour. In 2014, with Milan Bihlmann, he founded the Optimistic Traveler association.[6] The association's mission was to prove that generosity and human goodness are universal. The founders pledged to complete a world tour in 80 days without access to money and other common resources. Muammer and Milan traveled more than 47,000 kilometers through 19 different countries,[2] including France, Germany, Austria, Hungary, Romania, Bulgaria, Turkey, Iran, Pakistan, India, Thailand, Malaysia, Singapore, the United States, Morocco, and Spain. According to Muammer, traveling without money is \"the best way to meet people\".[7] The two friends had to depend on the help of people they met to eat, sleep, and travel. According to Muammer and Milan, this approach removes fear of the unknown and demonstrates that people can count on others, regardless of their nationality, religion, or social status. The story of this journey, Around the World in 80 Days Without a Cent (Le tour du monde en 80 jours, sans argent) was published in 2015 by editions Michel Lafon. The author's proceeds from the book are being used for humanitarian support in Haiti, with help from the Haïti Care association.\nCourtesy: Wiki");

        VideoItemDetail travelDest = new VideoItemDetail("https://www.youtube.com/watch?v=jkEmvP6ih48", "https://img.youtube.com/vi/jkEmvP6ih48/0.jpg",
                "Best Travel Destinations", "17:24",
                "Natural beauty such as beaches, tropical island resorts with coral reefs, hiking and camping in national parks, mountains, deserts and forests, are examples of traditional tourist attractions to spend summer vacations. Other examples of cultural tourist attractions include historical places, monuments, ancient temples, zoos, aquaria, museums and art galleries, botanical gardens, buildings and structures (e.g., castles, libraries, former prisons, skyscrapers, bridges), theme parks and carnivals, living history museums, signs, ethnic enclave communities, historic trains and cultural events. Factory tours, industrial heritage, creative art and crafts workshops are the object of cultural niches like industrial tourism and creative tourism. Many tourist attractions are also landmarks.\nCourtesy: Wiki");

        VideoItemDetail travelBlogger = new VideoItemDetail("https://www.youtube.com/watch?v=7ByoBJYXU0k", "https://img.youtube.com/vi/7ByoBJYXU0k/0.jpg",
                "Travel Blogger", "01:19",
                "In the 21st century, travel literature became a genre of social media in the form of travel blogs, with travel bloggers using outlets like personal blogs, Pinterest, Twitter, Facebook and Instagram to convey information about their adventures, and provide advice for navigating particular countries, or for traveling generally.[37] Travel blogs were among the first instances of blogging, which began in the mid 1990s.\nCourtesy: Wiki");

        VideoItemDetail beginners = new VideoItemDetail("https://www.youtube.com/watch?v=go4wo4WenQQ", "https://img.youtube.com/vi/go4wo4WenQQ/0.jpg",
                "Travel Blog for Beginners", "04:27",
                "In the 21st century, travel literature became a genre of social media in the form of travel blogs, with travel bloggers using outlets like personal blogs, Pinterest, Twitter, Facebook and Instagram to convey information about their adventures, and provide advice for navigating particular countries, or for traveling generally.[37] Travel blogs were among the first instances of blogging, which began in the mid 1990s.\nCourtesy: Wiki");

        VideoItemDetail sriLanka = new VideoItemDetail("https://www.youtube.com/watch?v=epmQYVLvnjU", "https://img.youtube.com/vi/epmQYVLvnjU/0.jpg",
                "Tavel guide to SriLanka", "13:35",
                "Sri Lanka's documented history spans 3,000 years, with evidence of pre-historic human settlements dating back to at least 125,000 years.[14] It has a rich cultural heritage and the first known Buddhist writings of Sri Lanka, the Pāli Canon, date back to the Fourth Buddhist council in 29 BC.[15][16] Its geographic location and deep harbours made it of great strategic importance from the time of the ancient Silk Road through to the modern Maritime Silk Road.\nCourtesy: Wiki");

        VideoItemDetail gadgets = new VideoItemDetail("https://www.youtube.com/watch?v=pDvZtVBXDXw", "https://img.youtube.com/vi/pDvZtVBXDXw/0.jpg",
                "Tavel gadgets", "11:04",
                " 6 Cool Travel Gadgets 2018 Links:-\n" +
                        "\n" +
                        "1- The Everyday Dopp: Effortlessly Organize Everything .... https://getsupply.com\n" +
                        "We set out to design the world's best dopp kit - and ended up designing the world's best everyday carry case in the process.\n" +
                        "\n" +
                        "2- 01:57 Xcale: The Perfect Travel Companion ... https://kck.st/2sJyqwh\n" +
                        "An expandable scale that replaces all your scales and security devices.\n" +
                        "\n" +
                        "3- 03:33 ProGo 2.0: Best Travel Backpack & Camera bag Ever ... https://progogear.com\n" +
                        "A Carry-on / Backpack with removable shelf, dedicated shoes compartment with anti loss device. Also a Camera bag with DSLR divider.\n" +
                        "\n" +
                        "4- 05:17 Tropic - The Ultimate Travel Shoe ... https://tropicfeel.com\n" +
                        "Made for exploring everything, from New York City to the Galapagos: Versatile | Quick Drying | High Tech | Anti-Odor.\n" +
                        "\n" +
                        "5- 07:05 THE POWER PACKER ... http://bit.ly/2HA7R0U\n" +
                        "Made for tech, made to move! The new slim pack for all your power and tech essentials.\n" +
                        "\n" +
                        "6- 08:20 SHAPL Dr.Nah_New Luggage & Backpack ... https://www.shapl.com\n" +
                        "Would you like to meet cool products with amazing design at affordable prices? Check out SHAPL Dr. Nah.\nCourtesy:Youtube");

        VideoItemDetail switerzland  = new VideoItemDetail("https://www.youtube.com/watch?v=QxP1p3EzOks", "https://img.youtube.com/vi/QxP1p3EzOks/0.jpg",
                "Swiz Travel guide", "15:07",
                "Switzerland (/ˈswɪtsərlənd/), officially the Swiss Confederation, is a sovereign state in Europe. It consists of 26 cantons, and the city of Bern is the seat of the federal authorities.[1][2][note 1] The federal republic is situated in Western-Central Europe,[note 4] and is bordered by Italy to the south, France to the west, Germany to the north, and Austria and Liechtenstein to the east. Switzerland is a landlocked country geographically divided between the Alps, the Swiss Plateau and the Jura, spanning a total area of 41,285 km2 (15,940 sq mi) (land area 39,997 km2 (15,443 sq mi)). While the Alps occupy the greater part of the territory, the Swiss population of approximately eight million people is concentrated mostly on the plateau, where the largest cities are to be found: among them are the two global cities and economic centres Zürich and Geneva.\nCourtesy: Wiki");

        VideoItemDetail bali = new VideoItemDetail("https://www.youtube.com/watch?v=i9E_Blai8vk", "https://img.youtube.com/vi/i9E_Blai8vk/0.jpg",
                "Bali Travel guide", "09:31",
                "Bali (Indonesian: Pulau Bali, Provinsi Bali) is an island and province of Indonesia with the biggest Hindu population. The province includes the island of Bali and a few smaller neighbouring islands, notably Nusa Penida, Nusa Lembongan and Nusa Ceningan. It is located at the westernmost end of the Lesser Sunda Islands, with Java to the west and Lombok to the east. Its capital, Denpasar, is located in the southern part of the island.\nCourtesy: Wiki");

        VideoItemDetail travelWords = new VideoItemDetail("https://www.youtube.com/watch?v=ikp4mMypuNk", "https://img.youtube.com/vi/ikp4mMypuNk/0.jpg",
                "100 Travel words", "24:57",
                "Fanny teaches 100 common English travel words. Learn the English pronunciation of these words in this practice drill.\n" +
                        "\n" +
                        "This is an excellent video to watch, repeat, and practice the English pronunciation of these words.\nCourtesy:Youtube");

        return new ArrayList<>(Arrays.asList(days, noMoney, travelDest, travelBlogger, beginners, sriLanka, switerzland,
                bali, gadgets, travelWords));

    }

    ArrayList<VideoItemDetail> addTechnologyVideoDetails(){

        VideoItemDetail sec = new VideoItemDetail("https://www.youtube.com/watch?v=-6ZbrfSRWKc", "https://img.youtube.com/vi/-6ZbrfSRWKc/0.jpg",
                "Security Engineer at Google", "02:04",
                "As a Security Engineer, you help protect network boundaries, keep computer systems and network devices hardened against attacks and provide security services to protect highly sensitive data like passwords and customer information. Learn about our work, team culture, and what makes security engineering at Google so exciting.\nCourtesy: Youtube");

        VideoItemDetail github = new VideoItemDetail("https://www.youtube.com/watch?v=w3jLJU7DT5E", "https://img.youtube.com/vi/w3jLJU7DT5E/0.jpg",
                "GitHub Explained", "03:45",
                "Ever wondered how GitHub works? Let's see how Eddie and his team use GitHub.\nCourtesy:Youtube");

        VideoItemDetail readmeImages = new VideoItemDetail("https://www.youtube.com/watch?v=hHbWF1Bvgf4", "https://img.youtube.com/vi/hHbWF1Bvgf4/0.jpg",
                "GitHub Readme Images Tutorial", "03:50",
                "Adding pictures to your GitHub readmes is a great way to highlight a feature and make it more professional.\n" +
                        "\n" +
                        "-Create an images/ or pictures/ folder in your repo\n" +
                        "-use relative paths to insert a markdown image\n" +
                        "![](images/you-picture.png)\n" +
                        "\n" +
                        "GitHub doesn't support markdown image sizing, but DOES allow the HTML img tag\n" +
                        "❮img src=\"images/you-picture.png\" ❯\n" +
                        "Then you can add width and height attributes\n" +
                        "❮img src=\"images/you-picture.png\" width=\"100\" ❯\n" +
                        "-Only specify 1 attribute to maintain the aspect ratio\nCourtesy:Youtube");

        VideoItemDetail readmeEss = new VideoItemDetail("https://www.youtube.com/watch?v=RZ5vduluea4", "https://img.youtube.com/vi/RZ5vduluea4/0.jpg",
                "Readme Essentials", "03:56",
                "The basics of writing a project README in Markdown.\nCourtesy:Youtube");

            VideoItemDetail beginners = new VideoItemDetail("https://www.youtube.com/watch?v=0fKg7e37bQE", "https://img.youtube.com/vi/0fKg7e37bQE/0.jpg",
                "GitHub for Beginners", "18:53",
                    "Github Tutorial For Beginners - learn Github for Mac or Github for windows\n" +
                            "If you've been wanting to learn Github, now's the perfect time!  Github is seen as a big requirement by most employers these days and is very critical to business workflow.  This Github tutorial will cover the basics of how to use Github and the command line.\nCourtesy:Youtube");

        VideoItemDetail acquisition = new VideoItemDetail("https://www.youtube.com/watch?v=m164XggdRGA", "https://img.youtube.com/vi/m164XggdRGA/0.jpg",
                "GitHub Acquisition", "08:37",
                "Microsoft CEO Satya Nadella speaks to \"Squawk Alley\" about the acquisition of GitHub, data security and the outlook for new M&A opportunities.\nCourtesy:Youtube");

        VideoItemDetail IoT = new VideoItemDetail("https://www.youtube.com/watch?v=UrwbeOIlc68", "https://img.youtube.com/vi/UrwbeOIlc68/0.jpg",
                "IoT", "01:01:24",
                "This video will help you grasp the basic concepts of Internet of Things & explains, how IoT is trying to revolutionize the world. This IoT tutorial video helps you to learn following topics:\n" +
                        "\n" +
                        "1. What is Internet of Things\n" +
                        "2. Why do we need Internet of Things\n" +
                        "3. Benefits of Internet of Things\n" +
                        "4. IoT features\n" +
                        "5. IoT Demo - Weather Station application using Raspberry Pi and Sense Hat\nCourtesy:Youtube");

        VideoItemDetail IotWorking  = new VideoItemDetail("https://www.youtube.com/watch?v=QSIPNhOiMoE", "https://img.youtube.com/vi/QSIPNhOiMoE/0.jpg",
                "IoT Working", "03:38",
                "The Internet of Things gives us access to the data from millions of devices. But how does it work, and what can we do with all that data?\nCourtesy:Youtube");

        VideoItemDetail amazonGo = new VideoItemDetail("https://www.youtube.com/watch?v=NrmMk1Myrxc", "https://img.youtube.com/vi/NrmMk1Myrxc/0.jpg",
                "Amazon Go", "01:49",
                "Now open in Seattle! Amazon Go is a new kind of store featuring the world’s most advanced shopping technology. No lines, no checkout – just grab and go!\nCourtesy:Youtube");

        VideoItemDetail best5 = new VideoItemDetail("https://www.youtube.com/watch?v=Qu9RhxUYc5s", "https://img.youtube.com/vi/Qu9RhxUYc5s/0.jpg",
                "Best 5 of 2018", "07:48",
                "Top 5 technologies to learn in 2018.\n" +
                        "\n" +
                        "Technologies are :\n" +
                        "IoT (Internet of Things)\n" +
                        "Cloud Computing\n" +
                        "Blockchain\n" +
                        "Artificial Intelligence\n" +
                        "Big Data\nCourtesy:Youtube");

        return new ArrayList<>(Arrays.asList(sec, github, readmeImages, readmeEss, beginners, acquisition, IoT,
                IotWorking, amazonGo, best5));

    }
}

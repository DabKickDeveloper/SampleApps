package sample.sdk.dabkick.livechat;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;
    public ArrayList<ChatRoom> listOfRooms;
    private static int mCurrentItem;
    private Activity mContext;

    public ChatRoomPagerAdapter(FragmentManager fragmentManager, AppCompatActivity context) {
        super(fragmentManager);
        this.mContext = context;
        populateRooms();
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public Fragment getItem(int position) {
        return ChatRoomFragment.newInstance(position);
    }

    public static int getCurrentItem() {
        return mCurrentItem;
    }

    public static void setCurrentItem(int mCurrentItem) {
        ChatRoomPagerAdapter.mCurrentItem = mCurrentItem;
    }

    public void populateRooms() {
        if (mContext.getClass() == HomepageActivity.class) {
            this.listOfRooms = new ArrayList<>();

            String[] roomNames = ((HomepageActivity) mContext).getResources().getStringArray(R.array.roomNames);
            String[] roomDesc = {"The young vital force of Coucou Chloe has relocated to London from Paris. Tune into her monthly hour-long slot for hi-tech emotive mixes channeling angelic and demonic energies...",
                    "Damon Eliza Palermo's \"Inner Realm\" emplores ambient, new age, krautrock \\u0026 all things out with occasional guests broadcasted live from NTS Los Angeles",
                    "Host Sasha Ali presents women-powered musical selections + artist interviews exploring women's roles as music makers, cultural producers and justice seekers* #AllGirlsEverything"};

            String[] roomLocations = ((HomepageActivity) mContext).getResources().getStringArray(R.array.roomLocations);
            String[] roomDates = ((HomepageActivity) mContext).getResources().getStringArray(R.array.roomDates);

            for (int i = 0; i < NUM_ITEMS; i++) {
                ChatRoom room = new ChatRoom(roomNames[i], roomDesc[i], roomLocations[i], roomDates[i]);
                listOfRooms.add(room);
            }
        }
    }

    public void clear(){
        setCurrentItem(0);//reset
        listOfRooms.clear();
    }
}

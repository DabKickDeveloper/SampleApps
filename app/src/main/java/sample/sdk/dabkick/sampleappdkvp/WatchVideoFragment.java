package sample.sdk.dabkick.sampleappdkvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dabkick.videosdk.livesession.videoview.DkVideoView;
import com.dabkick.videosdk.publicsettings.DabKick;
import com.dabkick.videosdk.publicsettings.DabKickVideoButton;
import com.dabkick.videosdk.publicsettings.DabKickVideoInfo;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WatchVideoFragment extends Fragment {

    private static final String ARG_VIDEO = "arg_title";
    public static final String TAG = WatchVideoFragment.class.getName();
    @BindView(R.id.fragment_watch_video_video_view)
    DkVideoView videoView;
    @BindView(R.id.fragment_watch_video_title)
    TextView titleTextView;
    private Unbinder unbinder;
    private DabKickVideoInfo dabKickVideoInfo;
    private boolean pausedVideoInLifecycle, onPauseCalled = false;

    public WatchVideoFragment() {
        // Required empty public constructor - use newInstance instead of constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment WatchVideoFragment.
     */
    public static WatchVideoFragment newInstance(DabKickVideoInfo dabKickVideoInfo) {
        WatchVideoFragment fragment = new WatchVideoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIDEO, dabKickVideoInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            dabKickVideoInfo = getArguments().getParcelable(ARG_VIDEO);
            videoView.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared() {
                    if (!onPauseCalled)
                        videoView.start();
                }
            });
            videoView.setVideoPath(dabKickVideoInfo.getVideoUrl());

            String title = dabKickVideoInfo.getTitle();
            titleTextView.setText(title);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_watch_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        DabKickVideoButton button = view.findViewById(R.id.dabkick_video_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                DabKick.setVideoProvider(Util.createDabKickVideoProvider(dabKickVideoInfo));
                DabKick.watchWithFriends(getActivity());
            }
        });
        return view;
    }

    @OnClick(R.id.fragment_watch_video_share)
    void onShareClicked() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, dabKickVideoInfo.getVideoUrl());
        startActivity(Intent.createChooser(intent, getString(R.string.share_video)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(videoView != null)
            videoView.pause();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        onPauseCalled = true;
        super.onPause();
        if (videoView.isPlaying()) {
            pausedVideoInLifecycle = true;
            videoView.pause();
        }
    }

    @Override
    public void onResume() {
        onPauseCalled = false;
        super.onResume();
        if (pausedVideoInLifecycle) {
            pausedVideoInLifecycle = false;
            videoView.start();
        }
    }
}

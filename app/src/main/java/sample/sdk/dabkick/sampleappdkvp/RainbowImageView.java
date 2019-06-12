package sample.sdk.dabkick.sampleappdkvp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class RainbowImageView extends android.support.v7.widget.AppCompatImageView {
    public RainbowImageView(Context context) {
        super(context);
        init();
    }

    public RainbowImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RainbowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        GradientDrawable rainbow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.rgb(78, 194, 196),
                        Color.rgb(101, 204, 188),
                        Color.rgb(146, 200, 137),
                        Color.rgb(208, 191, 71),
                        Color.rgb(203, 170, 123),
                        Color.rgb(197, 149, 176)});
        setBackground(rainbow);
    }

}

package com.neolix.test;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class TestActivity extends Activity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);

        test = (TextView) findViewById(R.id.test);

        AnimationSet set = new AnimationSet(true);
//        TranslateAnimation animation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 15f,
//                Animation.RELATIVE_TO_SELF, 0f
//        );

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -100);
        set.setFillEnabled(true);
        set.setFillAfter(true);
        set.addAnimation(animation);
        animation.setDuration(2000);
        test.startAnimation(set);
    }
}

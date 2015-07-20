package com.neolix.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AppListItem extends RelativeLayout {

    private final Context context;
    private LayoutInflater inflater;
    private ImageView appIcon;
    private TextView tvAppLabel;
    private TextView tip;
    private ImageView chooseBt;
    private TranslateAnimation nameAnim;
    private AlphaAnimation alp;
    private boolean isAlfAlive;
    private RelativeLayout totalView;

    public AppListItem(Context context) {
        super(context);
        initView(context);
        this.context = context;
    }

    public AppListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        this.context = context;
    }

    public AppListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        this.context = context;
    }

    private void initView(Context context) {
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.soft_row, this);
        totalView = (RelativeLayout) view.findViewById(R.id.vw1);
        appIcon = (ImageView) view.findViewById(R.id.img);
        tvAppLabel = (TextView) view.findViewById(R.id.name);
        tip = (TextView) view.findViewById(R.id.desc);

        chooseBt = (ImageView) findViewById(R.id.choosed_bt);
    }

    public void bindData(String name, Drawable drawable, String className) {
        appIcon.setImageDrawable(drawable);
        String cls = Configuration.getPrivateClassName(context);
        boolean equals = className.equals(cls);
        if (equals) {
            changeView(true);
        }

        tvAppLabel.setText(name);
    }

    public void changeView(boolean isCurrentItem) {
        chooseBt.setVisibility(isCurrentItem ? VISIBLE : GONE);
        totalView.setBackgroundColor(isCurrentItem ? Color.parseColor("#f3f3f3") : Color.parseColor("#00000000"));
        if (isCurrentItem) {
            startNameUpAnim();
            startTipUpAnim();
        } else {
            if (tip.getVisibility() == GONE) return;
            if (!isAlfAlive) {
                startTipDownAnim();
            } else tip.setVisibility(GONE);

//            if (!isNameAnimAlive)
            startNameDownAnim(200);
//            else
//                startNameDownAnim(0);
        }
    }

    private void startTipDownAnim() {
        alp = new AlphaAnimation(1.0f, 0.0f);
        alp.setDuration(500);
        tip.setAnimation(alp);
        alp.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                isAlfAlive = true;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                tip.setVisibility(GONE);
                isAlfAlive = false;
            }

        });
    }

    private void startTipUpAnim() {
        tip.setText(getContext().getString(R.string.tips));
        tip.setVisibility(VISIBLE);
        AlphaAnimation alp = new AlphaAnimation(0.0f, 1.0f);

        alp.setDuration(500);
        tip.setAnimation(alp);
    }

    private void startNameUpAnim() {
        AnimationSet set = new AnimationSet(true);
        nameAnim = new TranslateAnimation(
                0, 0, 0, -15
        );

        set.setFillEnabled(true);
        set.setFillAfter(true);
        nameAnim.setDuration(500);
        set.addAnimation(nameAnim);
        tvAppLabel.startAnimation(set);
    }

    private void startNameDownAnim(int time) {
        AnimationSet set = new AnimationSet(true);
        nameAnim = new TranslateAnimation(
                0, 0, -15, 0
        );

        nameAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        set.setFillEnabled(true);
        set.setFillAfter(true);
        nameAnim.setDuration(time);
        set.addAnimation(nameAnim);
        tvAppLabel.startAnimation(set);
    }

}
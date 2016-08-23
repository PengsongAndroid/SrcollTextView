package com.peng.scrolltextview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by PS on 2016/8/23.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ScrollTextView2 extends TextView implements Animator.AnimatorListener{

    private static final String TAG = ScrollTextView2.class.getSimpleName();

    private int mHeight;
    private int mWidth;
    private String mText = "";

    private AnimatorSet startAnimationSet;
    private AnimatorSet endAnimationSet;

    private static final String TranslationX = "translationX";
    private static final String TranslationY = "translationY";
    private static final String Alpha = "alpha";

    /**   动画间隔时间  */
    private static final int ANIMATION_DURATION = 4000;

    public ScrollTextView2(Context context) {
        super(context);
        initUpAnimation();
    }

    public ScrollTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text){
        if (TextUtils.isEmpty(text)){
            return;
        }
        mText = text;
        super.setText(mText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getHeight();
        mWidth = getWidth();
        if (mWidth != 0){
            if (null == startAnimationSet){
                initUpAnimation();
            }
            startAnimationSet.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initUpAnimation(){
        ObjectAnimator translate = ObjectAnimator.ofFloat(this, TranslationX, mWidth, -mWidth);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, Alpha, 1f, 0.5f);
        startAnimationSet = new AnimatorSet();
        startAnimationSet.play(translate).with(alpha);
        startAnimationSet.setDuration(ANIMATION_DURATION);
        startAnimationSet.addListener(this);
    }

    private void initDownAnimation(){
        ObjectAnimator translate = ObjectAnimator.ofFloat(this, TranslationX, mWidth, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, Alpha, 0f, 1f);
        endAnimationSet = new AnimatorSet();
        endAnimationSet.play(translate).with(alpha);
        endAnimationSet.setDuration(ANIMATION_DURATION);
        endAnimationSet.addListener(this);
    }

    @Override
    public void onAnimationStart(Animator animation) {
        Log.i(TAG, "onAnimationStart ");
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (null == endAnimationSet){
            initDownAnimation();
        }
        if (animation.equals(endAnimationSet)){
            startAnimationSet.start();
        } else {
            endAnimationSet.start();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}